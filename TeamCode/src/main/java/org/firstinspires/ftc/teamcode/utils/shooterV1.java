package org.firstinspires.ftc.teamcode.utils;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;


public class shooterV1 {
    // Launcher constants
    public int TARGET_RPM = 1150;

    public int RPM_TOLERANCE = 25;
    final int RPM_IN_RANGE_TIME = 250;
    final int FEED_TIME = 2000;

    public boolean launched = false;

    // Motors and servos
    public static DcMotorEx chipMotor;
    public static Servo cPos;



    // Other variables
    public State state = State.IDLE;

    public boolean isBusy;

    // timers
    private final ElapsedTime feedTimer = new ElapsedTime();
    private final ElapsedTime inToleranceTimer = new ElapsedTime();
    private final ElapsedTime recoveryTimer = new ElapsedTime();

    // PIDF coefficients for the launcher motor, getter and setter for live tuning
    private PIDFCoefficients pidfCoefficients = new PIDFCoefficients(200, 0, 4, 13.8);
    public PIDFCoefficients getCoefficients() {
        return pidfCoefficients;
    }
    public void setCoefficients(PIDFCoefficients coefficients) {
        this.pidfCoefficients = coefficients;
        chipMotor.setPIDFCoefficients(DcMotorEx.RunMode.RUN_USING_ENCODER, pidfCoefficients);
    }


    public shooterV1(HardwareMap hardwareMap) {
        // initialize hardware (drivetrain is initialized by Pedro Pathing)
        chipMotor = hardwareMap.get(DcMotorEx.class, "c");
        cPos = hardwareMap.get(Servo.class,"cPose");

        // Set launcher motor characteristics
        chipMotor.setZeroPowerBehavior(BRAKE);
        chipMotor.setMode(com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_USING_ENCODER);
        chipMotor.setPIDFCoefficients(DcMotorEx.RunMode.RUN_USING_ENCODER, pidfCoefficients);
        chipMotor.setDirection(DcMotorEx.Direction.REVERSE);


        // Make sure servos are stopped
    }

    public enum State {
        IDLE, //waiting to launch
        SPEED_UP,
        FEED,
        RECOVER
    }

    public void stop() {
        state = State.IDLE;
        chipMotor.setPower(0);
        passthrough.off();
    }

    public boolean isBusy() {
        return state != State.IDLE;
    }

    public double getChipRPM() {
        return chipMotor.getVelocity();
    }



    public int getTargetRPM() {
        return TARGET_RPM;
    }

    public void upTarget() {
        TARGET_RPM += 50;
    }

    public void downTarget() {
        TARGET_RPM -= 50;
    }
    public void setSpeedTest(int t){chipMotor.setVelocity(t);}



    public void shoot() {
        if (state == State.IDLE) {
            isBusy = true;
            state = State.SPEED_UP;
            inToleranceTimer.reset();
            inToleranceTimer.reset();
        }
    }


    public void posNectar() {
        cPos.setPosition(1);
    }
    public void posPollen() {
        cPos.setPosition(0);
    }

    public void startSpeed(int vel){
        chipMotor.setVelocity(vel);
    }

    public State getState() {
        return state;
    }

    public void update(boolean b) {
        switch (state) {
            case IDLE:
                chipMotor.setPower(0);
                passthrough.off();

                break;

            case SPEED_UP:
                chipMotor.setVelocity(TARGET_RPM);
                boolean chipInTol = Math.abs(TARGET_RPM - getChipRPM()) <= RPM_TOLERANCE;

                if (chipInTol) {
                    if (inToleranceTimer.milliseconds() >= RPM_IN_RANGE_TIME) {
                        // Ready to feed
                        passthrough.in();
                        state = State.FEED;
                        feedTimer.reset();

                    }
                } else {
                    inToleranceTimer.reset();
                }
                break;

            case FEED:

                if (feedTimer.milliseconds() >= FEED_TIME) {
                    passthrough.off();
                    recoveryTimer.reset();
                    state = State.RECOVER;
                }
                break;

            case RECOVER:
                isBusy = false;
                break;
        }
    }
}
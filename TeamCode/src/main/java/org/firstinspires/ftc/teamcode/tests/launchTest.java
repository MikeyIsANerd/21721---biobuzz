package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.utils.intake;
import org.firstinspires.ftc.teamcode.utils.passthrough;
import org.firstinspires.ftc.teamcode.utils.shooterV1;




@TeleOp(name="launch test", group="Linear OpMode")
public class launchTest extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor shooter = null;

    private shooterV1 s;
    private passthrough pt;
    private intake i;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        s = new shooterV1(hardwareMap);
        pt = new passthrough(hardwareMap);


        // Wait for the game to start (driver presses START)
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {



            if (gamepad1.dpadUpWasReleased())
                s.upTarget();

            if (gamepad1.dpadDownWasReleased())
                s.downTarget();


            if (gamepad1.dpadLeftWasReleased())
                s.posPollen();
            if (gamepad1.dpadRightWasReleased())
                s.posNectar();


            if (!gamepad1.aWasPressed())
                s.setSpeedTest(s.getTargetRPM());
            else
                s.setSpeedTest(0);

            telemetry.addData("target RPM", s.getTargetRPM());
            telemetry.addData("current RPM", s.getChipRPM());
            telemetry.update();
        }
    }
}

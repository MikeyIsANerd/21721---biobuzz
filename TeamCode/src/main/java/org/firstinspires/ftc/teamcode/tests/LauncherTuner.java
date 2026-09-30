package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.teamcode.utils.shooterV1;

/*
Launcher Tuning OpMode
Tune the PIDF controllers for the launcher subsystem

How to Use:

1. Press the Right Bumper on your gamepad to toggle the launcher flywheel on/off.
2. Watch Target RPM vs Actual RPM on Telemetry and tune P, I, D, and F constants:
   - F (Feedforward): Increase until flywheel reliably reaches target speed (1150).
   - P (Proportional): Adjust for quick acceleration with minimal overshoot.
   - D (Derivative): Increase if there is overshoot or oscillation.

Controls:
Right Bumper: Toggle launcher speed up
*/
@TeleOp(name = "Launcher Tuner", group = "Tests")
@SuppressWarnings("FieldCanBeLocal")
public class LauncherTuner extends OpMode {
    private shooterV1 launcher;
    public static double p = 200;
    public static double i = 0;
    public static double d = 4;
    public static double f = 13.8;

    @Override
    public void init() {
        // Create instance of launcher and initialize
        launcher = new shooterV1(hardwareMap);
        launcher.TARGET_RPM = 1150; // Set a default target RPM for tuning
        launcher.RPM_TOLERANCE = 0; // This stops the launcher from leaving the speed up state while tuning

        // Set initial coefficients from the launcher
        PIDFCoefficients initialCoeffs = launcher.getCoefficients();
        p = initialCoeffs.p;
        i = initialCoeffs.i;
        d = initialCoeffs.d;
        f = initialCoeffs.f;

        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }

    @Override
    public void loop() {
        // Update the controller coefficients and run the launcher loop
        PIDFCoefficients newCoeffs = new PIDFCoefficients(p, i, d, f);
        launcher.setCoefficients(newCoeffs);
        launcher.update(true);

        // Right Bumper: toggle launcher speed up
        if (gamepad1.rightBumperWasPressed()) {
            if (launcher.isBusy()) {
                launcher.stop();
            } else {
                launcher.shoot();
            }
        }

        // Log status to telemetry
        telemetry.addData("Launcher State", launcher.getState());
        telemetry.addData("Target RPM", launcher.getTargetRPM());
        telemetry.addData("Actual RPM", launcher.getChipRPM());
        telemetry.addData("P", newCoeffs.p);
        telemetry.addData("I", newCoeffs.i);
        telemetry.addData("D", newCoeffs.d);
        telemetry.addData("F", newCoeffs.f);
        telemetry.update();
    }
}
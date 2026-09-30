package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.utils.colorSens;

@TeleOp(name="Color Sensor Value Test", group="Tests")
public class colorSensValue extends OpMode {
    private colorSens cs;

    @Override
    public void init() {
        telemetry.addData("Status", "Initializing Color Sensor...");
        telemetry.update();

        // Initialize color sensor using your utility
        cs = new colorSens(hardwareMap);
        cs.enableLed(true);

        telemetry.addData("Status", "Initialized. Ready.");
        telemetry.update();
    }

    @Override
    public void loop() {
        // Fetch values from the sensor utility
        int red = cs.red();
        int blue = cs.blue();
        int alpha = cs.alpha();
        float hue = cs.getHue();

        boolean isRed = cs.isRed();
        boolean isBlue = cs.isBlue();

        // Display raw and analyzed values to Telemetry
        telemetry.addData("--- raw values ---", "");
        telemetry.addData("Red", red);
        telemetry.addData("Blue", blue);
        telemetry.addData("Alpha (Brightness)", alpha);

        telemetry.addData("--- checking if blue or red ---", "");
        telemetry.addData("Hue", "%.2f", hue);
        telemetry.addData("Is Red?", isRed);
        telemetry.addData("Is Blue?", isBlue);


        telemetry.update();
    }
}
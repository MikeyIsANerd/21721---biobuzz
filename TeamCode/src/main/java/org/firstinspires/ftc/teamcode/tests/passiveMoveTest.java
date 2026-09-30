package org.firstinspires.ftc.teamcode.tests;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.utils.colorSens;
import org.firstinspires.ftc.teamcode.utils.shooter;

// this is to test keeping a servo at a certain angle until a color is sensed, and then hold a new angle until it isn't sensed

public class passiveMoveTest extends OpMode {
    colorSens cs;
    shooter s;

    @Override
    public void init() {
        cs = new colorSens(hardwareMap);
        s = new shooter(hardwareMap);
    }

    @Override
    public void loop() {

        boolean nectarFound = cs.isRed() || cs.isBlue();

        if (nectarFound) {
            s.posNectar();
        } else {
            s.posPollen();
        }


    }







}

package org.firstinspires.ftc.teamcode.tests;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.utils.colorSens;
import org.firstinspires.ftc.teamcode.utils.shooterV1;
import com.qualcomm.robotcore.util.ElapsedTime;


// this is to test keeping a servo at a certain angle until a color is sensed, and then hold a new angle until it isn't sensed

public class passiveMoveTest extends OpMode {
    colorSens cs;
    shooterV1 s;
    private ElapsedTime upTime = new ElapsedTime();

    @Override
    public void init() {
        cs = new colorSens(hardwareMap);
        s = new shooterV1(hardwareMap);
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

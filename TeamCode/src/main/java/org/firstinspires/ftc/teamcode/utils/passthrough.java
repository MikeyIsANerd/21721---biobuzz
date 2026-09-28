package org.firstinspires.ftc.teamcode.utils;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class passthrough {
    public static double pow = (1);




    public static CRServo l;
    public static CRServo r;



    public passthrough(HardwareMap hardwareMap) {
        r = hardwareMap.get(CRServo.class, "tL");
        l = hardwareMap.get(CRServo.class, "tR");


    }



    public static void in(){
        l.setPower(1);
        r.setPower(-1);

    }


    public static void off(){
        l.setPower(0);
        r.setPower(0);

    }


}
package org.firstinspires.ftc.teamcode.utils;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import android.graphics.Color;

public class colorSens {
    public ColorSensor cs;
    public DistanceSensor distanceSensor;

    private final float[] hsvValues = new float[3];

    // Tunable tolerances and thresholds (without distance)
    public int colorMargin = 10;
    public int minAlpha = 50;

    public colorSens(HardwareMap hardwareMap) {
        cs = hardwareMap.get(ColorSensor.class, "cs");
        try {
            distanceSensor = hardwareMap.get(DistanceSensor.class, "cs");
        } catch (Exception e) {
            distanceSensor = null;
        }
    }

    /**
     * Get red component (0-255)
     */
    public int red() {
        return cs.red();
    }

    /**
     * Get green component (0-255)
     */
    public int green() {
        return cs.green();
    }

    /**
     * Get blue component (0-255)
     */
    public int blue() {
        return cs.blue();
    }

    /**
     * Get alpha (optical light reading)
     */
    public int alpha() {
        return cs.alpha();
    }

    /**
     * Get distance in centimeters (if distance sensor is available)
     */
    public double getDistanceCm() {
        if (distanceSensor != null) {
            return distanceSensor.getDistance(DistanceUnit.CM);
        }
        return -1.0;
    }

    /**
     * Get distance in inches (if distance sensor is available)
     */
    public double getDistanceInches() {
        if (distanceSensor != null) {
            return distanceSensor.getDistance(DistanceUnit.INCH);
        }
        return -1.0;
    }

    /**
     * Get HSV (Hue, Saturation, Value) array
     */
    public float[] getHsv() {
        Color.RGBToHSV(cs.red(), cs.green(), cs.blue(), hsvValues);
        return hsvValues;
    }

    /**
     * Get Hue (0-360)
     */
    public float getHue() {
        return getHsv()[0];
    }

    /**
     * Get Saturation (0-1)
     */
    public float getSaturation() {
        return getHsv()[1];
    }

    /**
     * Get Value / Brightness (0-1)
     */
    public float getValue() {
        return getHsv()[2];
    }

    /**
     * Enable or disable the LED
     */
    public void enableLed(boolean enable) {
        cs.enableLed(enable);
    }

    /**
     * Checks if red is detected using color margin and minimum alpha threshold
     */
    public boolean isRed() {
        return (red() > blue() + colorMargin) && (alpha() > minAlpha);
    }

    /**
     * Checks if blue is detected using color margin and minimum alpha threshold
     */
    public boolean isBlue() {
        return (blue() > red() + colorMargin) && (alpha() > minAlpha);
    }

    /**
     * Checks if an object is present based purely on alpha/brightness threshold
     */
    public boolean isObjectPresent() {
        return alpha() > minAlpha;
    }
}
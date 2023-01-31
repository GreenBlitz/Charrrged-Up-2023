package edu.greenblitz.tobyDetermined.commands.TelescopicArm.Commands;

import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow;

public class armUtils {
    public static boolean isInTheSameSide (double angle1, double angle2) {//in rads
        return (Elbow.getHypotheticalState(angle1) == Elbow.getHypotheticalState(angle2));
    }
}

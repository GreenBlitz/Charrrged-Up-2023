package edu.greenblitz.tobyDetermined.Nodesssss;

import edu.greenblitz.tobyDetermined.Nodesssss.TheSystemsNodes.GriperNode;
import edu.greenblitz.tobyDetermined.Nodesssss.TheSystemsNodes.NodeArm;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow.Elbow;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender.Extender;
import edu.wpi.first.math.Pair;

import java.util.LinkedList;

public class NodeSystemConstants {

    public enum SystemsPos {
        CONE_HIGH,
        CONE_MID,
        CUBE_HIGH,
        CUBE_MID,
        LOW,

        POST_CONE_DROP,
        PRE_CONE_DROP,

        INTAKE_GRAB_CONE_POSITION,
        INTAKE_GRAB_CUBE_POSITION,
        PRE_INTAKE_GRAB_POSITION,

        REST_ABOVE_BELLY,

        ZIG_HAIL,

        GRIPER_ONE,
        GRIPER_TWO,
        GRIPER_THREE,

        ARM_GROUND,
        ARM_LOWWW,
        ARM_MID,
        ARM_HIGH,

        GRIPER_OPEN,
        GRIPER_CLOSE,

        MID_NODE;

    }

    static public String systemName1 = "ARM";
    static public String systemName2 = "GRIPER";

    static public SystemsPos system1StartingNode = SystemsPos.ARM_GROUND;
    static public SystemsPos system2StartingNode = SystemsPos.GRIPER_CLOSE;

    public static GBNode getNodeBySystemName(String systemName) {
        if (systemName.equals(systemName1))
            return new NodeArm(Extender.getInstance().getLength(), Elbow.getInstance().getAngleRadians());
        return new GriperNode();
    }

    static LinkedList<Pair<String, Double>> costList = new LinkedList<>();

    static {
        costList.add(new Pair<>(SystemsPos.ARM_LOWWW + "-" + SystemsPos.ARM_MID, 3.0));
        costList.add(new Pair<>(SystemsPos.ARM_LOWWW + "-" + SystemsPos.ARM_HIGH, 5.0));
        costList.add(new Pair<>(SystemsPos.ARM_LOWWW + "-" + SystemsPos.ARM_GROUND, 7.0));
        costList.add(new Pair<>(SystemsPos.ARM_GROUND + "-" + SystemsPos.ARM_HIGH, 9.0));
        costList.add(new Pair<>(SystemsPos.ARM_GROUND + "-" + SystemsPos.ARM_MID, 10.0));
        costList.add(new Pair<>(SystemsPos.ARM_MID + "-" + SystemsPos.ARM_HIGH, 12.0));
        costList.add(new Pair<>(SystemsPos.GRIPER_CLOSE + "-" + SystemsPos.GRIPER_OPEN, 15.0));
    }

    public static double getCostByMap(SystemsPos a, SystemsPos b) {
        for (Pair<String, Double> stringDoublePair : costList) {
            if (stringDoublePair.getFirst().contains(a.toString()) && stringDoublePair.getFirst().contains(b.toString()))
                return stringDoublePair.getSecond();
        }
        return 0;
    }

}

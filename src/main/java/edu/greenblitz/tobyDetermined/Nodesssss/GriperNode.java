package edu.greenblitz.tobyDetermined.Nodesssss;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.utils.GBCommand;
import static edu.greenblitz.tobyDetermined.RobotMap.NodeSystem.SystemsPos.*;
import static edu.greenblitz.tobyDetermined.RobotMap.NodeSystem.SystemsPos;
import static edu.greenblitz.tobyDetermined.RobotMap.NodeSystem;
import java.util.Collections;
import java.util.LinkedList;

public class GriperNode extends GBNode {

    private final LinkedList<SystemsPos> armMustBe;
    public GriperNode(GBCommand command) {
        super(command);
        armMustBe = new LinkedList<>();
    }

    @Override
    public LinkedList<SystemsPos> getOtherSystemMustBe() {
        return armMustBe;
    }

    @Override
    public void setOtherSystemMustBe(SystemsPos[] griperMustBe) {
        Collections.addAll(this.armMustBe, griperMustBe);
    }


}

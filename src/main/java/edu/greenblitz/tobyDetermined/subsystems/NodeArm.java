package edu.greenblitz.tobyDetermined.subsystems;

import edu.greenblitz.utils.GBCommand;
import org.w3c.dom.Node;

import java.util.Collections;
import java.util.LinkedList;

public class NodeArm {
    private GBCommand command;
    private LinkedList<NodeArm> neighbors;
    private final int  difficulte = 1;

    public NodeArm(GBCommand command, NodeArm[] neighbors){
        this.command = command;
        Collections.addAll(this.neighbors, neighbors);
    }
    public void runCommand(){
        command.schedule();
    }
    public int getDifficulte() {
        return difficulte;
    }
}

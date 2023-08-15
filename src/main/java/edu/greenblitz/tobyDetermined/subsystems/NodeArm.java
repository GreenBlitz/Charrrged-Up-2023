package edu.greenblitz.tobyDetermined.subsystems;

import edu.greenblitz.utils.GBCommand;
import org.w3c.dom.Node;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

public class NodeArm {
    //private GBCommand command;
    private int num;
    private LinkedList<NodeArm> neighbors;

    public NodeArm(int command){
        //this.command = command;
        num = command;
    }

    public void addNeighbors(NodeArm neighbor){
        this.neighbors.add(neighbor);
    }
    public int getNum() {
        return num;
    }
    //public void runCommand(){
        //command.schedule();
    //}

}

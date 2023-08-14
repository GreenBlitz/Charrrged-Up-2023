import edu.greenblitz.utils.GBCommand;

import java.util.Collections;
import java.util.LinkedList;

public class Node extends GBCommand {
    private String data;
    private LinkedList<Node> neighbors;


    public Node(String d){
        data = d;
        neighbors = new LinkedList<>();
    }


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public LinkedList<Node> getNeighbors(){
        return neighbors;
    }
    public void setNeighbors(Node[] d){
        Collections.addAll(neighbors, d);
    }
}

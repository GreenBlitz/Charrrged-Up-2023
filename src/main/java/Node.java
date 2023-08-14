import  java.util.ArrayList;
import java.util.Collections;

public class Node {
    private String data;
    private ArrayList<Node> neighbors;
    public Node(String d){
        data = d;
        neighbors = new ArrayList<Node>();
    }


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Node[] getNeighbors(){
        return neighbors.toArray(new Node[0]);
    }
    public void setNeighbors(Node[] d){
        Collections.addAll(neighbors, d);
    }
}

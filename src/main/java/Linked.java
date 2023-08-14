import java.util.LinkedList;
import java.util.*;

public class Linked {
    Node A = new Node("A");
    Node B = new Node("B");
    Node C = new Node("C");
    Node D = new Node("D");
    Node E = new Node("E");
    Node F = new Node("F");
    LinkedList<Node> Nodes = new LinkedList<>();

    public Linked() {
        A.setNeighbors(new Node[]{B, C, D});
        B.setNeighbors(new Node[]{A, F});
        C.setNeighbors(new Node[]{A, D, E});
        D.setNeighbors(new Node[]{A, C, F});
        E.setNeighbors(new Node[]{C});
        F.setNeighbors(new Node[]{B, D});

        Nodes.add(A);
        Nodes.add(B);
        Nodes.add(C);
        Nodes.add(D);
        Nodes.add(E);
        Nodes.add(F);
    }

    public Node getNode() {
        return A;
    }
    public LinkedList<Node> getNodes(){
        return Nodes;
    }
    public String getNodes(int i){
        return Nodes.get(i).getData();
    }
}

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public class Graph {
    public static void main(String[] args){
        Node A = new Node("A");
        Node B = new Node("B");
        Node C = new Node("C");
        Node D = new Node("D");
        Node E = new Node("E");
        Node F = new Node("F");

        A.setNeighbors(new Node[] {B,C,D});
        B.setNeighbors(new Node[] {A,F});
        C.setNeighbors(new Node[] {A,D,E});
        D.setNeighbors(new Node[] {A,C,F});
        E.setNeighbors(new Node[] {C});
        F.setNeighbors(new Node[] {B,D});

        DFS(B);
        DFS(D);
    }
    public static void DFS(Node start){
        LinkedList<Node> visited = new LinkedList<>();
        Stack<Node> stack = new Stack<>();
        stack.push(start);

        while(!stack.empty()){
            Node current = stack.pop();
            System.out.print(current.getData() + ", ");
            if(!visited.contains(current)){
                visited.add(current);
                LinkedList<Node> neighbors = current.getNeighbors();
                for (int i = neighbors.size()-1;i>=0 ; i--){
                    stack.push( neighbors.get(i) );
                }
            }
        }
        System.out.println();
    }
}

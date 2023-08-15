import edu.greenblitz.utils.GBCommand;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Stack;

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

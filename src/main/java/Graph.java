import java.util.*;

public class Graph {
    public static void main(String[] args){

    }
    private static Linked linked;
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
    public static void BFS(int start) {
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[linked.getNodes().size()];

        queue.offer(start);
        visited[start] = true;

        while(queue.size() != 0){

            start = queue.poll();
            System.out.println(linked.getNodes(start)+"visited");
        }
    }
}




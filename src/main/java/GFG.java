// Java implementation of the above approach
import java.util.*;
class GFG
{
    static LinkedList<LinkedList<Integer>> v = new LinkedList<>();

    // An utility function to add an edge in an
    // undirected graph.
    static void addEdge(int x, int y){
        v.get(x).add(y);
        v.get(y).add(x);
    }

    // A function to print the path between
    // the given pair of nodes.
    static void printPath(LinkedList<Integer> stack)
    {
        for(int i = 0; i < stack.size() - 1; i++)
        {
            System.out.print(stack.get(i) + " -> ");
            //here need to add run command
        }
        System.out.println(stack.get(stack.size() - 1));
    }

    // An utility function to do
    // DFS of graph recursively
    // from a given vertex x.
    static void DFS(boolean[] visited, int x, int y, LinkedList<Integer> stack)
    {
        stack.add(x);
        if (x == y)
        {

            // print the path and return on
            // reaching the destination node
            printPath(stack);
            return;
        }
        visited[x] = true;

        // if backtracking is taking place     
        if (!v.get(x).isEmpty())
        {
            for(int j = 0; j < v.get(x).size(); j++)
            {

                // if the node is not visited
                if (!visited[v.get(x).get(j)])
                {
                    DFS(visited, v.get(x).get(j), y, stack);
                }
            }
        }

        stack.remove(stack.size() - 1);
    }

    // A utility function to initialise
    // visited for the node and call
    // DFS function for a given vertex x.
    static void DFSCall(int x, int y, int n, LinkedList<Integer> stack)
    {

        // visited array
        boolean[] visited = new boolean[n + 1];
        Arrays.fill(visited, false);

        // memset(vis, false, sizeof(vis))

        // DFS function call
        DFS(visited, x, y, stack);
    }

    // Driver code
    public static void main(String[] args)
    {
        for(int i = 0; i < 100; i++)
        {
            v.add(new LinkedList<>());
        }

        int n = 9;
        LinkedList<Integer> stack = new LinkedList<>();

        // Vertex numbers should be from 1 to 9.
        addEdge(1, 2);
        addEdge(1, 3);
        addEdge(2, 4);
        addEdge(2, 5);
        addEdge(2, 6);
        addEdge(3, 7);
        addEdge(3, 8);
        addEdge(3, 9);

        // Function Call
        DFSCall(4, 1, n, stack);
    }
}
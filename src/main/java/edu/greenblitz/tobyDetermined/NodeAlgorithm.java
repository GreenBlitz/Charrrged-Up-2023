package edu.greenblitz.tobyDetermined;

import edu.greenblitz.tobyDetermined.subsystems.NodeArm;

import java.util.Arrays;
import java.util.LinkedList;

public class NodeAlgorithm {
    // Java implementation of the above approach
        static LinkedList<LinkedList<NodeArm>> linkedList= new LinkedList<>();

        // An utility function to add an edge in an
        // undirected graph.
        static void addEdge(NodeArm x, NodeArm y){
            linkedList.get(x.getNum()).add(y);
           // x.addNeighbors(y);
            linkedList.get(y.getNum()).add(x);
           // y.addNeighbors(x);
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
        static void DFS(boolean[] visited, NodeArm x, NodeArm y, LinkedList<Integer> stack)
        {
            stack.add(x.getNum());
            if (x.getNum() == y.getNum())
            {

                // print the path and return on
                // reaching the destination node
                printPath(stack);
                return;
            }
            visited[x.getNum()] = true;

            // if backtracking is taking place
            if (!linkedList.get(x.getNum()).isEmpty())
            {
                for(int j = 0; j < linkedList.get(x.getNum()).size(); j++)
                {

                    // if the node is not visited
                    if (!visited[linkedList.get(x.getNum()).get(j).getNum()])
                    {
                        DFS(visited, linkedList.get(x.getNum()).get(j), y, stack);
                    }
                }
            }

            stack.remove(stack.size() - 1);
        }

        // A utility function to initialise
        // visited for the node and call
        // DFS function for a given vertex x.
        static void DFSCall(NodeArm x, NodeArm y, int n, LinkedList<Integer> stack)
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
                linkedList.add(new LinkedList<>());
            }

            int n = 10;
            LinkedList<Integer> stack = new LinkedList<>();

            NodeArm n1 = new NodeArm(1);
            NodeArm n2 = new NodeArm(2);
            NodeArm n3 = new NodeArm(3);
            NodeArm n4 = new NodeArm(4);
            NodeArm n5 = new NodeArm(5);
            NodeArm n6 = new NodeArm(6);
            NodeArm n7 = new NodeArm(7);
            NodeArm n8 = new NodeArm(8);
            NodeArm n9 = new NodeArm(9);


            // Vertex numbers should be from 1 to 9.
            addEdge(n1, n2);
            addEdge(n1, n3);
            addEdge(n2, n4);
            addEdge(n2, n5);
            addEdge(n2, n6);
            addEdge(n3, n7);
            addEdge(n3, n8);
            addEdge(n3, n9);

            // Function Call
           DFSCall(n4, n8, n, stack);
        }
    }


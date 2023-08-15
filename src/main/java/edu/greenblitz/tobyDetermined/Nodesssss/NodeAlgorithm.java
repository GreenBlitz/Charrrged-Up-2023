package edu.greenblitz.tobyDetermined.Nodesssss;

import java.util.Arrays;
import java.util.LinkedList;

public class NodeAlgorithm  {
    // Java implementation of the above approach
        static LinkedList<LinkedList<NodeArm>> linkedList= new LinkedList<>();
        static NodeBase nodeBase = new NodeBase();

        // An utility function to add an edge in an
        // undirected graph.
        static void addEdge(){
//            linkedList.get(x.getNum()).add(y);
//            x.addNeighbors(y);
//            linkedList.get(y.getNum()).add(x);
//            y.addNeighbors(x);
//            linkedList.get(nodeBase.getNode(1).getIndex()).addAll(nodeBase.getNode(1).getNeighbors());
//            linkedList.get(nodeBase.getNode(2).getIndex()).addAll(nodeBase.getNode(2).getNeighbors());
//            linkedList.get(nodeBase.getNode(3).getIndex()).addAll(nodeBase.getNode(3).getNeighbors());
//            linkedList.get(nodeBase.getNode(4).getIndex()).addAll(nodeBase.getNode(4).getNeighbors());
//            linkedList.get(nodeBase.getNode(5).getIndex()).addAll(nodeBase.getNode(5).getNeighbors());
//            linkedList.get(nodeBase.getNode(6).getIndex()).addAll(nodeBase.getNode(6).getNeighbors());
//            linkedList.get(nodeBase.getNode(7).getIndex()).addAll(nodeBase.getNode(7).getNeighbors());
//            linkedList.get(nodeBase.getNode(8).getIndex()).addAll(nodeBase.getNode(8).getNeighbors());
//            linkedList.get(nodeBase.getNode(9).getIndex()).addAll(nodeBase.getNode(9).getNeighbors());
            for(int i = 0; i<nodeBase.getList().size(); i++){
                linkedList.get(i).addAll(nodeBase.getNode(i).getNeighbors());
            }


        }

        // A function to print the path between
        // the given pair of nodes.
        static void printPath(LinkedList<Integer> stack)
        {
            for(int i = 0; i < stack.size() - 1; i++)
            {
                System.out.print(stack.get(i) + " -> ");
                //here need to add run command!!!!!!!!!!!!!!!!!!!
            }
            System.out.println(stack.get(stack.size() - 1));
        }

        // An utility function to do
        // DFS of graph recursively
        // from a given vertex x.
        static void DFS(boolean[] visited, NodeArm x, NodeArm y, LinkedList<Integer> stack)
        {
            stack.add(x.getIndex());
            if (x.getIndex() == y.getIndex())
            {

                // print the path and return on
                // reaching the destination node
                printPath(stack);
                return;
            }
            visited[x.getIndex()] = true;

            // if backtracking is taking place
            if (!linkedList.get(x.getIndex()).isEmpty())
            {
                for(int j = 0; j < linkedList.get(x.getIndex()).size(); j++)
                {

                    // if the node is not visited
                    if (!visited[linkedList.get(x.getIndex()).get(j).getIndex()])
                    {
                        DFS(visited, linkedList.get(x.getIndex()).get(j), y, stack);
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

            addEdge();

            DFSCall(nodeBase.getNode(4), nodeBase.getNode(8), n, stack);

//            NodeArm n1 = new NodeArm(1);
//            NodeArm n2 = new NodeArm(2);
//            NodeArm n3 = new NodeArm(3);
//            NodeArm n4 = new NodeArm(4);
//            NodeArm n5 = new NodeArm(5);
//            NodeArm n6 = new NodeArm(6);
//            NodeArm n7 = new NodeArm(7);
//            NodeArm n8 = new NodeArm(8);
//            NodeArm n9 = new NodeArm(9);


            // Vertex numbers should be from 1 to 9.
//            addEdge(n1, n2);
//            addEdge(n1, n3);
//            addEdge(n2, n4);
//            addEdge(n2, n5);
//            addEdge(n2, n6);
//            addEdge(n3, n7);
//            addEdge(n3, n8);
//            addEdge(n3, n9);

            // Function Call

        }
    }


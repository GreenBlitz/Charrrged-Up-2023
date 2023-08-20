package edu.greenblitz.tobyDetermined.Nodesssss;
// Java program to print all paths of source to
// destination in given graph
import java.io.*;
import java.util.*;
public class NodeBetterAlgorithm{
    static NodeBase nodeBase = new NodeBase();
    static LinkedList<LinkedList<NodeArm> > linkedList = new LinkedList<>();

    private static void addEdge() {

//        for (int i = 1; i < list.size(); i++) {
//            linkedList.get(i).addAll(list.get(i).getNeighbors());
//        }
        for(int i = 1; i<nodeBase.getList().size(); i++){
            linkedList.get(i).addAll(nodeBase.getNode(i).getNeighbors());
        }
    }

    // utility function for printing
// the found path in graph
    private static void printPath(LinkedList<NodeArm> path)
    {
        int size = path.size();
        for(NodeArm n : path)
        {
            System.out.print(n.getId() + " ");
            //add command schedule here
        }
        System.out.println();
    }

    // Utility function to check if current
// vertex is already present in path
    private static boolean isNotVisited(NodeArm x, LinkedList<NodeArm> path)
    {
        int size = path.size();
        for(int i = 0; i < size; i++)
            if (path.get(i).getId() == x.getId())
                return false;

        return true;
    }

    // Utility function for finding paths in graph
// from source to destination
    private static void findpaths(LinkedList<LinkedList<NodeArm>> g, NodeArm src, NodeArm dst, int n)
    {

        // Create a queue which stores
        // the paths
        Queue<LinkedList<NodeArm>> queue = new LinkedList<>();

        LinkedList<NodeArm> pathSaver = new LinkedList<>();

        int min = Integer.MAX_VALUE;

        // Path vector to store the current path
        LinkedList<NodeArm> path = new LinkedList<>();
        path.add(src);
        queue.offer(path);

        while (!queue.isEmpty())
        {


            path = queue.poll();
            NodeArm last = path.get(path.size() - 1);

            // If last vertex is the desired destination
            // then print the path
            if (last.getId() == dst.getId())
            {
                if(path.size()<min){
                    pathSaver = path;
                    min = pathSaver.size();
                }
                //  printPath(path);
            }

            // Traverse to all the nodes connected to
            // current vertex and push new path to queue
            LinkedList<NodeArm> lastNode = g.get(last.getId());
            for(int i = 0; i < lastNode.size(); i++)
            {
                if (isNotVisited(lastNode.get(i), path))
                {
                    LinkedList<NodeArm> newpath = new LinkedList<>(path);
                    newpath.add(lastNode.get(i));
                    queue.offer(newpath);
                }
            }
        }
        printPath(pathSaver);
    }

    // Driver code
    public static void main(String[] args)
    {

        for(int i = 0; i < 100; i++)
        {
            linkedList.add(new LinkedList<>());
        }
        int n = 10;
        // Construct a graph
        addEdge();


        // Function for finding the paths
        findpaths(linkedList, nodeBase.getNode(6), nodeBase.getNode(3), n);
    }
}
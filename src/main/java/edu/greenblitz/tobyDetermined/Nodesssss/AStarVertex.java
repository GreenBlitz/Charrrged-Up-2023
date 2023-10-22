package edu.greenblitz.tobyDetermined.Nodesssss;

import edu.wpi.first.math.Pair;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static edu.greenblitz.tobyDetermined.RobotMap.Intake.GriperPos;

public class AStarVertex {

    private static HashMap<Vertex, LinkedList<GriperPos>> pathMap;

    public static <T> void printPath(LinkedList<T> pathList) {
        for (int i = 0; i <= pathList.size() - 1; i++) {
            System.out.print(pathList.get(i) + ", ");
        }
        System.out.println();
    }
    public static void printPathVer(LinkedList<Vertex> pathList) {
        for (int i = 0; i <= pathList.size() - 1; i++) {
            System.out.print(pathList.get(i).getPos1() + ", " + pathList.get(i).getPos2() + ";;;; ");
        }
        System.out.println();
    }

    private static <T> boolean isInList(T object, LinkedList<T> list) {
        return list.contains(object);
    }

    public static Vertex getVertexLowestFcost(LinkedList<Vertex> open, GriperPos start, GriperPos end, GriperPos secondCurrent ) {
        int saveI = 0;
        double fCost = open.get(0).getFCostVertex(start, end);
        fCost+=addOtherSystemCost(open.get(0),start,  secondCurrent);
        for (int i = 1; i < open.size(); i++) {
            double currentFCost = open.get(i).getFCostVertex(start, end);
            currentFCost+=addOtherSystemCost(open.get(i),start, secondCurrent);
            if (currentFCost < fCost) {
                fCost = currentFCost;
                saveI = i;
            }
        }
        return open.get(saveI);
    }

    public static double addOtherSystemCost(Vertex vertex, GriperPos start, GriperPos secondCurrent) {
        if (!vertex.ifInOtherSystemMustBe(secondCurrent) && !vertex.getPos1().equals(start)) {
            GriperPos pos = NodeBase.getGripPos(secondCurrent);
            LinkedList<GriperPos> list = vertex.mergeAndReturnOtherSystemMustBe();
            double min = Double.MAX_VALUE;
            LinkedList<GriperPos> path = new LinkedList<>();
            for (GriperPos griperPos : list) {
                Pair<LinkedList<GriperPos>, Double> pair = getPath(pos, griperPos, vertex.getPos1());
                if (pair.getSecond() < min) {
                    min = pair.getSecond();
                    path = pair.getFirst();
                }
            }

            pathMap.put(vertex, path);
            return min;
        }
        return 0;
    }

    public static Pair<LinkedList<GriperPos>, Double> returnPath(Vertex vertex, HashMap<Vertex, Vertex> parents) {
        LinkedList<GriperPos> pathList = new LinkedList<>();
        Vertex current = vertex;
        while (current != null) {
            pathList.addFirst(current.getPos2());
            if (pathMap.containsKey(current)){
                for(int i =0; i<pathMap.get(current).size(); i++){
                    pathList.add(i,pathMap.get(current).get(i));
                }
            }
            pathList.addFirst(current.getPos1());
            current = parents.get(current);
        }
        return new Pair<>(pathList, 0.0);
    }

    public static Pair<LinkedList<GriperPos>, Double> getPath(GriperPos start, GriperPos end, GriperPos otherSystemCurrent) {
        LinkedList<GriperPos> open = new LinkedList<>();
        LinkedList<GriperPos> closed = new LinkedList<>();
        LinkedList<Vertex> openVer = new LinkedList<>();
        HashMap<Vertex, Vertex> parents = new HashMap<>();
        pathMap = new HashMap<>();
        GriperPos secondCurrent = otherSystemCurrent;
        int count = 1;
        open.add(start);

        while (!open.isEmpty()) {

            GriperPos current;
            Vertex currentVer = null;
            if (count != 1) {
                currentVer = getVertexLowestFcost(openVer, start, end, secondCurrent);
                current = currentVer.getPos2();
                openVer.remove(currentVer);
            } else {
                current = start;
                count++;
            }
            open.remove(current);
            closed.add(current);

            if (current.equals(end)) {
                return returnPath(currentVer, parents);
            }

            for (GriperPos neighbor : NodeBase.getNode(current).getNeighbors()) {
                if (!isInList(neighbor, closed) && !isInList(neighbor, open)) {
                    openVer.add(new Vertex(current, neighbor));
                    open.add(neighbor);
                    if(count!=2)
                        parents.put(currentVer, openVer.get(openVer.size() - 1));
                }
            }
        }
        return null;
    }

    public static LinkedList<GriperPos> printAndReturnFinalPath(GriperPos start, GriperPos end, GriperPos otherSystemCurrent){
        Pair<LinkedList<GriperPos>, Double> a = getPath(start,end,otherSystemCurrent);
        assert a != null;
        a.getFirst().addFirst(start);
        printPath(a.getFirst());
        return a.getFirst();
    }

    public static void main(String[] args) {
        LinkedList<GriperPos> a = printAndReturnFinalPath(GriperPos.GRIPER_ONE, GriperPos.GRIPER_THREE, GriperPos.CUBE_MID);
    }
}


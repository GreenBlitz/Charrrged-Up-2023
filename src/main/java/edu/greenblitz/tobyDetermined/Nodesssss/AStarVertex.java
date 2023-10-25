package edu.greenblitz.tobyDetermined.Nodesssss;

import edu.wpi.first.math.Pair;

import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.LinkedList;

import static edu.greenblitz.tobyDetermined.RobotMap.Intake.GriperPos;

public class AStarVertex {

    public static <T> void printPath(LinkedList<T> pathList) {
        for (int i = 0; i <= pathList.size() - 1; i++) {
            System.out.print(pathList.get(i) + ", ");
        }
        System.out.println();
    }
    public static void printPathVer(LinkedList<Vertex> pathList) {
        for (int i = 0; i <= pathList.size() - 1; i++) {
            System.out.print(pathList.get(i).getPos1() + ", "+ pathList.get(i).getPos2()+";;;;;");
        }
        System.out.println();
    }
    private static <T> boolean isInList(T object, LinkedList<T> list) {
        return list.contains(object);
    }

    private  static boolean ifInVerList(LinkedList<Vertex> nonUsable, Vertex ver) {
        for (Vertex vertex : nonUsable) {
            if (vertex.getPos1().equals(ver.getPos1()) && vertex.getPos2().equals(ver.getPos2()))
                return true;
        }
        return false;
    }

    public static Vertex getVertexLowestFcost(LinkedList<Vertex> open, GriperPos start, GriperPos end, LinkedList<Vertex> nonUsable, HashMap<Vertex, LinkedList<GriperPos>> pathMap) {
        int saveI = 0;
        int con = 0;
        //printPathVer(open);
        double fCost = Double.MAX_VALUE;
        Pair<Double, Boolean> a = addOtherSystemCost(open.get(0), pathMap);
        if(!a.getSecond()){
            //nonUsable.add(new Vertex(open.get(0).getPos1(), open.get(0).getPos2(), open.get(0).getOtherSystem()));
            open.remove(open.get(0));
            con++;
        }
        //else if (!ifInVerList(nonUsable, open.get(0))) {
        else {
            fCost = open.get(0).getFCostVertex(start, end);
            fCost+= a.getFirst();
        }
        for (int i = 1-con; i < open.size(); i++) {
            //System.out.println(con);
            a =  addOtherSystemCost(open.get(i), pathMap);
            if(!a.getSecond()){
                //System.out.println(con);
                //nonUsable.add(open.get(i));
                open.remove(open.get(i));
                con++;
            }
           // else if (!ifInVerList(nonUsable, open.get(i))) {
            else{
                double currentFCost = a.getFirst();
                currentFCost += open.get(i).getFCostVertex(start, end);
                if (currentFCost < fCost) {
                    fCost = currentFCost;
                    saveI = i;
                }
            }
        }
        return open.get(saveI);
    }

    public static Pair<Double, Boolean> addOtherSystemCost(Vertex vertex, HashMap<Vertex, LinkedList<GriperPos>> pathMap) {
        if (!vertex.isInOtherSystemMustBe(vertex.getOtherSystem()) ) {
            //System.out.println("in if 1"+vertex.getPos1()+", "+vertex.getPos2());
            LinkedList<GriperPos> otherSystemPositions = NodeBase.getOtherSystemPositions(vertex.getOtherSystem());
            boolean check = false;
            for(int i = 0; i<otherSystemPositions.size() && !check; i++){
                if(vertex.isInOtherSystemMustBe(otherSystemPositions.get(i))){
                    check = true;
                }
            }
            if(check) {
                //System.out.println("in if 2"+vertex.getPos1()+", "+vertex.getPos2());
                if (NodeBase.getNode(vertex.getOtherSystem()).getOtherSystemMustBe().contains(vertex.getPos1()) || NodeBase.getNode(vertex.getOtherSystem()).getOtherSystemMustBe().isEmpty()) {
                    //System.out.println("in if 3"+vertex.getPos1()+", "+vertex.getPos2()+", "+vertex.getOtherSystem());
                    GriperPos pos = NodeBase.getGripPos(vertex.getOtherSystem());
                    LinkedList<GriperPos> list = vertex.mergeAndReturnOtherSystemMustBe();
                    double min = Double.MAX_VALUE;
                    LinkedList<GriperPos> path = new LinkedList<>();
                    for (GriperPos griperPos : list) {
                        //System.out.println(pos+", "+griperPos+", "+vertex.getPos1());
                        Pair<LinkedList<GriperPos>, Double> pair = getPath(pos, griperPos, vertex.getPos1());
                        if (pair.getSecond() < min) {
                            min = pair.getSecond();
                            path = pair.getFirst();
                        }
                    }
                    pathMap.put(vertex, path);
                    vertex.setOtherSystem(path.get(path.size()-1));
                    return new Pair<>(min,true);
                }
                return new Pair<>(0.0,false);
            }
            return new Pair<>(0.0,false);
        }
        return new Pair<>(0.0,true);
    }

    public static Pair<LinkedList<GriperPos>, Double> returnPath(Vertex vertex, HashMap<Vertex, Vertex> parents, HashMap<Vertex, LinkedList<GriperPos>> pathMap) {
        LinkedList<GriperPos> pathList = new LinkedList<>();
        Vertex current = vertex;
        pathList.addFirst(current.getPos2());
        while (current != null) {
            if(!pathList.get(0).equals(current.getPos2()))
                pathList.addFirst(current.getPos2());
            if (pathMap.containsKey(current)) {
                for (int i = 0; i < pathMap.get(current).size(); i++) {
                    if(!pathList.get(i).equals(pathMap.get(current).get(i)))
                        pathList.add(i, pathMap.get(current).get(i));
                }
            }
            if(!pathList.get(0).equals(current.getPos1()))
                pathList.addFirst(current.getPos1());
            current = parents.get(current);
        }
        return new Pair<>(pathList, 0.0);
    }

    public static Pair<LinkedList<GriperPos>, Double> getPath(GriperPos start, GriperPos end, GriperPos secondSystemState) {
        LinkedList<Vertex> closedVer = new LinkedList<>();
        LinkedList<Vertex> openVer = new LinkedList<>();
        HashMap<Vertex, Vertex> parents = new HashMap<>();
        LinkedList<Vertex> nonUsable = new LinkedList<>();
        HashMap<Vertex, LinkedList<GriperPos>> pathMap = new HashMap<>();
        GriperPos current = start;
        Vertex currentVer;
        for (GriperPos neighbor : NodeBase.getNode(current).getNeighbors()) {
            if (!ifInVerList(closedVer, new Vertex(current, neighbor,secondSystemState)) && !ifInVerList(openVer, new Vertex(current, neighbor,secondSystemState))) {
                openVer.add(new Vertex(current, neighbor,secondSystemState));
            }
        }
        //System.out.println("adfafeeas f");

        while (!openVer.isEmpty()) {
            currentVer = getVertexLowestFcost(openVer, start, end, nonUsable, pathMap);
            current = currentVer.getPos2();
            openVer.remove(currentVer);
            closedVer.add(currentVer);

            if (current.equals(end)) {
                return returnPath(currentVer, parents, pathMap);
            }
            for (GriperPos neighbor : NodeBase.getNode(current).getNeighbors()) {
                if (!ifInVerList(closedVer, new Vertex(current, neighbor,currentVer.getOtherSystem())) && !ifInVerList(openVer, new Vertex(current, neighbor,currentVer.getOtherSystem()))) {
                    openVer.add(new Vertex(current, neighbor, currentVer.getOtherSystem()));
                    parents.put( openVer.get(openVer.size() - 1), currentVer);
                }
            }
        }
        return null;
    }

    public static LinkedList<GriperPos> printAndReturnFinalPath(GriperPos start, GriperPos end, GriperPos secondSystemState) {
        Pair<LinkedList<GriperPos>, Double> a = getPath(start, end, secondSystemState);
        printPath(a.getFirst());
        return a.getFirst();
    }

    public static void main(String[] args) {
        LinkedList<GriperPos> a = printAndReturnFinalPath(GriperPos.LOWWW, GriperPos.HIGH, GriperPos.GRIPER_CLOSE);
    }
}


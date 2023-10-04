package edu.greenblitz.utils.SystemCheck;

import java.util.LinkedList;

public class PingableManager {
    private LinkedList<IPingable> pingableList;
    private static PingableManager instance;

    private PingableManager (){
        this.pingableList = new LinkedList<>();
    }

    public static PingableManager getInstance(){
        if(instance == null){
            instance = new PingableManager();
        }
        return instance;
    }

    public void add(IPingable pingable){
        this.pingableList.add(pingable);
    }

    public LinkedList<IPingable> getPingableList(){
        return this.pingableList;
    }


}

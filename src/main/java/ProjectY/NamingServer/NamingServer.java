package ProjectY.NamingServer;

import ProjectY.Files.FileLog;
import ProjectY.HttpComm.HttpModule;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Vector;

import static java.lang.Math.abs;
@Service
public class NamingServer {
    private final Map map = new Map();
    private static String IP = "172.30.0.5";
    private HttpModule httpModule = new HttpModule(this);
    public NamingServer() {
    }
    public String addNode(String name, String ipAddress){
        return map.addNode(Hash(name),ipAddress);
    }
    public String removeNode(String name){
        return map.removeNode(Hash(name));
    }
    public String getIP(String name){
        return map.getIP(Hash(name));
    }

    public String getIPId(int Id){
        return map.getIP(Id);
    }

    public int getNodeSize(){
        return map.getSize();
    }
    public Vector<String> getIPlist(){
        return map.getIPlist();
    }
    public String locate(String fileName){
        System.out.println("Hash: "+Hash(fileName));
        return map.findClosestIP(Hash(fileName));
    }
    public int Hash(String name){
        double max = 2147483647;
        double min = -2147483647;
        return (int)((name.hashCode()+max)*(32768/(max+abs(min))));
    };
    public int getSize(){
        return map.getSize();
    };
    public void printServer(){
        map.printMap();
    }

    public int getPreviousId(int Id) {
        return map.getPreviousId(Id);
    }
    public int getNextId(int Id) {
        return map.getNextId(Id);
    }

    public String getReplicatedIP(int fileID){
        int replicatedOwner = map.getPreviousId(fileID);
        String replicatedOwnerIP = getIPId(replicatedOwner);
        return replicatedOwnerIP;
    }
}

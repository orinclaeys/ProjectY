package ProjectY.NamingServer;

import ProjectY.Files.FileLog;
import ProjectY.HttpComm.HttpModule;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import static java.lang.Math.abs;
@Service
public class NamingServer {
    private HttpModule httpModule = new HttpModule(this);
    private final Map map = new Map();
    private static String IP = "172.30.0.5";

    public NamingServer() {
    }

    public String addNode(String name, String ipAddress){
        return map.addNode(Hash(name),ipAddress);
    }
    public String getIP(String name){
        return map.getIP(Hash(name));
    }
    public String getIP(int nodeID){return map.getIP(nodeID);}
    public String getIPId(int Id){
        return map.getIP(Id);
    }
    public Vector<String> getIPlist(){
        return map.getIPlist();
    }
    public int getNextId(int Id) {
        return map.getNextId(Id);
    }
    public int getNodeSize(){
        return map.getSize();
    }
    public int getPreviousId(int Id) {
        return map.getPreviousId(Id);
    }
    public String getReplicatedIP(int fileID){return map.getReplicationIP(fileID);}
    public int getSize(){
        return map.getSize();
    };
    /**
     * Calculates the hash based on the node name
     *
     * @param name the name of the node
     * @return the calculated hash
     */
    public int Hash(String name){
        double max = 2147483647;
        double min = -2147483647;
        return (int)((name.hashCode()+max)*(32768/(max+abs(min))));
    }
    public String locate(String fileName){
        return map.findClosestIP(Hash(fileName));
    }
    public void printServer(){
        map.printMap();
    }
    public String removeNode(String name){
        return map.removeNode(Hash(name));
    }
    public String removeNode(int nodeID){return map.removeNode(nodeID);}
}

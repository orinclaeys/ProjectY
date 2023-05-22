package ProjectY.NamingServer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

import static java.lang.Math.abs;

public class Map {
    private HashMap<Integer,String> map = new HashMap<>();
    private Vector<String> IPlist = new Vector<>();
    public Map() {
        this.loadMap();
    }
    public void loadMap(){
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("src/main/java/ProjectY/NamingServer/NameFile.json");
        try{
            this.map=mapper.readValue(file, new TypeReference<HashMap<Integer, String>>() {
            });
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void saveMap(){
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File("src/main/java/ProjectY/NamingServer/NameFile.json"), map);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public String addNode(Integer ID, String ipAddress){
        String result;
        if(map.containsKey(ID)){
            result="Error: Node is already added";
        }else {
            map.put(ID, ipAddress);
            saveMap();
            result="Node added succesfully";
            IPlist.add(ipAddress);
        }
        return result;
    }
    public String removeNode(Integer ID){
        String result;
        if(map.containsKey(ID)) {
            map.remove(ID);
            for(int i=0;i<IPlist.size();i++){
               if(IPlist.get(i)==getIP(ID)){
                    IPlist.remove(i);
                }
            }
            saveMap();
            result="Node deleted succesfully";
        }else{
            result="Error: No such Node";
        }
        return result;
    }
    public String getIP(Integer ID){
        return map.get(ID);
    }
    public int getSize(){
        return map.size();
    }
    public String findClosestIP(int hash){
        Object[] keys =  map.keySet().toArray();
        Integer ID = 0;
        Integer diff = 100000000;
        for(int i=0;i<keys.length;i++){
            if(diff>abs(hash-(Integer)keys[i])){
                diff=abs(hash-(Integer)keys[i]);
                ID=(Integer)keys[i];
            }
        }
        return map.get(ID);
    }
    public void printMap(){
        System.out.println("Printing map {");
        map.forEach((key,value) -> System.out.println(key + " = " + value));
        System.out.println("}");
    }

    public int getPreviousId(int Id) {
        Object[] keys = map.keySet().toArray();
        int previousId = 0;
        for(int i=0;i<keys.length;i++){
            if (((Integer) keys[i] < Id) && (previousId < (Integer) keys[i])) {
                previousId = (Integer) keys[i];
            }
        }
        if (Id == 0) {
            previousId = 32768;
        }
        return previousId;
    }
    public int getNextId(int Id) {
        Object[] keys = map.keySet().toArray();
        int nextId = 32768;
        for(int i=0;i<keys.length;i++){
            if (((Integer) keys[i] > Id) && (nextId > (Integer) keys[i])) {
                nextId = (Integer) keys[i];
            }
        }
        if (Id == 32768) {
            nextId = 0;
        }
        return nextId;
    }

    public Vector<String> getIPlist(){return IPlist;}

    public Vector<String> getReplicationList(int fileID){
        Vector<String> replicationOwners = new Vector<>();
        Object[] keys = map.keySet().toArray();
        for(int i=0;i<keys.length;i++){
            if ((Integer) keys[i] < fileID) {
                replicationOwners.add(map.get((Integer) keys[i]));
            }
        }
        return replicationOwners;
    }
}

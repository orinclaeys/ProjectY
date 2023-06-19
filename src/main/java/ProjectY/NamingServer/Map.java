package ProjectY.NamingServer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static java.lang.Math.abs;

public class Map {
    private HashMap<Integer,String> map = new HashMap<>();
    private Vector<String> IPlist = new Vector<>();
    public Map() {this.loadMap();}
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
            for(int i=0;i<IPlist.size();i++){
                if(Objects.equals(IPlist.get(i), getIP(ID))){
                    IPlist.remove(i);
                }
            }
            map.remove(ID);
            saveMap();
            result="Node deleted succesfully";
        }else{
            result="Error: No such Node";
        }
        return result;
    }
    public String getIP(Integer ID){return map.get(ID);}
    public int getSize(){return map.size();}
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
        Arrays.stream(keys).sorted();
        int previousId = 0;
        for(int i=0;i<keys.length;i++){
            if((Integer) keys[i] == Id){
                if (i == 0) {
                    previousId = (int) keys[keys.length-1];
                }
                else{
                    previousId = (Integer) keys[i-1];
                }
            }
        }
        return previousId;
    }
    public int getNextId(int Id) {
        Object[] keys = map.keySet().toArray();
        Arrays.stream(keys).sorted();
        int nextId = 32768;
        for(int i=0;i<keys.length;i++){
            if((Integer) keys[i] == Id){
                if (i == keys.length-1) {
                    nextId = (int) keys[0];
                }
                else{
                    nextId = (Integer) keys[i+1];
                }
            }
        }
        return nextId;
    }

    public Vector<String> getIPlist(){return IPlist;}

    public String getReplicationIP(int fileID){
        String IP = null;
        Object[] keys = map.keySet().toArray();
        Arrays.sort(keys);
        for (int i=0;i<keys.length;i++){
            if((Integer) keys[i]>fileID){
                if(i==0){
                    IP= map.get((Integer) keys[keys.length-1]);
                }else{
                    IP=map.get((Integer) keys[i-1]);
                }
            }
        }
        return IP;
    }

}

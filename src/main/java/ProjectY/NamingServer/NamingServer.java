package ProjectY.NamingServer;

import org.springframework.stereotype.Service;

import java.io.IOException;

import static java.lang.Math.abs;
@Service
public class NamingServer {
    private final Map map = new Map();

    public NamingServer() {}
    public String addNode(String name, String ipAddress){
        return map.addNode(Hash(name),ipAddress);
    }
    public String removeNode(String name){
        return map.removeNode(Hash(name));
    }
    public String getIP(String name){
        return map.getIP(Hash(name));
    }
    public int getNodeSize(){
        return map.getSize();
    }
    public String locate(String fileName){
        System.out.println("Hash: "+Hash(fileName));
        return map.findClosestIP(Hash(fileName));
    }
    private int Hash(String name){
        int max = 2147483647;
        int min = -2147483647;
        return (name.hashCode()+max)*(32768/(max+abs(min)));
    };
    public void printServer(){
        map.printMap();
    }


}

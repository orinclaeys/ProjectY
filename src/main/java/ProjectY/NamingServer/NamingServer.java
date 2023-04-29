package ProjectY.NamingServer;

import ProjectY.Multicast.MulticastModule;
import ProjectY.Multicast.MulticastModuleServer;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static java.lang.Math.abs;
@Service
public class NamingServer {
    private final Map map = new Map();
    private MulticastModule multicastModule;
    private String IP = "192.168.1.1";

    public NamingServer() {
        try {
            this.multicastModule = new MulticastModuleServer(this);
            new Thread(this.multicastModule).start();
        } catch (IOException e) {
            System.out.println("NamingServer: Error creating MulticastModule: "+e);
        }
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
    public String getServerIP(){return this.IP;}
    public String locate(String fileName){
        System.out.println("Hash: "+Hash(fileName));
        return map.findClosestIP(Hash(fileName));
    }
    public int Hash(String name){
        int max = 2147483647;
        int min = -2147483647;
        return (name.hashCode()+max)*(32768/(max+abs(min)));
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

}

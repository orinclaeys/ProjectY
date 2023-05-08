package ProjectY.NamingServer;

import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class NamingServerService extends Thread{
    private NamingServer server;

    public NamingServerService(NamingServer server) {this.server = server;}

    public String AddNode(String name,String IPAddress){return this.server.addNode(name,IPAddress);}
    public String DeleteNode(String name){return this.server.removeNode(name);}
    public String GetIPAddress(String name){return this.server.getIP(name);}
    public String GetIPAddressId(int Id){return this.server.getIPId(Id);}
    public String LocateIP(String name){return  this.server.locate(name);}

    public JSONObject handleDiscovery(JSONObject message) {
        JSONObject response = new JSONObject();
        response.put("Sender", "NamingServer");
        response.put("IPlist", server.getIPlist());
        response.put("Message", AddNode(message.get("Name").toString(), message.get("IPAddress").toString()));
        response.put("Size", server.getSize());
        return response;
    }


    public JSONObject handleFailure(String nodeName){
        JSONObject response = new JSONObject();
        int Id = this.server.Hash(nodeName);
        response.put("previousId", this.server.getPreviousId(Id));
        response.put("previousIP", this.server.getIPId(this.server.getPreviousId(Id)));
        response.put("nextId", this.server.getNextId(Id));
        response.put("nextIP", this.server.getIPId(this.server.getNextId(Id)));
        DeleteNode(nodeName);
        return response;
    }
}

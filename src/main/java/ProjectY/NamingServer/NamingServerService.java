package ProjectY.NamingServer;

import ProjectY.Files.FileLog;
import ProjectY.NamingServer.NamingServer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Vector;
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


    public JSONObject handleFailure(JSONObject message){
        int Id = (int) message.get("Failed node Id");
        String nodeName = (String) message.get("Failed node name");

        JSONObject response = new JSONObject();
        response.put("failedNodeId", Id);
        response.put("previousId", this.server.getPreviousId(Id));
        response.put("previousIP", this.server.getIPId(this.server.getPreviousId(Id)));
        response.put("nextId", this.server.getNextId(Id));
        response.put("nextIP", this.server.getIPId(this.server.getNextId(Id)));
        DeleteNode(nodeName);
        return response;
    }

    public void handleReplication(JSONObject message){
        if (message.get("Sender").equals("Client")){
            if (message.get("Message").equals("Replication")){
                Vector<FileLog> fileLogList = new Vector<>();
                JSONArray fileLogListJSON = ((JSONArray) message.get("FileLogList"));
                for (Object o : fileLogListJSON) {
                    fileLogList.add(new FileLog((JSONObject) o));
                }

                for (FileLog fileLog : fileLogList) {
                    server.replication(fileLog);
                    System.out.println(" ");
                }
            }
            if (message.get("Message").equals("Replication Response")){
                // MOET NOG AFGEWERKT WORDEN
            }
        }
    }

    public String GetPreviousIPAddressId(int Id){
        return this.server.getIPId(this.server.getPreviousId(Id));
    }
}

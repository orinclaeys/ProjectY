package ProjectY.NamingServer;

import ProjectY.Files.FileLog;
import ProjectY.HttpComm.HttpModule;
import ProjectY.NamingServer.NamingServer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.CompletableFuture;

public class NamingServerService extends Thread{
    private NamingServer server;
    private HttpModule httpModule =new HttpModule(this.server);
    public NamingServerService(NamingServer server) {this.server = server;}

    public String AddNode(String name,String IPAddress){return this.server.addNode(name,IPAddress);}
    public String DeleteNode(String name){return this.server.removeNode(name);}
    public String GetIPAddress(String name){return this.server.getIP(name);}
    public String GetIPAddressId(int Id){return this.server.getIPId(Id);}
    public String LocateIP(String name){return  this.server.locate(name);}

    /**
     * Handles discovery response: Auto-discover the Naming server and existing nodes in the network
     * The Naming server receives the multicast message and executes the following steps:
     *  Calculate the hash of a node name
     *  Adds the hash and IP address in its map data structure
     *  Responds to the new node with the number of existing nodes that are currently in the network
     *
     * @param message the received message
     * @response response the sender and IPList, message and size
     */
    public JSONObject handleDiscovery(JSONObject message) {
        JSONObject response = new JSONObject();
        Vector<String> IPlist = (Vector<String>) server.getIPlist().clone();
        response.put("Sender", "NamingServer");
        response.put("IPlist", IPlist);
        response.put("Message", AddNode(message.get("Name").toString(), message.get("IPAddress").toString()));
        response.put("Size", server.getSize());
        System.out.println("Server: Discovery response: "+response);
        return response;
    }

    /**
     * Failure: This algorithm is activated in every exception thrown during communication
     * with other nodes. This allows distributed detection of node failure
     * Request the previous node and next node parameters from the nameserver
     *
     * @param nodeID the node ID
     */

    public void handleFailure(int nodeID){
        int previousID = server.getPreviousId(nodeID);
        String previousIP = server.getIP(previousID);
        int nextID = server.getNextId(nodeID);
        String nextIP = server.getIP(nextID);
        httpModule.updatePreviousID(nextIP,previousID);
        httpModule.updateNextID(previousIP,nextID);

        server.removeNode(nodeID);

    }

    /**
     * Replication: All files that are stored on each node should be replicated to corresponding nodes in system Y.
     * This way, a new node to which the file is replicated becomes the owner of the file.
     * Naming server receives hash values of files that need to be replicated. Replication is performed as follows:
     * Naming server informs the originating node on that and then the originating node transfers the files to the replicated nodes.
     *
     * @param message sender, message and file id
     * @response sender, message, replicated owner ip
     */
    public JSONObject handleReplication(JSONObject message){
        JSONObject response = new JSONObject();
        if (message.get("Sender").equals("Client")){
            if (message.get("Message").equals("Replication")){
                int fileID = (int) message.get("fileID");
                String replicatedOwnerIP = server.getReplicatedIP(fileID);

                response.put("Sender", "NamingServer");
                response.put("Message", "Replication");
                response.put("ReplicatedOwnerIP", replicatedOwnerIP);
            }
        }
        return response;
    }

    public String GetPreviousIPAddressId(int Id){
        int previousID = server.getPreviousId(Id);
        System.out.println("Previous Id of "+Id+" is "+previousID);
        return this.server.getIPId(this.server.getPreviousId(Id));
    }

    public Integer handleNextIDRequest(int Id){
        return this.server.getNextId(Id);
    }
}

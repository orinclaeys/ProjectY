package ProjectY.NamingServer;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="ProjectY")
public class RESTControllerServer {
    private final NamingServer server = new NamingServer();
    @Autowired
    public RESTControllerServer() {}
    @GetMapping(path="NamingServer/getLocation/{Name}")
    public String getIPAddress(@PathVariable("Name") String Name){
        NamingServerService service = new NamingServerService(this.server);
        return service.LocateIP(Name);
    }
    @PostMapping(path="Discovery")
    public JSONObject Discovery(@RequestBody JSONObject message){
        System.out.println("Server: Discovery received: "+message);
        NamingServerService service = new NamingServerService(this.server);
        return service.handleDiscovery(message);
    }

    @DeleteMapping(path="NamingServer/deleteNode/{nodeName}")
    public String deleteNode(@PathVariable("nodeName") String nodeName){
        NamingServerService service = new NamingServerService(this.server);
        return service.DeleteNode(nodeName);
    }

    @GetMapping(path="NamingServer/getIPAddress/{Id}")
    public String getIPAddress(@PathVariable("Id") int Id){
        NamingServerService service = new NamingServerService(this.server);
        return service.GetIPAddressId(Id);
    }

    @GetMapping(path="NamingServer/getPreviousIPAddress/{Id}")
    public String getPreviousIPAddress(@PathVariable("Id") int Id){
        NamingServerService service = new NamingServerService(this.server);
        return service.GetPreviousIPAddressId(Id);
    }

    @DeleteMapping(path="Failure/{nodeID}")
    public void failure(@PathVariable("nodeID") int nodeID){
        NamingServerService service = new NamingServerService(this.server);
        service.handleFailure(nodeID);
    }

    @PostMapping(path="NamingServer/replication")
    public JSONObject replication(@RequestBody JSONObject message) {
        NamingServerService service = new NamingServerService(this.server);
        System.out.println("Server: Replication received: "+message);
        JSONObject response = service.handleReplication(message);
        System.out.println("Server: Replication response: "+response);
        return response;
    }

    @GetMapping(path="NamingServer/FailureAgent/sendNextIDRequest/{ID}")
    public Integer handleNextIDRequest(@PathVariable("ID") int ID){
        NamingServerService service = new NamingServerService(this.server);
        return service.handleNextIDRequest(ID);
    }
}

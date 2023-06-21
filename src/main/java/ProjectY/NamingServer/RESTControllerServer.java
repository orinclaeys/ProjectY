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

    @PostMapping(path="Discovery")
    public JSONObject Discovery(@RequestBody JSONObject message){
        NamingServerService service = new NamingServerService(this.server);
        return service.handleDiscovery(message);
    }
    @DeleteMapping(path="NamingServer/deleteNode/{nodeName}")
    public String deleteNode(@PathVariable("nodeName") String nodeName){
        NamingServerService service = new NamingServerService(this.server);
        return service.DeleteNode(nodeName);
    }
    @DeleteMapping(path="Failure/{nodeID}")
    public void failure(@PathVariable("nodeID") int nodeID){
        NamingServerService service = new NamingServerService(this.server);
        service.handleFailure(nodeID);
    }
    @GetMapping(path="NamingServer/getIPAddress/{Id}")
    public String getIPAddress(@PathVariable("Id") int Id){
        NamingServerService service = new NamingServerService(this.server);
        return service.GetIPAddressId(Id);
    }
    @GetMapping(path="NamingServer/getLocation/{Name}")
    public String getIPAddress(@PathVariable("Name") String Name){
        NamingServerService service = new NamingServerService(this.server);
        return service.LocateIP(Name);
    }
    @GetMapping(path="NamingServer/getPreviousIPAddress/{Id}")
    public String getPreviousIPAddress(@PathVariable("Id") int Id){
        NamingServerService service = new NamingServerService(this.server);
        return service.GetPreviousIPAddressId(Id);
    }
    @GetMapping(path="NamingServer/FailureAgent/sendNextIDRequest/{ID}")
    public Integer handleNextIDRequest(@PathVariable("ID") int ID){
        NamingServerService service = new NamingServerService(this.server);
        return service.handleNextIDRequest(ID);
    }
    @PostMapping(path="NamingServer/replication")
    public JSONObject replication(@RequestBody JSONObject message) {
        NamingServerService service = new NamingServerService(this.server);
        JSONObject response = service.handleReplication(message);
        return response;
    }
}

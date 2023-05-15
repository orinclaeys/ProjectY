package ProjectY.NamingServer;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="ProjectY")
public class RESTControllerServer {
    private NamingServer server = new NamingServer();
    @Autowired
    public RESTControllerServer() {}
    @GetMapping(path="NamingServer/getLocation/{Name}")
    public String getIPAddress(@PathVariable("Name") String Name){
        NamingServerService service = new NamingServerService(this.server);
        return service.LocateIP(Name);
    }
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

    @GetMapping(path="NamingServer/getIPAddress/{Id}")
    public String getIPAddress(@PathVariable("Id") int Id){
        NamingServerService service = new NamingServerService(this.server);
        return service.GetIPAddressId(Id);
    }

    @PostMapping(path="Failure")
    public JSONObject failure(@RequestBody JSONObject message){
        NamingServerService service = new NamingServerService(this.server);
        return service.handleFailure(message);
    }

    @PostMapping(path="NamingServer/replication")
    public void replication(@RequestBody JSONObject message) {
        NamingServerService service = new NamingServerService(this.server);
        service.handleReplication(message);
    }

    @PostMapping(path="NamingServer/replicationResponse")
    public void replicationResponse(@RequestBody JSONObject response) {
        NamingServerService service = new NamingServerService(this.server);
        service.handleReplication(response);
    }
}

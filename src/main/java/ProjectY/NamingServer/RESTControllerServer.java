package ProjectY.NamingServer;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;

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

    @PostMapping(path="Failure")
    public JSONObject failure(@RequestBody JSONObject message){
        NamingServerService service = new NamingServerService(this.server);
        return service.handleFailure(message);
    }

    @PostMapping(path="NamingServer/replication")
    public JSONObject replication(@RequestBody JSONObject message) {
        NamingServerService service = new NamingServerService(this.server);
        System.out.println("Server: Replication received: "+message);
        return service.handleReplication(message);
    }
}

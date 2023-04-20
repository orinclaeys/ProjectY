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
    @PostMapping(path="Discovery/{nodeName}/{IPAddress}")
    public String addNode(@PathVariable("nodeName") String nodeName,@PathVariable("IPAddress") String IPAddress){
        NamingServerService service = new NamingServerService(this.server);
        return service.AddNode(nodeName,IPAddress);
    }
    @DeleteMapping(path="NamingServer/deleteNode/{nodeName}")
    public String deleteNode(@PathVariable("nodeName") String nodeName){
        NamingServerService service = new NamingServerService(this.server);
        return service.DeleteNode(nodeName);
    }
}

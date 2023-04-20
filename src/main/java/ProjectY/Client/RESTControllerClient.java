package ProjectY.Client;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "ProjectY")

public class RESTControllerClient {

    private Client client = new Client();
    @Autowired
    public RESTControllerClient() {}

    @PutMapping(path = "Discovery/{nodeName}/{IPAddress}")
    public JSONObject discovery(@PathVariable("nodeName") String nodeName, @PathVariable("IPAddress") String IPAddress) {
        ClientService clientService = new ClientService(this.client);
        return clientService.handleDiscovery(nodeName);
    }

    @PutMapping(path = "Shutdown/{nodeName}/{IPAddress}")
    public JSONObject shutdown(@PathVariable("nodeName") String nodeName, @PathVariable("IPAddress") String IPAddress) {
        ClientService clientService = new ClientService(this.client);
        return clientService.shutdown(nodeName);
    }


}

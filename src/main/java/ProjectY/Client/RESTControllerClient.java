package ProjectY.Client;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "ProjectY")

public class RESTControllerClient {

    private Client client = new Client();
    @Autowired
    public RESTControllerClient() {}

    @GetMapping(path = "Discovery/{nodeName}/{IPAddress}")
    public JSONObject discovery(@PathVariable("nodeName") String nodeName, @PathVariable("IPAddress") String IPAddress) {
        ClientService clientService = new ClientService(this.client);
        return clientService.handleDiscovery(nodeName);
    }

}

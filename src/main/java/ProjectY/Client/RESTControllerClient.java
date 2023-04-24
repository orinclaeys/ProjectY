package ProjectY.Client;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.ref.Cleaner;

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
    public void shutdown(@PathVariable("nodeName") String nodeName, @PathVariable("IPAddress") String IPAddress) throws IOException, InterruptedException {
        ClientService clientService = new ClientService(this.client);
        client.shutdown();
    }

    @PutMapping(path = "Update/{nodeName}")
    public JSONObject update(@PathVariable("nodeName") String nodeName) {
        ClientService clientService = new ClientService(this.client);
        return clientService.update(nodeName);
    }

    @PutMapping("{IPAddress}/ProjectY/Shutdown/PreviousNode")
    public void shutdownPreviousNode(@PathVariable("IPAddress") String IPAddress) {
        ClientService clientService = new ClientService(this.client);

    }
}

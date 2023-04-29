package ProjectY.Client;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.ref.Cleaner;

@RestController
@RequestMapping(path = "ProjectY")

public class RESTControllerClient {

    private Client client = new Client("TestClient");

    @Autowired
    public RESTControllerClient() {}

    @PostMapping(path = "DiscoveryRespons")
    public void discoveryRespons(@RequestBody JSONObject respons) {
        ClientService clientService = new ClientService(this.client);
        System.out.println("Discovery Respons received");
        clientService.handleDiscoveryRespons(respons);
    }

    @PutMapping(path = "Shutdown/{nodeName}/{IPAddress}")
    public void shutdown(@PathVariable("nodeName") String nodeName, @PathVariable("IPAddress") String IPAddress) throws IOException, InterruptedException {
        ClientService clientService = new ClientService(this.client);
        client.shutdown();
    }

/*
    @PutMapping(path = "Update/{nodeName}")
    public JSONObject update(@PathVariable("nodeName") String nodeName) {
        ClientService clientService = new ClientService(this.client);
        return clientService.update(nodeName);
    }
*/

    @PutMapping("Shutdown/PreviousNode/{NextId}")
    public void shutdownPreviousNode(@PathVariable("NextId") int NextId) {
        ClientService clientService = new ClientService(this.client);
    }

    @PutMapping("Shutdown/NextNode/{PreviousId}")
    public void shutdownNextNode(@PathVariable("Previous") int PreviousId) {
        ClientService clientService = new ClientService(this.client);
    }



}

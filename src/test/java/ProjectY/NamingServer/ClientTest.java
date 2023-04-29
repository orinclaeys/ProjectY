package ProjectY.NamingServer;

import ProjectY.Client.Client;
import ProjectY.Multicast.MulticastModuleClient;
import ProjectY.Multicast.MulticastModuleServer;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class ClientTest {
    @Test
    public void DiscoveryTest() throws Exception{
        Client client1 = new Client("Node1");
        client1.print();
        client1.Discovery();
        client1.print();
    }
    @Test
    public void MulticastTest() throws IOException {
        NamingServer server = new NamingServer();
        Client client = new Client("Node1");
    }
}

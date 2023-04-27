package ProjectY.NamingServer;

import ProjectY.Client.Client;
import org.junit.jupiter.api.Test;

public class ClientTest {
    @Test
    public void DiscoveryTest() throws Exception{
        Client client = new Client("Node1");
        client.Discovery();

    }
}

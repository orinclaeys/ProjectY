package ProjectY.NamingServer;

import ProjectY.Client.Client;
import org.junit.jupiter.api.Test;

public class ClientTest {
    @Test
    public void DiscoveryTest() throws Exception{
        Client client = new Client("Node1");
        client.Discovery();
    }

    @Test
    public void shutdownTest() throws Exception{
        Client client1 = new Client("Node1");
        Client client2 = new Client("Node2");
        Client client3 = new Client("Node3");
        client2.shutdown();


    }

}

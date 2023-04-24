package ProjectY.NamingServer;

import ProjectY.Client.Client;
import org.junit.jupiter.api.Test;

public class ClientTest {
    @Test
    public void DiscoveryTest() throws Exception{
        Client client1 = new Client("Node1");
        client1.print();
        client1.Discovery();
        client1.print();

    }
}

package ProjectY.NamingServer;

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
        //NamingServer server = new NamingServer();
        //server.run();
        Client client = new Client("Node1");
    }
}

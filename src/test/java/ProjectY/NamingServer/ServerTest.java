package ProjectY.NamingServer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServerTest {
    @Test
    public void AddRemoveTest() throws Exception{
        NamingServer server = new NamingServer();
        server.addNode("Node1","192.168.1.1");
        server.addNode("Node2","192.168.1.2");
        assertEquals(server.getIP("Node1"),"192.168.1.1");
        assertEquals(server.getIP("Node2"),"192.168.1.2");
        server.printServer();
        server.removeNode("Node1");
        server.printServer();
    }
    @Test
    public void locateTest() throws Exception{
        NamingServer server = new NamingServer();
        server.addNode("Node1","192.168.1.1");
        server.addNode("Node2","192.168.1.2");
        System.out.println(server.locate("File1"));
        server.printServer();
    }

}

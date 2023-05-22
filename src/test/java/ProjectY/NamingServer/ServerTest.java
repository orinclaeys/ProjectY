package ProjectY.NamingServer;


import ProjectY.Files.FileLog;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.Vector;

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

/*    @Test
    public void replicationTest() throws Exception{
        NamingServer server = new NamingServer();
        FileLog fileLog = new FileLog("Test",7);
        server.replication(fileLog);
    }*/

    @Test
    public void replicationTest2() throws Exception{
        NamingServer server = new NamingServer();
        NamingServerService service = new NamingServerService(server);
        FileLog fileLog1 = new FileLog("Test",7);
        FileLog fileLog2 = new FileLog("Test2",4);
        JSONArray fileLogList = new JSONArray();

        JSONObject message = new JSONObject();
        message.put("Sender", "Client");
        message.put("Message", "Replication");
        message.put("FileLogList", fileLogList);
        service.handleReplication(message);
    }

/*    @Test
    public void MapperTest(){
        FileLog fileLog = new FileLog("test",2);
        JSONArray replicatedOwners = new JSONArray();
        replicatedOwners.addAll(fileLog.getReplicatedOwners());

        JSONObject message = new JSONObject();
        message.put("replicatedOwners",replicatedOwners);
        System.out.println(message);


    }*/


}

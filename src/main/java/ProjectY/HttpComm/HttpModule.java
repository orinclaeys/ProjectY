package ProjectY.HttpComm;

import ProjectY.NamingServer.NamingServer;
import org.json.simple.JSONObject;

public class HttpModule {
    private NamingServer server;

    public HttpModule(NamingServer server) {this.server = server;}

    public void replication(JSONObject message, String ip) {
        System.out.println(message+" send to "+ ip);
    }
}

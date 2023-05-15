package ProjectY.HttpComm;

import ProjectY.NamingServer.NamingServer;
import org.json.simple.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.time.Duration;

public class HttpModule {
    private NamingServer server;

    public HttpModule(NamingServer server) {this.server = server;}

    public void replication(JSONObject message, String ip) {
        System.out.println(message+" send to "+ ip);
    }

}

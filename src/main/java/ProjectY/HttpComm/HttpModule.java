package ProjectY.HttpComm;

import ProjectY.NamingServer.NamingServer;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class HttpModule {
    private NamingServer server;

    public HttpModule(NamingServer server) {this.server = server;}

    public void replication(JSONObject message, String ip) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://"+ip+":8080/ProjectY/Client/replication"))
                .POST(HttpRequest.BodyPublishers.ofString(message.toJSONString()))
                .header("Content-type", "application/json")
                .timeout(Duration.ofSeconds(1000))
                .build();
        try {
            client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateNextID(String IP,int nextID){
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://"+IP+":8081/ProjectY/Update/PreviousNode/"+nextID))
                .PUT(HttpRequest.BodyPublishers.noBody())
                .build();
        try {
            client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void updatePreviousID(String IP, int previousID){
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://"+IP+":8081/ProjectY/Update/NextNode/"+previousID))
                .PUT(HttpRequest.BodyPublishers.noBody())
                .build();
        try {
            client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

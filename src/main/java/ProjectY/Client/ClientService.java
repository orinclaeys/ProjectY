package ProjectY.Client;

import ProjectY.NamingServer.NamingServer;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ClientService extends Thread {

    private Client client;

    public ClientService(Client client) {this.client = client;}

    public JSONObject handleDiscovery(String name) {
        JSONObject response = new JSONObject();

        if (client.updateNextID(name)) {
            response.put("update", true);
            response.put("currentID", client.getCurrentId());
            response.put("nextID", client.getNextId());
        }
        else if (client.updatePreviousID(name)) {
            response.put("update", true);
            response.put("currentID", client.getCurrentId());
            response.put("previousID", client.getPreviousId());
        }
        else {
            response.put("update", false);
        }
        return response;
    }

    public void shutdown(String name) throws IOException, InterruptedException {
        HttpClient httpclient = HttpClient.newHttpClient();
        HttpRequest requestPreviousNode = HttpRequest.newBuilder()
                .uri(URI.create("localhost:8080/projectY/Shutdown/PreviousNode"))
                .build();

        HttpResponse<String> responsePreviousNode =
                httpclient.send(requestPreviousNode, HttpResponse.BodyHandlers.ofString());

        HttpRequest requestNextNode = HttpRequest.newBuilder()
                .uri(URI.create("localhost:8080/projectY/Shutdown/NextNode"+client.getNextId()))
                .build();

        HttpResponse<String> responseNextNode =
                httpclient.send(requestNextNode, HttpResponse.BodyHandlers.ofString());
    }

}

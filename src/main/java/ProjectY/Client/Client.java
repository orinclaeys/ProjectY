package ProjectY.Client;

import ProjectY.NamingServer.NamingServer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.PrimitiveIterator;

import static java.lang.Math.abs;

public class Client {
    private int PreviousId = 1;
    private int NextId = 1;
    private int CurrentId;
    private String name;
    private String IPAddres;

    public Client( String name) {
        CurrentId = Hash(name);
        this.name = name;
        this.IPAddres = "192.168.1.1";
    }

    public Client() {
    }


    public boolean updateNextID(String ID){
        int NewId = Hash(ID);
        if( (CurrentId < NewId) & (NewId <NextId )){
            setNextId(NewId);
            return true;
        }
        else{
            return false;
        }
        };
    public boolean updatePreviousID(String ID) {
        int NewId = Hash(ID);
        if ((PreviousId < NewId) & (NewId < CurrentId)) {
            setPreviousId(NewId);
            return true;
        }
        else {
            return false;
        }
    }

    public int getPreviousId() {
        return PreviousId;
    }

    public void setPreviousId(int previousId) {
        PreviousId = previousId;
    }

    public int getNextId() {
        return NextId;
    }

    public void setNextId(int nextId) {
        NextId = nextId;
    }

    public int getCurrentId() {
        return CurrentId;
    }

    public void setCurrentId(int currentId) {
        CurrentId = currentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private int Hash(String name){
        int max = 2147483647;
        int min = -2147483647;
        return (name.hashCode()+max)*(32768/(max+abs(min)));
    };

    public void shutdown() throws IOException, InterruptedException {
        HttpClient httpclient = HttpClient.newHttpClient();

        // Get the IP of the previous node.
        HttpRequest requestPreviousIPAddress = HttpRequest.newBuilder()
                .uri(URI.create("localhost:8080/ProjectY/NamingServer/getIPAddress/"+getPreviousId()))
                .build();
        HttpResponse<String> responsePreviousIPAddress =
                httpclient.send(requestPreviousIPAddress, HttpResponse.BodyHandlers.ofString());

        // Get the IP of the next node.
        HttpRequest requestNextIPAddress = HttpRequest.newBuilder()
                .uri(URI.create("localhost:8080/ProjectY/NamingServer/getIPAddress/"+getNextId()))
                .build();
        HttpResponse<String> responseNextIPAddress =
                httpclient.send(requestNextIPAddress, HttpResponse.BodyHandlers.ofString());

        // Send the ID of the next node to the previous node.
        HttpRequest requestPreviousNode = HttpRequest.newBuilder()
                .uri(URI.create(responsePreviousIPAddress+":8080/ProjectY/Update/PreviousNode/"+getNextId()))
                .build();
        HttpResponse<String> responsePreviousNode =
                httpclient.send(requestPreviousNode, HttpResponse.BodyHandlers.ofString());

        // Send the ID of the previous node to the next node.
        HttpRequest requestNextNode = HttpRequest.newBuilder()
                .uri(URI.create(responseNextIPAddress+":8080/ProjectY/Update/NextNode/"+getPreviousId()))
                .build();
        HttpResponse<String> responseNextNode =
                httpclient.send(requestNextNode, HttpResponse.BodyHandlers.ofString());

        // Remove the node from the Naming server’s Map.
        HttpRequest requestDeleteNode = HttpRequest.newBuilder()
                .uri(URI.create("localhost:8080/ProjectY/NamingServer/deleteNode"+this.name))
                .build();
        HttpResponse<String> responseDeleteNode =
                httpclient.send(requestDeleteNode, HttpResponse.BodyHandlers.ofString());
    }

    public void failure(String nodeName) throws IOException, InterruptedException {
        HttpClient httpclient = HttpClient.newHttpClient();

        // Get the IP and ID of the previous and next nodes.
        HttpRequest requestFailure = HttpRequest.newBuilder()
                .uri(URI.create("localhost:8080/ProjectY/NamingServer/failure/"+nodeName))
                .build();
        HttpResponse<String> response =
                httpclient.send(requestFailure, HttpResponse.BodyHandlers.ofString());

        JSONObject message = new ObjectMapper().readValue(response.body(), JSONObject.class);

        // Send the ID of the next node to the previous node.
        HttpRequest requestPreviousNode = HttpRequest.newBuilder()
                .uri(URI.create(message.get("previousIP")+":8080/ProjectY/Update/PreviousNode/"+message.get("nextId")))
                .build();
        HttpResponse<String> responsePreviousNode =
                httpclient.send(requestPreviousNode, HttpResponse.BodyHandlers.ofString());

        // Send the ID of the previous node to the next node.
        HttpRequest requestNextNode = HttpRequest.newBuilder()
                .uri(URI.create(message.get("nextIP")+":8080/ProjectY/Update/NextNode/"+message.get("previousId")))
                .build();
        HttpResponse<String> responseNextNode =
                httpclient.send(requestNextNode, HttpResponse.BodyHandlers.ofString());
    }
    public void Discovery(){
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/ProjectY/Discovery/"+this.name+"/"+this.IPAddres))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject message = new ObjectMapper().readValue(response.body(), JSONObject.class);
            System.out.println(message);

            if(message.get("Sender").equals("NamingServer")){
                if(message.get("size").equals("1")){
                    System.out.println("First node in Network");
                    this.setPreviousId(this.getCurrentId());
                    this.setNextId(this.getCurrentId());
                }else{
                    System.out.println("Networksize>1");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

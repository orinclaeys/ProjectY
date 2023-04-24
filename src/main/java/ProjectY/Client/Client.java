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

        HttpRequest requestPreviousNode = HttpRequest.newBuilder()
                .uri(URI.create("/ProjectY/Shutdown/PreviousNode/"+getPreviousId()))
                .build();

        HttpResponse<String> responsePreviousNode =
                httpclient.send(requestPreviousNode, HttpResponse.BodyHandlers.ofString());

        HttpRequest requestNextNode = HttpRequest.newBuilder()
                .uri(URI.create("/ProjectY/Shutdown/NextNode/"+getNextId()))
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

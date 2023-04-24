package ProjectY.Client;

import ProjectY.NamingServer.NamingServer;

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

    public Client( String name) {
        CurrentId = Hash(name);
        this.name = name;
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

        HttpRequest requestPreviousIPAddress = HttpRequest.newBuilder()
                .uri(URI.create("/ProjectY/NamingServer/getIPAddress/"+getPreviousId()))
                .build();

        HttpResponse<String> responsePreviousIPAddress =
                httpclient.send(requestPreviousIPAddress, HttpResponse.BodyHandlers.ofString());

        HttpRequest requestNextIPAddress = HttpRequest.newBuilder()
                .uri(URI.create("/ProjectY/NamingServer/getIPAddress/"+getPreviousId()))
                .build();

        HttpResponse<String> responseNextIPAddress =
                httpclient.send(requestNextIPAddress, HttpResponse.BodyHandlers.ofString());

        HttpRequest requestPreviousNode = HttpRequest.newBuilder()
                .uri(URI.create(responsePreviousIPAddress+"/ProjectY/Shutdown/PreviousNode/"+getPreviousId()))
                .build();

        HttpResponse<String> responsePreviousNode =
                httpclient.send(requestPreviousNode, HttpResponse.BodyHandlers.ofString());

        HttpRequest requestNextNode = HttpRequest.newBuilder()
                .uri(URI.create(responseNextIPAddress+"/ProjectY/Shutdown/NextNode/"+getNextId()))
                .build();

        HttpResponse<String> responseNextNode =
                httpclient.send(requestNextNode, HttpResponse.BodyHandlers.ofString());
    }
}

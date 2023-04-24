package ProjectY.Client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static java.lang.Math.abs;

public class Client {
    private int previousID;
    private int nextID;
    private int currentID;
    private String name;
    private String IPAddres;

    public Client( String name) {
        this.currentID = Hash(name);
        this.previousID = this.currentID;
        this.nextID = this.currentID;
        this.name = name;
        this.IPAddres = "192.168.1.1";
    }

    public Client() {}


    public boolean updateNextID(String name){
        int newID = Hash(name);
        if(this.currentID ==this.nextID){
         if(newID>this.currentID){
             this.nextID = newID;
             return true;
         }else{
             return false;
         }
        }else {
            if ((this.currentID < newID) & (newID < this.nextID)) {
                setNextId(newID);
                return true;
            } else {
                return false;
            }
        }
    }
    public boolean updatePreviousID(String name) {
        int newID = Hash(name);
        if(this.currentID==this.previousID){
            if(newID < this.currentID){
                this.previousID = newID;
                return true;
            }else{
                return false;
            }
        }else {
            if ((this.previousID < newID) & (newID < this.currentID)) {
                setPreviousId(newID);
                return true;
            } else {
                return false;
            }
        }
    }

    public int getPreviousId() {return previousID;}
    public void setPreviousId(int previousId) {
        previousID = previousId;}
    public int getNextId() {return nextID;}
    public void setNextId(int nextId) {
        nextID = nextId;}
    public int getCurrentId() {return currentID;}
    public void setCurrentId(int currentId) {
        currentID = currentId;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    private int Hash(String name){
        double max = 2147483647;
        double min = -2147483647;
        return (int) ((name.hashCode()+max)*(32768/(max+abs(min))));
    }

    public void shutdown() throws IOException, InterruptedException {
        HttpClient httpclient = HttpClient.newHttpClient();

        HttpRequest requestPreviousIPAddress = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/ProjectY/NamingServer/getIPAddress/"+ getPreviousId()))
                .build();

        HttpResponse<String> responsePreviousIPAddress =
                httpclient.send(requestPreviousIPAddress, HttpResponse.BodyHandlers.ofString());

        HttpRequest requestNextIPAddress = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/ProjectY/NamingServer/getIPAddress/"+ getNextId()))
                .build();

        HttpResponse<String> responseNextIPAddress =
                httpclient.send(requestNextIPAddress, HttpResponse.BodyHandlers.ofString());
        System.out.println(responseNextIPAddress.body());

        /*HttpRequest requestPreviousNode = HttpRequest.newBuilder()
                .uri(URI.create("http://"+responsePreviousIPAddress.body()+":8080/ProjectY/Shutdown/PreviousNode/"+ getNextId()))
                .build();

        HttpResponse<String> responsePreviousNode =
                httpclient.send(requestPreviousNode, HttpResponse.BodyHandlers.ofString());

        HttpRequest requestNextNode = HttpRequest.newBuilder()
                .uri(URI.create("http://"+responseNextIPAddress.body()+":8080/ProjectY/Shutdown/NextNode/"+ getPreviousId()))
                .build();

        HttpResponse<String> responseNextNode =
                httpclient.send(requestNextNode, HttpResponse.BodyHandlers.ofString());

        HttpRequest requestDeleteNode = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/ProjectY/NamingServer/deleteNode"+this.name))
                .build();

        HttpResponse<String> responseDeleteNode =
                httpclient.send(requestDeleteNode, HttpResponse.BodyHandlers.ofString());*/
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
                if(message.get("Message").equals("Node added succesfully")) {
                    if (message.get("Size").equals(1)) {
                        System.out.println("First node in Network");
                        this.setPreviousId(this.getCurrentId());
                        this.setNextId(this.getCurrentId());
                    } else {
                        System.out.println("Networksize>1");
                    }
                }
                if(message.get("Message").equals("Error: Node is already added")){
                    System.out.println("Error: Name is already registered at NamingServer");
                }
            }

            if(message.get("Sender").equals("Client")){
                if(message.get("Update").equals(true)){
                    this.setPreviousId((Integer) message.get("YourPreviousID"));
                    this.setNextId((Integer) message.get("YourNextID"));
                }
            }






        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void print(){
        System.out.println("Client");
        System.out.println("-------------------");
        System.out.println("Name: "+this.name);
        System.out.println("IP-Addres: "+this.IPAddres);
        System.out.println("ID: "+this.currentID);
        System.out.println("NextID: "+this.nextID);
        System.out.println("PreviousID: "+this.previousID);
        System.out.println("-------------------");
    }
}

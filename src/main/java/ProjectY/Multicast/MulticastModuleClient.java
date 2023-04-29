package ProjectY.Multicast;

import ProjectY.Client.Client;
import ProjectY.Client.ClientService;
import ProjectY.NamingServer.NamingServer;
import ProjectY.NamingServer.NamingServerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class MulticastModuleClient extends MulticastModule{
    private final Client client;

    public MulticastModuleClient(Client client) throws IOException {
        this.multicastAddress = InetAddress.getByName("225.225.225.225");
        this.port = 2001;
        this.socket = new MulticastSocket(this.port);
        this.socket.joinGroup(this.multicastAddress);
        this.client = client;
    }
    public void sendMulticast(JSONObject message){
        String Stringmessage = message.toJSONString();
        byte[] data = Stringmessage.getBytes();

        //DatagramPacket packet1 = new DatagramPacket(data, data.length, this.multicastAddress, this.port-1);
        DatagramPacket packet2 = new DatagramPacket(data, data.length, this.multicastAddress, this.port);
        try {
            //this.socket.send(packet1);
            this.socket.send(packet2);
            System.out.println("Client: Message send to"+this.multicastAddress.toString()+":"+this.port);
            //System.out.println("Client: Message send to"+this.multicastAddress.toString()+":"+(this.port-1));
        } catch (IOException e) {
            System.out.println("MulticastModule: Error while sending message: "+e);
        }
    }
    @Override
    public void run(){
        System.out.println("Client: Listening for multicast on port: "+this.port);
        try {
            while (true) {
                byte[] data = new byte[2047];
                DatagramPacket packet = new DatagramPacket(data, data.length);
                this.socket.receive(packet);
                String packetString = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Client: multicast received");
                System.out.println(packetString);
                ClientService service = new ClientService(this.client);
                ObjectMapper mapper = new ObjectMapper();
                JSONObject message = mapper.readValue(packetString, JSONObject.class);
                System.out.println("converted to Json");
                JSONObject response = service.handleDiscovery((String) message.get("Name"));

                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("http://"+message.get("IPAddress")+":8080/ProjectY/DiscoveryRespons/"))
                        .POST(HttpRequest.BodyPublishers.ofString(response.toJSONString()))
                        .build();
                client.send(request, HttpResponse.BodyHandlers.ofString());
            }
        }catch (IOException e){
            System.out.println("MulticastModule: Error while receiving: "+e);
        } catch (InterruptedException e) {
            System.out.println("MulticastModule: Error while sending respons");
        }

    }
}


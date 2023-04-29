package ProjectY.Multicast;

import ProjectY.NamingServer.NamingServer;
import ProjectY.NamingServer.NamingServerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class MulticastModuleServer extends MulticastModule{
    private final DatagramSocket socket;
    private final NamingServer server;

    public MulticastModuleServer(NamingServer server) throws IOException {
        this.multicastAddress = InetAddress.getByName("255.255.255.255");
        this.port = 2000;
        this.socket = new DatagramSocket(this.port);
        this.server = server;
    }

    public void sendMulticast(JSONObject message){
        String Stringmessage = message.toJSONString();
        byte[] data = Stringmessage.getBytes();

        DatagramPacket packet = new DatagramPacket(data, data.length, this.multicastAddress, this.port+1);
        try {
            this.socket.send(packet);
            System.out.println("Server: Message send to 255.255.255.255:"+this.port+1);
        } catch (IOException e) {
            System.out.println("MulticastModule: Error while sending message: "+e);
        }
    }
    @Override
    public void run(){
        System.out.println("Server: Listening for multicast on port: "+this.port);
        try {
            while (true) {
                byte[] data = new byte[2047];
                DatagramPacket packet = new DatagramPacket(data, data.length);
                this.socket.receive(packet);
                String packetString = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Server: Multicast received");
                System.out.println(packetString);
                NamingServerService service = new NamingServerService(this.server);
                ObjectMapper mapper = new ObjectMapper();
                JSONObject message = mapper.readValue(packetString, JSONObject.class);
                System.out.println("converted to Json");
                JSONObject response = service.handleDiscovery((String) message.get("Name"), (String) message.get("IPAddress"));

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

package ProjectY.NamingServer;

import org.json.simple.JSONObject;

public class NamingServerService extends Thread{
    private NamingServer server;

    public NamingServerService(NamingServer server) {this.server = server;}

    public String AddNode(String name,String IPAddress){return this.server.addNode(name,IPAddress);}
    public String DeleteNode(String name){return this.server.removeNode(name);}
    public String GetIPAddress(String name){return this.server.getIP(name);}
    public String GetIPAddressId(int Id){return this.server.getIPId(Id);}
    public String LocateIP(String name){return  this.server.locate(name);}

    public JSONObject handleDiscovery(String name, String IPAddress){
        System.out.println(AddNode(name, IPAddress));
        JSONObject response = new JSONObject();
        response.put("Sender","NamingServer");
        response.put("Size",server.getSize());
        return response;
    }
}

package ProjectY.Client;

import ProjectY.NamingServer.NamingServer;
import org.json.simple.JSONObject;

public class ClientService extends Thread {

    private Client client;

    public ClientService(Client client) {this.client = client;}

    public JSONObject handleDiscovery(String name) {
        JSONObject response = new JSONObject();

        if (client.updateNextID(name)) {
            response.put("update", true);
            response.put("currentID", client.getCurrentId());
            response.put("nextID", client.getNextID());
        }
        else if (client.updatePreviousID(name)) {
            response.put("update", true);
            response.put("currentID", client.getCurrentID);
            response.put("previousID", client.getPreviousID());
        }
        else {
            response.put("update", false);
        }
        return response;
    }

}

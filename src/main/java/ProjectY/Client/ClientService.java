package ProjectY.Client;

import ProjectY.NamingServer.NamingServer;
import org.json.simple.JSONObject;

public class ClientService extends Thread {

    private Client client;

    public ClientService(Client client) {this.client = client;}

    public JSONObject handleDiscovery(String name) {
        JSONObject response = new JSONObject();
        int currentID = Hash(name);

        if (client.updateNextID(currentID)) {
            response.put("update", true);
            response.put("currentID", currentID);
            response.put("nextID", client.getNextID());
        }
        else if (client.updatePreviousID(currentID)) {
            response.put("update", true);
            response.put("currentID", currentID);
            response.put("previousID", client.getPreviousID());
        }
        else {
            response.put("update", false);
        }
        return response;
    }

}

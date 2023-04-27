package ProjectY.Client;

import org.json.simple.JSONObject;


public class ClientService extends Thread {

    private Client client;

    public ClientService(Client client) {this.client = client;}

    public JSONObject handleDiscovery(String name) {
        JSONObject response = new JSONObject();
        response.put("Sender","Client");
        if (client.updateNextID(name)) {
            response.put("Update", true);
            response.put("YourPreviousID", client.getCurrentId());
            response.put("YourNextID", client.getNextId());
        }
        else if (client.updatePreviousID(name)) {
            response.put("Update", true);
            response.put("YourNextID", client.getCurrentId());
            response.put("YourPreviousID", client.getPreviousId());
        }
        else {
            response.put("Update", false);
        }
        return response;
    }



}

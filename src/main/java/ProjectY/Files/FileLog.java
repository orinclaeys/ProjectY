package ProjectY.Files;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Map;
import java.util.Vector;

public class FileLog {
    private String fileName;
    private int fileID;
    private int owner;
    private Vector<Integer> replicatedOwners = new Vector<>();
    private Vector<String> downloadLocations = new Vector<>();

    public FileLog(String fileName, int fileID) {
        this.fileName = fileName;
        this.fileID = fileID;
    }
    public FileLog(JSONObject fileLog){
        this.fileName = (String) fileLog.get("fileName");
        this.fileID = (Integer) fileLog.get("fileID");
        this.owner = (Integer) fileLog.get("owner");
        this.replicatedOwners.addAll((JSONArray) fileLog.get("replicatedOwners"));
        this.downloadLocations.addAll((JSONArray) fileLog.get("downloadLocations"));
    }
    public void setOwner(int ownerID) {this.owner = ownerID;}
    public int getOwner(){return owner;}
    public int getFileID(){return fileID;}

    public JSONObject toJSON(){
        JSONObject response = new JSONObject();
        response.put("fileName",fileName);
        response.put("fileID",fileID);
        response.put("owner",owner);
        JSONArray replicatedOwnerJSON = new JSONArray();
        replicatedOwnerJSON.addAll(replicatedOwners);
        response.put("replicatedOwners",replicatedOwnerJSON);
        JSONArray downloadLocationsJSON = new JSONArray();
        replicatedOwnerJSON.addAll(downloadLocations);
        response.put("downloadLocations",downloadLocationsJSON);
        return response;
    }

}


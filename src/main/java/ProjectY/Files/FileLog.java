package ProjectY.Files;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;

public class FileLog {
    private int fileID;
    private String fileName;
    private int owner;
    private String replicatedOwner;

    public FileLog(String fileName, int fileID) {
        this.fileName = fileName;
        this.fileID = fileID;
    }

    public FileLog(JSONObject fileLog){
        this.fileName = (String) fileLog.get("fileName");
        this.fileID = (Integer) fileLog.get("fileID");
        this.owner = (Integer) fileLog.get("owner");
        this.replicatedOwner = (String) fileLog.get("replicatedOwner");
    }

    public int getFileID(){return fileID;}
    public String getFileName() {return fileName;}
    public int getOwner(){return owner;}
    public String getReplicatedOwner() {return replicatedOwner;}

    public void setOwner(int ownerID) {this.owner = ownerID;}
}


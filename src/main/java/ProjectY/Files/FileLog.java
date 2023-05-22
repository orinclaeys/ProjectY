package ProjectY.Files;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;

public class FileLog {
    private String fileName;
    private int fileID;
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
    public void setOwner(int ownerID) {this.owner = ownerID;}
    public int getOwner(){return owner;}
    public int getFileID(){return fileID;}
    public String getFileName() {return fileName;}
    public String getReplicatedOwner() {return replicatedOwner;}
}


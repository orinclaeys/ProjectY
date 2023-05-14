package ProjectY.Files;

import java.util.Map;
import java.util.Vector;

public class FileLog {
    private String fileName;
    private int fileID;
    private int owner;
    private Vector<Integer> replicatedOwners;
    private boolean replicated=false;
    private Vector<String> downloadLocations;

    public FileLog(String fileName, int fileID) {
        this.fileName = fileName;
        this.fileID = fileID;
    }
    public void setOwner(int ownerID) {this.owner = ownerID;}
    public int getOwner(){return owner;}
    public int getFileID(){return fileID;}

}


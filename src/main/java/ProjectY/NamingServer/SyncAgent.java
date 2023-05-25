package ProjectY.NamingServer;

import java.io.File;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

public class SyncAgent {

    private List<String> fileList;

    public void updateList(List<String> fileListNextNode) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

            }
        }, 0, 5000);
    }

    public void addFile(String fileName) {
        this.fileList.add(fileName);
    }

    public void removeFile(String fileName) {
        this.fileList.remove(fileName);
    }
}

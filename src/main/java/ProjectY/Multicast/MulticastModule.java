package ProjectY.Multicast;

import org.json.simple.JSONObject;

import java.net.DatagramSocket;
import java.net.InetAddress;

public abstract class MulticastModule implements Runnable {
    public InetAddress multicastAddress;
    public DatagramSocket socket;
    public int port;
    abstract public void sendMulticast(JSONObject message);
    @Override
    abstract public void run();
}

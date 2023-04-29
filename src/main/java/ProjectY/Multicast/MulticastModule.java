package ProjectY.Multicast;

import org.json.simple.JSONObject;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public abstract class MulticastModule implements Runnable {
    public InetAddress multicastAddress;
    public MulticastSocket socket;
    public int port;
    abstract public void sendMulticast(JSONObject message);
    @Override
    abstract public void run();
}

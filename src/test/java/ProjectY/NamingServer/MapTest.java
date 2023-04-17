package ProjectY.NamingServer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapTest {
    @Test
    public void AddRemoveTest() throws Exception{
        Map map = new Map();
        map.addNode(5,"192.168.1.1");
        map.addNode(10,"192.168.1.2");
        map.printMap();
        map.removeNode(5);
        map.printMap();
    }
    @Test
    public void findClosestTest() throws Exception{
        Map map = new Map();
        map.addNode(5,"192.168.1.1");
        map.addNode(10,"192.168.1.2");
        assertEquals(map.findClosestIP(6),"192.168.1.1");
        assertEquals(map.findClosestIP(3),"192.168.1.1");
        assertEquals(map.findClosestIP(8),"192.168.1.2");
        assertEquals(map.findClosestIP(12),"192.168.1.2");
    }


}

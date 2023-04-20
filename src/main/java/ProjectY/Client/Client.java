package ProjectY.Client;

import ProjectY.NamingServer.NamingServer;

import java.util.PrimitiveIterator;

import static java.lang.Math.abs;

public class Client {
    private int PreviousId;
    private int NextId;
    private int CurrentId;
    private String name;

    public Client(int previousId, int nextId, int currentId, String name) {
        PreviousId = previousId;
        NextId = nextId;
        CurrentId = currentId;
        this.name = name;
    }

    public Client() {
    }


    public boolean UpdateNextID(String id){
        int NewId = Hash(id);
        if( (CurrentId < NewId) & (NewId <NextId )){
            setNextId(NewId);
            return true;
        }
        else{
            return false;
        }
        };
    public boolean UpdatePreviousID(String id) {
        int NewId = Hash(id);
        if ((PreviousId < NewId) & (NewId < CurrentId)) {
            setPreviousId(NewId);
            return true;
        }
        else {
            return false;
        }
    }

    public int getPreviousId() {
        return PreviousId;
    }

    public void setPreviousId(int previousId) {
        PreviousId = previousId;
    }

    public int getNextId() {
        return NextId;
    }

    public void setNextId(int nextId) {
        NextId = nextId;
    }

    public int getCurrentId() {
        return CurrentId;
    }

    public void setCurrentId(int currentId) {
        CurrentId = currentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private int Hash(String name){
        int max = 2147483647;
        int min = -2147483647;
        return (name.hashCode()+max)*(32768/(max+abs(min)));
    };


}

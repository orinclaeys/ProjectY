package ProjectY.Files;

import ProjectY.Client.Client;

import java.nio.file.Path;

import static java.lang.Math.abs;

public class Files {

    public void replicas(String nodename, Path pathname){

    }

    public void localFiles(String filename, Path pathname){


    }

    public int Hash(String name){
        int max = 2147483647;
        int min = -2147483647;
        return (name.hashCode()+max)*(32768/(max+abs(min)));
    };
}

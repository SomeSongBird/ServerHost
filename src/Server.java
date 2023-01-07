import java.lang.ProcessBuilder;
import java.io.*;

public class Server{
    ProcessBuilder pb;
    String startCommand;
    String exitCommand;
    String name;

    public Server(String name, String startingCommand,String exitCommand){
        this.name = name;
        this.startCommand = startingCommand;
        this.exitCommand = exitCommand;
    }

    public void start(){
        try{
            Process p = new ProcessBuilder(startCommand).start();
            System.out.println("Started Server");
        }catch(Exception e){
            System.out.println("oopsie woopsie, there was a little fucky wucky");
            System.out.println(e.getMessage());
        }
    }
    public void stop(){
        try{
            Process exitProcess = new ProcessBuilder(exitCommand).start();
            exitProcess.waitFor();
        }catch(Exception e){
            System.out.println("oopsie woopsie, there was a little fucky wucky");
            System.out.println(e.getMessage());
        }
    }
}
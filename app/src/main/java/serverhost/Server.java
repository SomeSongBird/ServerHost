package serverhost;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.lang.ProcessBuilder;
//import java.io.*;

public class Server{
    private String startCommand;
    private String exitCommand;
    private Process process;
    public boolean running;
    public String name;

    public Server(String name, String startingCommand,String exitCommand){
        this.name = name;
        this.startCommand = startingCommand;
        this.exitCommand = exitCommand;
        this.running = false;
    }

    public void start(){
        try{
            String[] start = startCommand.split(" ");
            process = new ProcessBuilder(start).start();
            this.running = true;
            System.out.println("Started Server");
        }catch(Exception e){
            System.err.println("oopsie woopsie, there was a little fucky wucky");
            System.err.println(e.getMessage());
        }
    }

    public void checkForStopped(){
        if(!process.isAlive()){
            this.running = false;
        }
    }

    public BufferedInputStream getInputStream(){
        return new BufferedInputStream(process.getInputStream());
    }
    public BufferedOutputStream getOutputStream(){
        return new BufferedOutputStream(process.getOutputStream());
    }

    public void stop(){
        try{
            if(exitCommand.equals("")){
                //process.destroy();
                //this.running = false;  
                System.out.println("No exit script available, close server through command inputs");  
                return;
            }
            String[] exit = exitCommand.split(" ");
            Process exitProcess = new ProcessBuilder(exit).start();
            exitProcess.waitFor();
            this.running = false;
        }catch(Exception e){
            System.out.println("oopsie woopsie, there was a little fucky wucky");
            System.out.println(e.getMessage());
        }
    }

    public String toString(){
        return name+","+startCommand+","+exitCommand;
    }
}
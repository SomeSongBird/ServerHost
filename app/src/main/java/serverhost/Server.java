package serverhost;

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
            process = new ProcessBuilder(startCommand).start();
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

    public void stop(){
        try{
            if(exitCommand.equals("none")){
                //process.destroy();
                //this.running = false;    
                return;
            }
            Process exitProcess = new ProcessBuilder(exitCommand).start();
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
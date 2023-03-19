package serverhost;

import java.io.*;
import java.lang.ProcessBuilder;

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

    public InputStreamReader getInputStream(){
        return new InputStreamReader(process.getInputStream());
    }
    public OutputStream getOutputStream(){
        return process.getOutputStream();
    }

    public boolean checkAlive(){
        if(process!=null){
            return(process.isAlive());
        }
        return false;
    }

    public boolean stop(){
        if(!checkAlive()){
            this.running = false;
            return true;
        }
        try{
            if(exitCommand.equals("")){
                //process.destroy();
                //this.running = false;  
                System.out.println("No exit script available, close server through command inputs");  
                return false;
            }
            String[] exit = exitCommand.split(" ");
            Process exitProcess = new ProcessBuilder(exit).start();
            exitProcess.waitFor();
            this.running = false;
            return true;
        }catch(Exception e){
            System.out.println("oopsie woopsie, there was a little fucky wucky");
            System.out.println(e.getMessage());
            return false;
        }
    }

    public void forceStop(){
        process.destroyForcibly();
    }

    public String toString(){
        return name+","+startCommand+","+exitCommand;
    }
}
package serverhost;

import java.util.Scanner;
import java.io.*;

public class ServerContainer{
    static String serverListLocation = "C:/Users/ferchrj/Desktop/ServerList.txt";
    public Server[] serverList; 

    public ServerContainer(){
        readList();
    }

    private void readList(){
        try{
            Scanner fr = new Scanner(new File(serverListLocation));
            String info;
            this.serverList = new Server[0];
            while(fr.hasNextLine()){
                info = fr.nextLine();
                if(info=="") continue;
                // if(info.charAt(0)=='#') continue; // comments
                readServerInfo(info);
            }
            fr.close();
        }catch(Exception e){
            System.err.println("Error reading server list");
            System.err.println(e.getMessage());
            return;
        }
    }
    
    private void readServerInfo(String serverInfo){
        //System.out.println(serverInfo);
        String[] serverInfoList = serverInfo.strip().split(",");
        String serverName = serverInfoList[0];
        String startCommand = serverInfoList[1];
        String exitCommand = "";
        if(serverInfoList.length>=3){
            exitCommand = serverInfoList[2];
        }
        
        Server s = new Server(serverName,startCommand,exitCommand);
        if(s!=null){
            Server[] tmp = new Server[this.serverList.length+1];
            for(int i = 0;i<this.serverList.length;i++){
                tmp[i] = this.serverList[i];
            }
            tmp[tmp.length-1] = s;
            this.serverList = tmp;
            return;
        }
    }
    
    private void writeList(){
        try{
            FileWriter fw = new FileWriter(new File(serverListLocation));
            for(Server server : serverList){
                fw.write(server.toString()+"\n");
            }
            fw.close();
        }catch(Exception e){
            System.err.println("Error writing server list");
            System.err.println(e.getMessage());
        }
    }

    public void shutdown(){
        for(Server server : serverList){
            if(server.running){
                if(!server.stop()){
                    server.forceStop();
                }
            }
        }
        writeList();
    }

    public boolean addNewServer(String name,String start,String exit){
        try{
            Server newServer = new Server(name, start, exit);
            Server[] newList = new Server[serverList.length+1];
            for(int i = 0;i<newList.length;i++){
                newList[i] = serverList[i];
            }
            newList[newList.length-1] = newServer;
            serverList = newList;
        }catch(Exception e){
            return false;
        }
        return true;
    }
}
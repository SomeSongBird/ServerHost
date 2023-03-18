package serverhost;

import java.util.Scanner;
import java.io.*;

public class ServerContainer{
    static String serverListLocation = "ServerList.txt";
    public Server[] serverList; 

    public ServerContainer(){
        readList();
    }

    public void readList(){
        try{
            Scanner fr = new Scanner(new File(serverListLocation));
            String info;
            this.serverList = new Server[0];
            while(fr.hasNextLine()){
                info = fr.nextLine();
                readServerInfo(info);
            }
            fr.close();
        }catch(Exception e){
            System.out.println("Error reading server list");
            System.out.println(e.getMessage());
            return;
        }
    }

    public void readServerInfo(String serverInfo){
        //System.out.println(serverInfo);
        String[] serverInfoList = serverInfo.strip().split(",");
        String startCommand = serverInfoList[1];
        String exitCommand = serverInfoList[2];
        
        Server s = new Server(serverInfoList[0],startCommand,exitCommand);

        Server[] tmp = new Server[this.serverList.length+1];
        int i = 0;
        for(;i<this.serverList.length;i++){
            tmp[i] = this.serverList[i];
        }
        tmp[i] = s;
        this.serverList = tmp;
    }

    public boolean addNewServer(String name,String start,String exit){
        String serverInfo = name+","+start+","+exit+"\n";
        
        String newServs = "";
        boolean set = false;
        try{
            Scanner fr = new Scanner(new File(serverListLocation));
            while(fr.hasNextLine()){
                String servs = fr.nextLine()+"\n";
                if((int)Character.toLowerCase(serverInfo.charAt(0))<(int)Character.toLowerCase(servs.charAt(0)) & !set){
                    set = true;
                    newServs+=serverInfo;
                }
                newServs+=servs;
            }
            if(!set) newServs+=serverInfo;
            fr.close();
            
            FileWriter fw = new FileWriter(serverListLocation);
            fw.write(newServs);
            fw.close();

            readList();
        }catch(Exception e){
            System.out.println("Error updating server list");
            return false;
        }
        return true;
    }
}
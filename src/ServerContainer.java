import java.util.Scanner;
import java.util.Arrays;
import java.io.*;

public class ServerContainer{
    String serverListLocation = "ServerList.txt";
    Server[] serverList = new Server[0]; 

    public ServerContainer(){
        readList();
    }

    public void readList(){
        try{
            Scanner fr = new Scanner(new File(serverListLocation));
            String info;
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
        System.out.println(serverInfo);
        String[] serverInfoList = serverInfo.strip().split(",");

        String[] cmds = Arrays.copyOfRange(serverInfoList,4,serverInfoList.length);
        //System.out.println(cmds[0]);
        Server s = new Server(serverInfoList[0],cmds,serverInfoList[1],serverInfoList[2],serverInfoList[3]);

        Server[] tmp = new Server[this.serverList.length+1];
        int i = 0;
        for(;i<this.serverList.length;i++){
            tmp[i] = this.serverList[i];
        }
        tmp[i] = s;
        this.serverList = tmp;
    }

    public boolean addNewServer(String name,String commands,String inputSource,String outputDump,String errorDump){
        if(inputSource=="") inputSource = "none";
        if(outputDump=="") outputDump = name+"_outut.txt";
        if(errorDump=="") errorDump = name+"_errors.txt";
        commands = commands.strip().replaceAll("\n",",");
        String serverInfo = name+","+inputSource+","+outputDump+","+errorDump+","+commands+"\n";
        

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
import java.util.Scanner;

public class ServerContainer{
    String serverListLocation = "";
    Server[] serverList; 


    public ServerContainer(){
        File file = new File(serverListLocation);
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fis.read(data);
        fis.close();

        String str = new String(data, "UTF-8");

        str.split('\n');
        serverList = new Server[str.length];
        for(int i=0;i<serverList.length;i++){

            // format for storage will be name,input,output,error,commands as a csv

            String serverInfo = str[i].split(',');
            serverList[i] = new Server(serverInfo[0],serverInfo[4:],serverInfo[1],serverInfo[2],serverInfo[3]);
        }
    }

}
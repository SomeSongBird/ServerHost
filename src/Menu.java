import java.util.*;

public class Menu{

    public void loadMenu(){
        Scanner s = new Scanner(System.in);
        ServerContainer sc = new ServerContainer();

        System.out.println("Enter a the game you want to start\nEnter h for more options");
        boolean exit = false;
        while(s.hasNext()){
            String str = s.nextLine().strip();
            int select=0;
            try{
                select = Integer.parseInt(str)-1;
            }catch(Exception e){
                switch (str) {
                    case "h":
                    case "H":
                    case "help":
                    case "Help":
                        System.out.println("list) list available servers\nnew) add a new server to the list\nexit) close the program");
                        break;
                    case "list":
                        for(int i=0;i<sc.serverList.length;i++){
                            System.out.printf("%d:%s\n",i+1,sc.serverList[i].name);
                        }
                        break;
                    case "exit":
                        exit = true;
                        break;
                    case "new":
                        updateList(sc,s);
                        break;
                    default:
                    break;
                }
                if(exit) break;
                continue;
            }
            if(select>=0 && select<sc.serverList.length){
                System.out.println(sc.serverList[select].name);
                if(sc.serverList[select].running){
                    sc.serverList[select].stop();
                }else{
                    sc.serverList[select].start();
                }
            }else{
                System.out.println("Value not in range");
            }
        }
        s.close();
    }
    
    public void updateList(ServerContainer sc,Scanner s){
        String name = "";
        String start = "";
        String exit = "";
        
        while(name.equals("")){
            System.out.print("Server Name: ");
            name = s.nextLine();
            if(name.equals(""))System.out.println("No valid name detected");
        }

        while(start.equals("")){
            System.out.print("Server startup command or script: ");
            start = s.nextLine();
            if(start.equals(""))System.out.println("No valid startup command detected");
        }

        
        System.out.print("Server shutdown command or script (leave blank if there is none): ");
        exit = s.nextLine();

        sc.addNewServer(name,start,exit);
    }

    public static void main(String[]args){
        Menu m = new Menu();
        //m.updateList();
        m.loadMenu();
    }
}
import java.util.*;
import java.lang.ProcessBuilder;
import java.lang.Thread;

public class Menu{

    public void loadMenu(){
        Scanner s = new Scanner(System.in);
        ServerContainer sc = new ServerContainer();

        System.out.println("Enter a the game you want to start");
        while(true){
            String str = s.nextLine().strip();
            int select=0;
            try{
                select = Integer.parseInt(str)-1;
            }catch(Exception e){
                if(str.toLowerCase().equals("exit"))break;
                else continue;
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

    public void updateList(){
        String name = "Bestguy";
        String start = "notepad.exe";
        String exit = "none";

        ServerContainer sc = new ServerContainer();
        sc.addNewServer(name,start,exit);
    }

    public static void main(String[]args){
        Menu m = new Menu();
        m.updateList();
        m.loadMenu();
    }
}
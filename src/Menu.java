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
            int select = Integer.parseInt(str)-1;
            if(select>=0 && select<sc.serverList.length){
                System.out.println(sc.serverList[select].name);
                sc.serverList[select].changeState();
            }else{
                System.out.println("Value not in range");
            }
        }
    }

    public void updateList(){
        String name = "Bestguy";
        String commands = "notepad.exe\nnotepad.exe";
        String input = "";
        String output = "";
        String error = "";

        ServerContainer sc = new ServerContainer();
        sc.addNewServer(name,commands,input,output,error);
    }

    public static void main(String[]args){
        Menu m = new Menu();
        m.updateList();
        m.loadMenu();
    }
}
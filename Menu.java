import java.util.Scanner;
import java.lang.ProcessBuilder;

public class Menu{
    String noteCMD = "notepad.exe";
    String valheimCommand = "valheim_server";
    String[] valheimArgs = {"-nographics","-batchmode","-name \"Pswerver\"", "-port 2456","-world \"The Famhous\"", "-password \"Daisy\"","-crossplay"};

    public void loadMenu(){
        Scanner s = new Scanner(System.in);
        Server note = new Server(noteCMD);

        System.out.print("Enter a the game you want to start");
        System.out.println("1. notepad");
        while(true){
            String str = s.nextLine().strip();
            switch(str){
                case "1" : 
                    note.changeState();
                    break;
                default:
                    System.out.println("Error");
            }
        }
    }

    public static void main(String[]args){
        Menu m = new Menu();
        m.loadMenu();
    }
}
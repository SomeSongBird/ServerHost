package serverhost.command_line;

import serverhost.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.*;
//import java.util.regex.*;

public class Menu{

//#region init
    Scanner userInput = new Scanner(System.in);
    ServerContainer serverContainer;
    public Menu(ServerContainer sc){
        serverContainer = sc;
    }
//#endregion

//#region helpers

    private String greenColorCode = "\033[92m";
    private String redColorCode = "\033[91m";
    private String defaultColor = "\033[0m";

    private boolean cancel(String input){
        String ui = input.toLowerCase();
        if(ui.equals("cancel")||ui.equals("exit")){
            return true;
        }
        return false;
    }

    private String getUserInput(){
        String in = userInput.nextLine().strip();
        return in;
    }

    public static void waitForNextKeystroke(){
        try{
            while(System.in.available()==0){
                Thread.sleep(100);
            }
            System.in.read();
        }catch(Exception e){}
    }

    private static void clearScreen() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    } 
//#endregion helpers

//#region menus
    public void intro(){
        clearScreen();
        System.out.println("Welcome to ServerHost: the host of all your servers!");
        System.out.print("Press Enter to start");
        waitForNextKeystroke();
        mainMenu();
    }

    public void mainMenu(){
        clearScreen();
        boolean cont = true;
        while(cont){
            displayServers();
            cont = mainOptions();
            clearScreen();
        }
    }

    private void displayServers(){
        System.out.println("------ ServerHost Managed Servers ------");
        int index = 1;
        for(Server server : serverContainer.serverList){
            String running = (server.running)? greenColorCode+"{Running}"+defaultColor : redColorCode+"{Not Running}"+defaultColor;
            System.out.println("  "+(index++)+": "+server.name+"|"+running);
        }
    }

    private boolean mainOptions(){
        System.out.println("------------------------------------------");
        while(true){
            String input = getUserInput().trim().toLowerCase();
            String[] inputs = input.split(" ");
            switch(inputs[0]){
                case "h":
                case "help":
                case "options":
                    System.out.println("help                -> display vailable options");
                    System.out.println("start <serverID>    -> start the server listed at the serverID");
                    System.out.println("stop <serverID>     -> stop the server listed at the serverID");
                    System.out.println("update <serverID>   -> update the details about the listed serverID");
                    System.out.println("command <serverID>  -> open the command line for the listed server");
                    System.out.println("newServer           -> add a new server to the server list");
                    System.out.println("quit                -> shutdown all servers and close ServerHost");
                    System.out.println("------------------------------------------");
                    break;
                case "start":
                    try{
                        int serverID = Integer.parseInt(inputs[1]);
                        System.out.println("input: "+serverID);
                        if(serverID>0 && serverID<=serverContainer.serverList.length){
                            if(!serverContainer.serverList[serverID-1].running){
                                serverContainer.serverList[serverID-1].start();
                            }else{
                                System.out.println("Server is already running");
                            }
                        }else{
                            System.out.println("Server ID not found");
                        }
                    }catch(Exception e){
                        System.out.println("Entered ID isn't a number");
                    }
                    waitForNextKeystroke();
                    return true;
                case "stop":
                    try{
                        int serverID = Integer.parseInt(inputs[1]);
                        if(serverID>0 && serverID<=serverContainer.serverList.length){
                            if(serverContainer.serverList[serverID-1].running){
                                serverContainer.serverList[serverID-1].stop();
                            }else{
                                System.out.println("Server is not running");
                            }
                        }else{
                            System.out.println("Server ID not found");
                        }
                    }catch(Exception e){
                        System.out.println("Entered ID isn't a number");
                    }
                    return true;
                case "update":
                    try{
                        int serverID = Integer.parseInt(inputs[1]);
                        if(serverID>0 && serverID<=serverContainer.serverList.length){
                            if(!serverContainer.serverList[serverID-1].running){
                                String[] serverDetails = addNewServer();
                                Server updateServer = new Server(serverDetails[0], serverDetails[1], serverDetails[2]);
                                serverContainer.serverList[serverID-1] = updateServer;
                            }else{
                                System.out.println("Cannot update server details while server is already running");
                            }
                        }else{
                            System.out.println("Server ID not found");
                        }
                    }catch(Exception e){
                        System.out.println("Entered ID isn't a number");
                    }
                    waitForNextKeystroke();
                    return true;
                case "command":
                    int serverID = Integer.parseInt(inputs[1]);
                    if(serverID>0 && serverID<=serverContainer.serverList.length){
                        if(serverContainer.serverList[serverID-1].running){
                            commandServer(serverContainer.serverList[serverID-1]);
                        }else{
                            System.out.println("Server is not running");
                        }
                    }else{
                        System.out.println("Server ID not found");
                    }
                    return true;
                case "new":
                case "new_server":
                    String[] serverDetails = addNewServer();
                    if(serverDetails!=null){
                        if(serverContainer.addNewServer(serverDetails[0], serverDetails[1], serverDetails[2])){
                            System.out.println("Successfully added new server");
                        }else{
                            System.out.println("Something went wrong adding new server");
                        }
                        waitForNextKeystroke();
                    }
                    return true;
                case "exit":
                case "shutdown":
                case "q":
                case "quit":
                    serverContainer.shutdown();
                    return false;
            }
        }
    }

    private String[] addNewServer(){
        String name = "";
        String start = "";
        String exit = "";

        while(name.equals("")){
            clearScreen();
            System.out.print("Server Name: ");
            name = getUserInput();
            name = name.trim();
            if(cancel(name)) return null;
            if(name.equals("")){
                System.out.println("No valid name detected");
                waitForNextKeystroke();
            }
        }
        
        while(start.equals("")){
            clearScreen();
            System.out.print("Server startup command or script: ");
            start = getUserInput();
            if(cancel(name)) return null;
            if(start.equals("")){
                System.out.println("No valid startup command detected");
                waitForNextKeystroke();
            }
        }
        clearScreen();
        System.out.print("Server shutdown command or script (leave blank if there is none): ");
        exit = getUserInput();
        if(cancel(name)) return null;
        return new String[]{name,start,exit};
    }

    private void commandServer(Server server){
        BufferedReader reader = new BufferedReader(server.getInputStream());
        BufferedWriter outgoing_commands = new BufferedWriter(server.getOutputStream());
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                getInputFromServer(reader);
                } catch(Exception t) { return;}
            }
        });
        t.start();
        while(true){
            String command = getUserInput();
            if(cancel(command))break;
            command = command+"\r\n";
            try {outgoing_commands.write(command);
                outgoing_commands.flush();
            } catch (Exception e) {break;}
        }
        t.interrupt();
    }

    private void getInputFromServer(BufferedReader reader){
        while(true){
            if(Thread.interrupted()) return;
            try{
                if(reader.ready()) System.out.println(reader.readLine());
                else Thread.sleep(50);
            }catch(Exception e){
                return;
            }
        }
    }

//#endregion menus
}
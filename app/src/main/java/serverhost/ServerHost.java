package serverhost;

import serverhost.command_line.*;
//import serverhost.gui.*;

public class ServerHost{
    public static void main(String[] args) {
        //check for commandline option otherwise startup the gui
        ServerContainer serverContainer = new ServerContainer();
        if(args.length>0){
            if(args[0].equals("commandLine")){
                Menu menu = new Menu(serverContainer);
                menu.intro();
            }
        }
    }
}
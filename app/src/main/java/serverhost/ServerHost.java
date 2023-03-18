package serverhost;

import serverhost.command_line.*;

public class ServerHost{

    public static void main(String[] args) {
        //check for commandline option otherwise startup the gui
        if(args[0].equals("commandLine")){
            Menu m = new Menu();
            m.loadMenu();
        }
    }
}

import java.util.List;
import java.lang.ProcessBuilder;
import java.io.File;

public class Server{
    ProcessBuilder[] pb;
    Process[] p;
    String name;

    public Server(String name, String[] cmds, String input, String output, String error){
        this.name = name;

        pb = new ProcessBuilder[cmds.length];
        p = new Process[cmds.length];
        try{
            for(int i=0;i<cmds.length;i++){
                //System.out.println(cmds[i]);
                pb[i] = new ProcessBuilder(cmds[i]);
                if(!input.equals("none")) pb[i].redirectInput(new File(input));
                if(!output.equals("none")) pb[i].redirectOutput(new File(output));
                if(!error.equals("none")) pb[i].redirectError(new File(error));
            }
        }catch(Exception e){
            System.out.println("Error creating Server object for:"+name);
        }
    }

    public void changeState(){
        try{
            for(int i=0;i<p.length;i++){
                if(p[i]==null){
                    p[i] = pb[i].start();
                    System.out.println("Started Server");
                }else{
                    p[i].destroy();
                    p[i] = null;
                    System.out.println("Stopped Server");
                }
            }
        }catch(Exception e){
            System.out.println("oopsie woopsie, there was a little fucky wucky");
            System.out.println(e.getMessage());
        }
    }
}
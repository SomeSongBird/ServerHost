import java.lang.ProcessBuilder;

public class Server{
    ProcessBuilder pb;
    Process p = null;
    
    public Server(String name, String cmd, String input, String output, String error){
        pb = new ProcessBuilder(cmd);
        pb.redirectInput(input);
        pb.redirectOutput(output);
        pb.redirectError(error);
    }
    public Server(String name, String[] cmd, String input, String output, String error){
        pb = new ProcessBuilder(cmd);
        pb.redirectInput(input);
        pb.redirectOutput(output);
        pb.redirectError(error);
    }

    public void changeState(){
        try{
            if(p==null){
                p = pb.start();
                System.out.println("Started Server");
            }
            else if(p!=null){
                p.destroy();
                p = null;
                System.out.println("Stoppeded Server");
            }
        }catch(Exception e){
            System.out.println("oopsie woopsie, there was a little fucky wucky");
        }
    }
}
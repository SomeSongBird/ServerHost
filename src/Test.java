import java.lang.ProcessBuilder;
import java.lang.Thread;

public class Test{
    public static void main(String[] args) {
        //test1();
    }
    public static void test1(){
        try{
            Process p = new ProcessBuilder("MicrosoftEdge.exe").start();
            System.out.println(p.pid());
            p.waitFor();
            System.out.println("closed");
            Thread.sleep(2000);
            Process close = new ProcessBuilder("bat.bat").start();
            close.waitFor();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
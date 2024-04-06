import java.io.IOException;

public class GrandGrandGrandChildProcess 
{
    public static void showInfo(String letter)
    {
        ProcessHandle yo = ProcessHandle.current();
        long pid = yo.pid();
        long ppid = yo.parent().get().pid();
        System.out.println(letter+" PID: "+pid+" PPID: "+ ppid);
    }

    public static void main (String[] args) throws IOException,InterruptedException
    {
        showInfo(args[0]);
    }

}

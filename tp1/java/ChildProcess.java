import java.io.IOException;
import java.util.ArrayList;

public class ChildProcess 

{
    /* Constantes*/
    static final String processB = "B";
    static final String processD = "D";
    static final String processE = "E";
    static final String processF = "F";
    static final String processG = "G";
    static final String processH = "H";
    static final String processI = "I";
    static final String processJ = "J";
    static final int time = 50000;
    /*Ruta del siguiente nivel*/
    private static final String JAVA_FILE_LOCATION="ChildProcess.java";
    /*Recursos necesarios*/
    static ArrayList<Process> childs = new ArrayList<Process>();
    
    public static void showInfo(String letter)
    {
        ProcessHandle yo = ProcessHandle.current();
        long pid = yo.pid();
        long ppid = yo.parent().get().pid();
        System.out.println(letter+" PID: "+pid+" PPID: "+ ppid);
    }

    public static void createChild(String child) throws IOException
    {
        ProcessBuilder builder = new ProcessBuilder("java", JAVA_FILE_LOCATION, child);
        builder.inheritIO();
        childs.add(builder.start());
    }

    public static void waitChilds() throws InterruptedException
    {
        for (Process c : childs) 
        {
			c.waitFor();
		}
    }

    public static void main (String[] args) throws IOException,InterruptedException
    {
        showInfo(args[0]);

        switch (args[0]) 
        {
            case processB:
                createChild(processE);
                createChild(processF);
                break;
            case processD:
            	createChild(processG);
            	break;
            case processF:
            	createChild(processH);
            	createChild(processI);
            	break;
            case processI:
            	createChild(processJ);
            	break;
            default:
            	Thread.sleep(time);
                break;
        }
        waitChilds();
    }
}


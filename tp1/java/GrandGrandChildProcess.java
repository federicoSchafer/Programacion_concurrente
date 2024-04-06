import java.io.IOException;

public class GrandGrandChildProcess 
{
    /*Arrays de los hijos de cada proceso */
    static String[] childrenOfI = {"J"};

    /* Constantes*/
    static final String processI = "I";
    /*Ruta del siguiente nivel*/
    private static final String JAVA_FILE_LOCATION="C:\\Users\\npompeo\\Documents\\TrabajoPractico01\\src\\GrandGrandGrandChildProcess.java";
    /*Recursos necesarios*/
    static Process childs[];

    public static void showInfo(String letter)
    {
        ProcessHandle yo = ProcessHandle.current();
        long pid = yo.pid();
        long ppid = yo.parent().get().pid();
        System.out.println(letter+" PID: "+pid+" PPID: "+ ppid);
    }

    public static void createChilds(int numberOfChilds, String[] children) throws IOException
    {
        ProcessBuilder builder;
        childs = new Process[numberOfChilds];
        for(int i=0;i<numberOfChilds;i++)
        {
            builder = new ProcessBuilder("java",JAVA_FILE_LOCATION,children[i]);
            builder.inheritIO();
            childs[i] = builder.start();
        }
    }

    public static void waitChilds(int numberOfChilds) throws InterruptedException
    {
        for(int i=0; i<numberOfChilds; i++)
        {
            childs[i].waitFor();
        }
    }

    public static void main (String[] args) throws IOException,InterruptedException
    {
        showInfo(args[0]);
        if (args[0].equals(processI))
        {
            createChilds(childrenOfI.length, childrenOfI);
            waitChilds(childrenOfI.length);
        }
    }
}

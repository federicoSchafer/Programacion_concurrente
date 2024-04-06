import java.io.IOException;

public class ChildProcess 

{
    /*Arrays de los hijos de cada proceso */
    static String[]  childrenOfB = {"E","F"};
    static String[]  childrenOfD = {"G"};
    /* Constantes*/
    static final String processB = "B";
    static final String processC = "C";
    static final String processD = "D";
    /*Ruta del siguiente nivel*/
    private static final String JAVA_FILE_LOCATION="C:\\Users\\npompeo\\Documents\\TrabajoPractico01\\src\\GrandChildProcess.java";
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

        switch (args[0]) 
        {
            case processB:
                createChilds(childrenOfB.length, childrenOfB);
                waitChilds(childrenOfB.length);
                break;
            case processD:
                createChilds(childrenOfD.length, childrenOfD);
                waitChilds(childrenOfD.length);
            default:
                break;
        }
        
    }
}
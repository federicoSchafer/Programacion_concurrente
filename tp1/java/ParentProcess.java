import java.io.IOException;

public class ParentProcess 
{
    /*Constantes*/
    private static final String JAVA_FILE_LOCATION="ChildProcess.java";
    private static final int numberOfChilds = 3;
    /*Recursos necesarios*/
    static Process childs[];
    static String[] letter = {"B","C","D"};

    
    public static void createChilds(int numberOfChilds) throws IOException
    {
        ProcessBuilder builder;
        childs = new Process[numberOfChilds];
        for(int i=0;i<numberOfChilds;i++)
        {
            builder = new ProcessBuilder("java",JAVA_FILE_LOCATION,letter[i]); //Acá ya le paso la letra del hijo
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

    public static void main(String[] args) throws IOException,InterruptedException
    {
        ProcessHandle yo = ProcessHandle.current();
        long pid = yo.pid();
        System.out.println("A PID: "+pid); //En este caso, siempre va a ser A, por ser el proceso Padre de todo
        createChilds(numberOfChilds);
        waitChilds(numberOfChilds);
    }
}
public class Concurrente implements Runnable
{
    /* Recursos comunes */
    private int[][] rc;
    private int escalar;

    /* Recursos necesarios para calcular numero de filas contiguas */
    private int hilos;
    private int filaActual = 0;
    private int cantidadFilasContiguas;
    /* Recursos para el caso de una matriz 10 x 10 con 4 hilos, cuya division no es entera */
    private boolean realizarEsfuerzoExtra;
    private int hilosRestantes;
    private final int cero = 0;
    private final int uno = 1;


    public Concurrente(int valorEscalar,int numeroHilos, int[][] matrizOriginal)
    {
        escalar=valorEscalar;
        hilos=numeroHilos;
        if((double) matrizOriginal.length / hilos - matrizOriginal.length % hilos > cero)
        {
            realizarEsfuerzoExtra = true;
            hilosRestantes = hilos;
        }
        cantidadFilasContiguas = matrizOriginal.length / hilos;
        rc = new int[matrizOriginal.length][matrizOriginal.length];
        /* Esto es para copiar la matriz en si, sin alterrar la matriz original */
        for(int i=0;i<rc.length;i++)
        {
            for(int j=0;j<rc.length;j++)
            {
                rc[i][j] = matrizOriginal[i][j];
            }
        }
    }

    @Override
    public void run()
    {
        calculoConcurrente();
        if(realizarEsfuerzoExtra)
        {
            hilosRestantes--;
        }
    }

    public synchronized void calculoConcurrente()
    {
        if(hilos == 1)
        {
            for(int i=0;i<rc.length;i++)
            {
                for(int j=0;j<rc.length;j++)
                {
                    rc[i][j] = rc[i][j]*escalar;
                }
            }
        }
        else
        {
            int cantidadFilasRestantes = cantidadFilasContiguas;
            while(filaActual < rc.length && cantidadFilasRestantes > 0)
            {
                for(int j=0;j<rc.length;j++)
                {
                    rc[filaActual][j] = rc[filaActual][j] * escalar;
                }
                filaActual++;
                cantidadFilasRestantes--;
            } 

            if(realizarEsfuerzoExtra && hilosRestantes == uno)
            {
                cantidadFilasRestantes = cantidadFilasContiguas;
                while(filaActual < rc.length)
                {
                    for(int j=0;j<rc.length;j++)
                    {
                        rc[filaActual][j] = rc[filaActual][j] * escalar;
                    }
                    filaActual++;
                } 
            }
        }
    }

    public int[][] recuperarMatriz()
    {
        return rc;
    }
}

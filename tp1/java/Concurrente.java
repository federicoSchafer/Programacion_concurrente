import java.time.Duration;
import java.time.Instant;

public class Concurrente 
{   
    private int numeroHilos;
    private int escalar;
    private int filasPorHilo;
    private int filasAdicionales;
    private int[][] rc;
    private long ultimaMedicion;

    public Concurrente(int numeroHilos, int escalar,int[][] rc)
    {
        this.numeroHilos=numeroHilos;
        this.escalar=escalar;
        this.rc=rc;

        filasPorHilo = this.rc.length / this.numeroHilos;
        filasAdicionales = this.rc.length % this.numeroHilos;
    }

    public void arranque()
    {
        int filaInicial = 0,filaFinal;
        HiloMultiplicadorDeMatriz[] threads = new HiloMultiplicadorDeMatriz[numeroHilos];
        Instant inicio = Instant.now();
        for (int i = 0; i < numeroHilos; i++) 
        {
            filaFinal = filaInicial + filasPorHilo + (i < filasAdicionales ? 1 : 0);
            threads[i] = new HiloMultiplicadorDeMatriz(filaInicial, filaFinal);
            threads[i].start();
            filaInicial = filaFinal;
        }

        for (int i = 0; i < numeroHilos; i++) 
        {
            try 
            {
                threads[i].join();
            } 
            catch (InterruptedException e) 
            {
                e.printStackTrace();
            }
        }
        Instant fin = Instant.now();
        long tiempoEmpleado = Duration.between(inicio, fin).toMillis();
        ultimaMedicion = tiempoEmpleado;

    }

    public double obtenerUltimaMedicion()
    {
        return (double) ultimaMedicion;
    }

    private class HiloMultiplicadorDeMatriz extends Thread
    {
        private final int startRow;
        private final int endRow;

        public HiloMultiplicadorDeMatriz(int startRow, int endRow)
        {
            this.startRow = startRow;
            this.endRow = endRow;
        }

        @Override
        public void run()
        {
            for (int i = startRow; i < endRow; i++)
            {
                for (int j = 0; j < rc.length; j++)
                {
                    rc[i][j] *= escalar;
                }
            }
        }
    }

}

import java.time.Duration;
import java.time.Instant;

public class Main 
{
    /* Cotas */
    private static final int cotaSuperior = 9;
    private static final int cotaInferior = 0;
    private static int tamanioMatriz = 80;
    /* Recursos comunes a todos */
    private static final int escalar = 2;
    /* Recursos comunes a Main */
    private static int[][] matrizOriginal = new int[tamanioMatriz][tamanioMatriz];
    /* Recursos para Hilos */
    private static final int numeroHilos=4; ////Puede ser 1, 2 o 4
    /* Recursos para comparar */
    private static int[][] rs;
    private static int[][] rc;
    /* Rondas de pruebas */
    private static int tamanioMuestraTesting = 30;
    private static double[] tiempoEmpleadoSecuencial = new double[tamanioMuestraTesting];
    private static double[] tiempoEmpleadoConcurrente = new double[tamanioMuestraTesting];


    public static void main(String[] args) throws Exception 
    {
        inicializarMatrizOriginal();
        inicializarSecuencial();
        inicializarCalculoConcurrente();
        verificarIgualdad();
        calcularPromedioDeEjecucion();
    }

    

    private static void inicializarMatrizOriginal()
    {
        for(int i=0;i<10;i++)
        {
            for(int j=0;j<10;j++)
            {
                matrizOriginal[i][j] = obtenerNumeroRandom();
            }
        }
    }

    private static int obtenerNumeroRandom()
    {
        int valorRandom = (int) (Math.floor(Math.random()*(cotaSuperior-cotaInferior)+cotaInferior));
        return valorRandom;
    }

    private static void inicializarCalculoConcurrente()
    {
        for(int pos=0;pos < tamanioMuestraTesting ; pos++)
        {
            Concurrente cConcurrente = new Concurrente(escalar, numeroHilos, matrizOriginal);
            Instant inicio = Instant.now();
            for(int i=0;i<numeroHilos;i++)
            {
                Thread myThread = new Thread(cConcurrente);
                myThread.start();
                try 
                {
                    myThread.join();
                } 
                catch (InterruptedException e) 
                {
                    e.printStackTrace();
                }
            }
            Instant fin = Instant.now();
            long tiempoEmpleado = Duration.between(inicio, fin).toMillis();
            tiempoEmpleadoConcurrente[pos] = (double) tiempoEmpleado;
            rc = cConcurrente.recuperarMatriz();
        }
    }

    private static void verificarIgualdad()
    {
        boolean sonIguales = true;
        for(int i=0;i<matrizOriginal.length;i++)
        {
            for(int j=0;j<matrizOriginal.length;j++)
            {
                if(rs[i][j] != rc[i][j])
                {
                    sonIguales = false;
                }
            }
        }
        
        if(sonIguales)
        {
            System.out.println("Las matrices son iguales");
        }
        else
        {
            System.out.println("Las matrices no son iguales");
        }
    }

    private static void inicializarSecuencial()
    {
        for(int pos = 0 ; pos < tamanioMuestraTesting ; pos++)
        {
            Secuencial cSecuencial = new Secuencial(escalar, matrizOriginal);
            rs=cSecuencial.calculoSecuencial();
            tiempoEmpleadoSecuencial[pos] = cSecuencial.obtenerUltimaMedicion();
        }
    }

    private static void calcularPromedioDeEjecucion() 
    {
        double sumaSecuencial=0,sumaConcurrente=0;
        double promedioSecuencial,promedioConcurrente;
        for(int i=0; i<tamanioMuestraTesting; i++)
        {
            sumaSecuencial += tiempoEmpleadoSecuencial[i];
            sumaConcurrente += tiempoEmpleadoConcurrente[i];
        } 
        promedioSecuencial = sumaSecuencial / tamanioMuestraTesting;
        promedioConcurrente = sumaConcurrente / tamanioMuestraTesting;
        System.out.println("El tiempo promedio de ejecucion para un calculo secuencial es de "+promedioSecuencial+" ms");
        System.out.println("El tiempo promedio de ejecucion para un calculo concurrente es de "+promedioConcurrente+" ms");
    }
    
}

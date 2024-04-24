public class Main 
{
    /* Cotas */
    private static final int cotaSuperior = 9;
    private static final int cotaInferior = 0;
    private static int tamanioMatriz = 80;
    /* Recursos comunes a todos */
    private static final int escalar = 1;
    /* Recursos para Hilos */
    private static final int numeroHilos=4; ////Puede ser 1, 2 o 4
    /* Recursos para comparar */
    private static int[][] rs = new int[tamanioMatriz][tamanioMatriz];
    private static int[][] rc = new int[tamanioMatriz][tamanioMatriz];
    /* Rondas de pruebas */
    private static int tamanioMuestraTesting = 30;
    private static double[] tiempoEmpleadoSecuencial = new double[tamanioMuestraTesting];
    private static double[] tiempoEmpleadoConcurrente = new double[tamanioMuestraTesting];


    public static void main(String[] args) throws Exception 
    {
        inicializarMatrices();
        inicializarSecuencial();
        inicializarCalculoConcurrente();
        verificarIgualdad();
        calcularPromedioDeEjecucion();
    }

    private static void inicializarMatrices()
    {
        for(int i=0;i<tamanioMatriz;i++)
        {
            for(int j=0;j<tamanioMatriz;j++)
            {
               int numero = obtenerNumeroRandom();
               rs[i][j] = numero;
               rc[i][j] = numero;
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
        for(int pos = 0 ; pos < tamanioMuestraTesting ; pos++)
        {
            Concurrente cConcurrente = new Concurrente(numeroHilos, escalar, rc);
            cConcurrente.arranque();
            tiempoEmpleadoConcurrente[pos] = cConcurrente.obtenerUltimaMedicion();
        }
    }

    private static void verificarIgualdad()
    {
        boolean sonIguales = true;
        for(int i=0;i<tamanioMatriz;i++)
        {
            for(int j=0;j<tamanioMatriz;j++)
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
            Secuencial cSecuencial = new Secuencial(escalar, rs);
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

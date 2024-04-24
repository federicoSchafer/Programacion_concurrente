import java.time.Duration;
import java.time.Instant;

public class Secuencial 
{
    private int[][] rs;
    private int escalar;
    private long ultimaMedicion;

    public Secuencial(int valorEscalar, int[][] matrizOriginal)
    {
        escalar=valorEscalar;
        rs = matrizOriginal;
    }

    public int[][] calculoSecuencial()
    {
        Instant inicio = Instant.now();
        for(int i=0;i<rs.length;i++)
        {
            for(int j=0;j<rs.length;j++)
            {
                rs[i][j] = rs[i][j]*escalar;
            }
        }
        Instant fin = Instant.now();
        long tiempoEmpleado = Duration.between(inicio, fin).toMillis();
        ultimaMedicion = tiempoEmpleado;
        return rs;
    }

    public double obtenerUltimaMedicion()
    {
        return (double) ultimaMedicion;
    }


}

import java.util.concurrent.Semaphore;
public class Rio 
{

    private Semaphore semaforo = new Semaphore(1);

    public void cruzar(Persona oAnimal) throws InterruptedException 
    {
        semaforo.acquire();
        oAnimal.cruzarRio();
        semaforo.release();
    }

    public void esperar() throws InterruptedException 
    {
        semaforo.acquire();
    }

    public void liberar() 
    {
        semaforo.release();
    }

}

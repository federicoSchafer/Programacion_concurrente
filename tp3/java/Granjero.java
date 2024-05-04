public class Granjero extends Persona
{

    public Granjero(Rio rio) 
    {
        super(rio);
    }

    @Override
    public void cruzarRio() 
    {
        try 
        {
            rio.esperar();
            System.out.println("El granjero cruza el río con el pollo.");
            Thread.sleep(1000);
            rio.liberar();

            rio.esperar();
            System.out.println("El granjero cruza el río solo.");
            Thread.sleep(1000);
            rio.liberar();

            rio.esperar();
            System.out.println("El granjero cruza el río con el maíz.");
            Thread.sleep(1000);
            rio.liberar();
        } 
        catch (InterruptedException e) 
        {
            System.out.println("Ocurrió una interrupción durante el cruce del granjero.");
        }
    }

}

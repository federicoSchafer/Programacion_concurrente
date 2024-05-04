public class Maiz extends Persona 
{
    public Maiz(Rio rio) 
    {
        super(rio);
    }

    @Override
    public void cruzarRio() 
    {
        try 
        {
            rio.esperar();
            System.out.println("El granjero cruza el río con el maíz.");
            Thread.sleep(1000);
            rio.liberar();
        } 
        catch (InterruptedException e) 
        {
            // No se hace nada si se interrumpe al maíz
        }
    }
}

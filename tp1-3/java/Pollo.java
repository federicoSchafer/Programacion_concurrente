public class Pollo extends Persona
{
    public Pollo(Rio rio) 
    {
        super(rio);
    }

    @Override
    public void cruzarRio() 
    {
        try 
        {
            rio.esperar();
            System.out.println("El pollo cruza el río con el granjero.");
            Thread.sleep(1000);
            rio.liberar();
        } 
        catch (InterruptedException e) 
        {
            System.out.println("El granjero regresó solo por el pollo.");
            try 
            {
                rio.esperar();
            } 
            catch (InterruptedException e1) 
            {
                e1.printStackTrace();
            }
            System.out.println("El pollo cruza el río solo.");
            try 
            {
                Thread.sleep(1000);
            } 
            catch (InterruptedException e1) 
            {
                e1.printStackTrace();
            }
            rio.liberar();
        }
    }
}

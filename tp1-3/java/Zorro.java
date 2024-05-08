public class Zorro extends Persona
{
    public Zorro(Rio rio) 
    {
        super(rio);
    }

    @Override
    public void cruzarRio() 
    {
        try 
        {
            rio.esperar();
            System.out.println("El zorro intenta cruzar el río, pero el granjero lo detiene.");
            rio.liberar();
        } 
        catch (InterruptedException e) 
        {
            // No se hace nada si se interrumpe al zorro
        }
    }
}

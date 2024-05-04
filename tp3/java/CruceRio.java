public class CruceRio 
{
    public static void main(String[] args) 
    {
        Rio rio = new Rio();
        Granjero granjero = new Granjero(rio);
        Zorro zorro = new Zorro(rio);
        Pollo pollo = new Pollo(rio);
        Maiz maiz = new Maiz(rio);

        Thread hiloGranjero = new Thread(granjero::cruzarRio);
        Thread hiloZorro = new Thread(zorro::cruzarRio);
        Thread hiloPollo = new Thread(pollo::cruzarRio);
        Thread hiloMaiz = new Thread(maiz::cruzarRio);

        hiloGranjero.start();
        hiloZorro.start();
        hiloPollo.start();
        hiloMaiz.start();
    }
}

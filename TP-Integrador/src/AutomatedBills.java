import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;

public class AutomatedBills {
	
	static AFIPPage afipPage;
	static ArrayList<String> list;
	static ArrayList<AFIPPage> threadList = new ArrayList<AFIPPage>();
	static long inicio;
	static long fin;
	static String excelPath = "C:\\Users\\npompeo\\Documents\\GitHub\\Programacion_concurrente\\TP-Integrador\\resources\\excel\\FacturasAT.xlsx";
	static String chromeDriverPath = "C:\\Users\\npompeo\\Documents\\GitHub\\Programacion_concurrente\\TP-Integrador\\resources\\chromeDriver\\chromedriver.exe";

	//Constantes para Selenium
	static String afipURL = "https://auth.afip.gob.ar/contribuyente_/login.xhtml";
	static String sheetname = "hoja1";
	
	//Constantes
	static int zero = 0;
	static int one = 1;
	static int thousand = 1000;

	static final String separador = "\n\n------------------------------------------------------------------------------";
	static final String tiempoTotal = "\nTiempo total para hacer ";
	static final String facturas = " facturas: ";
	static final String segundos = " segundos";

	public static void main(String[] args) throws IOException, InterruptedException 
	{
		

		
		Console console = System.console();
		if (console == null) 
		{
            System.out.println("No se pudo obtener la consola. Asegúrate de estar ejecutando el programa en una consola compatible.");
            System.exit(1);
        }
 
        String cuil = console.readLine("Ingrese su CUIL/CUIT: ");
        char[] passwordArray = console.readPassword("Ingrese su clave fiscal: ");
        String password = new String(passwordArray);
        
        User user = new User(cuil, password);
        
        String hilos = console.readLine("Ingrese la cantidad de hilos a ejecutar: ");
        int cantHilos = Integer.parseInt(hilos);

        XlsxReader reader = new XlsxReader();
        reader.open(excelPath, sheetname);
        
		ArrayList<Order> regisList = new ArrayList<Order>();
		regisList=reader.read();

		int cantRegis = regisList.size() / cantHilos;
		int resto = regisList.size() % cantHilos;
		
		inicio = System.currentTimeMillis();
		
		if (cantRegis >= one)
		{
			//creo n-1 hilos
			int i;
			for(i=zero; i < cantHilos - one; i++)
			{
				threadList.add(new AFIPPage(chromeDriverPath, afipURL, user, reader, regisList, cantRegis*i, cantRegis));
				threadList.get(i).start();
			}
			//creo el hilo faltante sumandole la cantidad de registros sobrantes 
			//en caso de que regisList.size()/CANT_HILOS no de un numero entero
			threadList.add(new AFIPPage(chromeDriverPath, afipURL, user, reader, regisList, cantRegis*i, cantRegis + resto));
			threadList.get(i).start();
		}
		else //si hay mas registros que hilos, creo un hilo x registro
		{
			int i;
			for (i = zero; i < resto; i++) 
			{
				threadList.add(new AFIPPage(chromeDriverPath, afipURL, user, reader, regisList, i, one));
				threadList.get(i).start();
			}
			cantHilos = i;
		}
		
		for(int i=0; i< cantHilos; i++)
		{
			threadList.get(i).join();
		}
		
		reader.close();
		
		
		fin = System.currentTimeMillis();
		float segundos = (fin - inicio) / thousand;
		
		System.out.println(separador);
		
		System.out.println(tiempoTotal + regisList.size() + facturas + segundos);
	}
}
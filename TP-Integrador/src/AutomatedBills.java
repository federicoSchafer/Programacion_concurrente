import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class AutomatedBills {
	
	static AFIPPage afipPage;
	static RegistroExcel regis;
	static ArrayList<String> list;
	static ArrayList<AFIPPage> threadList = new ArrayList<AFIPPage>();
	static long inicio;
	static long fin;
	static String excelPath = "C:\\Users\\tomas\\eclipse-workspace-2024\\PruebaAutomatizacion\\resources\\excel\\FacturasAT.xlsx";
	static String chromeDriverPath = "C:\\Users\\tomas\\eclipse-workspace-2024\\PruebaAutomatizacion\\resources\\chromeDriver\\chromedriver.exe";
	static int CANT_HILOS = 3;

	//Constantes para Selenium
	static String afipURL = "https://auth.afip.gob.ar/contribuyente_/login.xhtml";
	static String strHoja1 = "hoja1";
	
	//Constantes
	static int one = 1;
	static int thousand = 1000;

	static final String separador = "\n\n------------------------------------------------------------------------------";
	static final String tiempoTotal = "\nTiempo total para hacer ";
	static final String facturas = " facturas: ";
	static final String segundos = " segundos";

	public static void main(String[] args) throws IOException, InterruptedException 
	{
		//inicializo
		regis = new RegistroExcel();		
		ArrayList<Order> regisList = new ArrayList<Order>();
		
		regisList=XlsxReader.read(excelPath, strHoja1);
		
		int cantRegis = regisList.size()/CANT_HILOS;
		int resto = regisList.size()%CANT_HILOS;
	
		inicio = System.currentTimeMillis();
		//creo n-1 hilos
		int i;
		for(i=0; i < CANT_HILOS - one; i++)
		{
			threadList.add(new AFIPPage(chromeDriverPath, afipURL, regisList, cantRegis*i, cantRegis));
			threadList.get(i).start();
		}
		//creo el hilo faltante sumandole la cantidad de registros sobrantes 
		//en caso de que regisList.size()/CANT_HILOS no de un numero entero
		threadList.add(new AFIPPage(chromeDriverPath, afipURL, regisList, cantRegis*i, cantRegis + resto));
		threadList.get(i).start();
		
		for(i=0; i< CANT_HILOS; i++)
		{
			threadList.get(i).join();
		}

		XlsxReader.write(excelPath, strHoja1, regisList);
		fin = System.currentTimeMillis();
		float segundos = (fin - inicio) / thousand;
		
		System.out.println(separador);
		
		System.out.println(tiempoTotal+ regisList.size() +facturas + segundos + segundos);
	}
}

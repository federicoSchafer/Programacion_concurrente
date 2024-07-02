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
	static String afipURL = "https://auth.afip.gob.ar/contribuyente_/login.xhtml";

	public static void main(String[] args) throws IOException, InterruptedException 
	{
		//inicializo
		regis = new RegistroExcel();		
		ArrayList<Order> regisList = new ArrayList<Order>();
		
		regisList=XlsxReader.read(excelPath, "hoja1");
		
		int cantRegis = regisList.size()/CANT_HILOS;
		int resto = regisList.size()%CANT_HILOS;
	
		inicio = System.currentTimeMillis();
		//creo n-1 hilos
		int i;
		for(i=0; i < CANT_HILOS - 1; i++)
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

		XlsxReader.write(excelPath, "hoja1", regisList);
		fin = System.currentTimeMillis();
		float segundos = (fin - inicio) / 1000;
		
		System.out.println("\n\n------------------------------------------------------------------------------");
		
		System.out.println("\nTiempo total para hacer "+ regisList.size() +" facturas: " + segundos + " segundos");
	}
}

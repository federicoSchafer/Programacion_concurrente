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
		//regisList = regis.readExcelAndSaveInList(excelPath, "Hoja1");
		
		
		
		
		
//		afipPage = new AFIPPage(chromeDriverPath, afipURL, regisList, regisList.subList(0,10));
//		threadList.add(afipPage);
//		threadList.get(0).start();
//		afipPage.join();
		
		
		
		ArrayList<Order> regisList = new ArrayList<Order>();
		
		regisList=XlsxReader.read(excelPath, "hoja1");
		
		
//		for (int i = 0; i < regisList.size(); i++) {
//			
//			regisList.get(i).print();
//			regisList.get(i).status = true;
//		}
		
		
		
		
		int cantRegis = regisList.size()/CANT_HILOS;
	
		inicio = System.currentTimeMillis();
		
		for(int i=0; i< CANT_HILOS; i++)
		{
			threadList.add(new AFIPPage(chromeDriverPath, afipURL, regisList, cantRegis*i, cantRegis));
			threadList.get(i).start();
		}
		
		for(int i=0; i< CANT_HILOS; i++)
		{
			threadList.get(i).join();
		}
		
		
		XlsxReader.write(excelPath, "hoja1", regisList);
		
		
		
		
		//afipPage = new AFIPPage(chromeDriverPath);
		
		//afipPage.visit("https://auth.afip.gob.ar/contribuyente_/login.xhtml");
		
		
		//INGRESAR CUIL Y CONTRASEÑA
//		User user = new User();
//		afipPage.singIn(user.cuil, user.password);
//		afipPage.clickOnComprobantes();
		
		//run
//		list = new ArrayList<String>();
		
//		
//		for (int i = 0; i < regisList.size(); i++) 
//		{
//			
//			if(threadList.size()==CANT_HILOS)
//			{
//				mtx.acquire();
//			}
//
//			if(afipPage.hacerFactura(i, regisList.get(i))>0)
//			{
//				list.add( "FACTURADO");
//			}
//			else
//			{
//				list.add("NO FACTURADO");
//			}
//			
//			afipPage.visit("https://fe.afip.gob.ar/rcel/jsp/menu_ppal.jsp");
//			
//		}
		fin = System.currentTimeMillis();
		
		//end
		//afipPage.quit();
//		regis = new RegistroExcel();
//		regis.writeInformsInExcel("C:\\Users\\tomas\\eclipse-workspace-2024\\PruebaAutomatizacion\\resources\\excel\\FacturasAT.xlsx", "Hoja1", list);
//		
		
		
		float segundos = (fin - inicio) / 1000;
		
		System.out.println("\n\n------------------------------------------------------------------------------");
		
		System.out.println("\nTiempo total para hacer "+ regisList.size() +" facturas: " + segundos + " segundos");
		//System.out.println("Cantidad promedio de facturas hechas por minuto: " + (float)regisList.size() / (segundos/60));

		
	}

}

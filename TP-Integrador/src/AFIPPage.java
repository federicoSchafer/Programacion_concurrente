
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


public class AFIPPage extends Thread 
{

	static final String DESCRIPTION = "Varios";
	
	private WebDriver driver;
	private String url;
	private User user;
	ArrayList<Order> regisList;
	int orderIni;
	int numbOfOrders;
	XlsxReader reader;
	

	//Constantes para Selenium
	final static String urlMenuPrincipal = "https://fe.afip.gob.ar/rcel/jsp/menu_ppal.jsp";
	final static String webDriverChrome = "webdriver.chrome.driver";

	final static String strcuilLocator = "F1:username";
	final static String strsiguienteBtnLocator = "F1:btnSiguiente";
	final static String strcontrasenaLocator = "F1:password";
	final static String stringresarBtnLocator = "F1:btnIngresar";
	final static String strpageLocator1 = "//img[@src = 'frameworkAFIP/v1/img/logo_afip.png']";
	final static String strmisServiciosLocator = "//*[@id=\"root\"]/div/main/section[1]/div/ul/li[3]";
	final static String strcomprobantesEnLineaLocator = "//*[@id=\"serviciosMasUtilizados\"]/div/div/div/div[1]/a/div/h3";

	final static String strguPaKaBtnLocator = "//*[@id=\"contenido\"]/form/table/tbody/tr[4]/td/input[2]";
	final static String strgenerComprBtnLocator = "btn_gen_cmp";
	final static String strcerrarBtnLocator = "novolveramostrar";
	final static String strpuntVentTextLocator = "//*[@id=\"contenido\"]/form/div/div/table/tbody/tr[1]/th";
	final static String strddlPtoVentLocator = "puntodeventa";
	final static String strddlTipoComprLocator = "universocomprobante";
	final static String strcontinuarBtnLocator = "//*[@id=\"contenido\"]/form/input[2]";

	final static String strfechaLocator = "fc";
	final static String strconceptoLocator = "idconcepto";

	final static String strcondFrentIVALocator = "idivareceptor";
	final static String strtipoDocLocator = "idtipodocreceptor";
	final static String strdniLocator = "nrodocreceptor";
	final static String strnombreLocator = "razonsocialreceptor";
	final static String strcontadoCheckLocator = "formadepago1";
	final static String strcontinuarBtn2Locator = "//*[@id=\"formulario\"]/input[2]";

	final static String strdescripcionLocator = "detalle_descripcion1";
	final static String strprecioLocator = "detalle_precio1";
	final static String stralicIVALocator = "detalle_tipo_iva1";
	final static String strcontinuarBtn3Locator = "//*[@id=\"contenido\"]/form/input[8]";

	final static String strconfirmarBtnLocator = "btngenerar";
	final static String strcomprGeneradTextLocator = "//*[@id=\"botones_comprobante\"]/b";
	final static String strmenuBtnLocator = "//*[@id=\"contenido\"]/table/tbody/tr[2]/td/input";
	

	//Constantes
	final static String strError0="Error ";
	final static String strError1="Error 1 ";
	final static String strError2="Error 2 ";
	final static String strError3="Error 3 ";
	final static String strError4="Error 4 ";
	final static String strProductos = " Productos";
	final static String strPuntoDeVenta = "Punto de Ventas a utilizar";

	final static int zero = 0;
	final static int one = 1;
	final static int oneHundred = 100;
	final static int oneThousand = 1000;
	final static int fiveHundred = 500;
	final static int fiveThounsand = 5000;

	final static boolean TRUE=true;
	final static boolean FALSE=false;
	
	//Selenium
	By cuilLocator = By.id(strcuilLocator);
	By siguienteBtnLocator = By.id(strsiguienteBtnLocator);
	By contrasenaLocator = By.id(strcontrasenaLocator);
	By ingresarBtnLocator = By.id(stringresarBtnLocator);
	
	By pageLocator1 = By.xpath(strpageLocator1);
	By misServiciosLocator = By.xpath(strmisServiciosLocator);
	By comprobantesEnLineaLocator = By.xpath(strcomprobantesEnLineaLocator);
	
	By guPaKaBtnLocator = By.xpath(strguPaKaBtnLocator);
	By generComprBtnLocator = By.id(strgenerComprBtnLocator);
	By cerrarBtnLocator = By.id(strcerrarBtnLocator);
	By puntVentTextLocator = By.xpath(strpuntVentTextLocator);
	By ddlPtoVentLocator = By.id(strddlPtoVentLocator);
	By ddlTipoComprLocator = By.id(strddlTipoComprLocator);
	By continuarBtnLocator = By.xpath(strcontinuarBtnLocator);
	
	By fechaLocator = By.id(strfechaLocator);
	By conceptoLocator = By.id(strconceptoLocator);
	
	By condFrentIVALocator = By.id(strcondFrentIVALocator);
	By tipoDocLocator = By.id(strtipoDocLocator);
	By dniLocator = By.id(strdniLocator);
	By nombreLocator = By.id(strnombreLocator);
	By contadoCheckLocator = By.id(strcontadoCheckLocator);
	By continuarBtn2Locator = By.xpath(strcontinuarBtn2Locator);
	
	By descripcionLocator = By.id(strdescripcionLocator);
	By precioLocator = By.id(strprecioLocator);
	By alicIVALocator = By.id(stralicIVALocator);
	By continuarBtn3Locator = By.xpath(strcontinuarBtn3Locator);
	
	By confirmarBtnLocator = By.id(strconfirmarBtnLocator);
	By comprGeneradTextLocator = By.xpath(strcomprGeneradTextLocator);
	By menuBtnLocator = By.xpath(strmenuBtnLocator);
	
	public AFIPPage(String location, String url, User user, XlsxReader reader, ArrayList<Order> regisList, int ini, int numb) 
	{
		this.url = url;
		this.regisList = regisList;
		this.orderIni = ini;
		this.numbOfOrders = numb;
		this.user = user;
		this.reader = reader;
		System.setProperty(webDriverChrome, location);
		ChromeOptions options=new ChromeOptions();
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
	}
		
	@Override
	public void run() 
	{
		visit(this.url);
		singIn(user.cuil, user.password);
		try 
		{
			clickOnComprobantes();
		} 
		catch (InterruptedException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		for(int i=zero ; i<numbOfOrders; i++)
		{
			Order r = regisList.get(orderIni + i);
			regisList.get(orderIni + i).status = hacerFactura(i, r);
			synchronized (reader) 
			{
				r.print();
				try 
				{
					reader.writeOrder(r);
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
			}
			visit(urlMenuPrincipal);
		}
		quit();
	}
	
	
	
	public void singIn(String cuil, String contra)
	{
		WebDriverWait ewait= newWait();
		try 
		{
			ewait.until(ExpectedConditions.elementToBeClickable(cuilLocator));	
			type(cuil, cuilLocator);
			click(siguienteBtnLocator);
			ewait.until(ExpectedConditions.elementToBeClickable(contrasenaLocator));
			type(contra, contrasenaLocator);
			click(ingresarBtnLocator);
		}
		catch (org.openqa.selenium.TimeoutException e) 
		{
			System.out.print(strError0+ e.getMessage());
			quit();
			System.exit(1);
		}	
	}

	public void clickOnComprobantes() throws InterruptedException 
	{	
		WebDriverWait ewait= newWait();
		try 
		{
			ewait.until(ExpectedConditions.presenceOfElementLocated(comprobantesEnLineaLocator));
			click(comprobantesEnLineaLocator);
			Thread.sleep(fiveThounsand);
			ArrayList<String> tabs = getWindowHandles();
			switchToWindow(tabs, one);
			click(guPaKaBtnLocator);
		}
		catch (org.openqa.selenium.TimeoutException e) 
		{
			System.out.println(strError0+ e.getMessage());
			quit();
			System.exit(1);
		}
	}

	public boolean hacerFactura(int i, Order regis)
	{
		WebDriverWait ewait= newWait();
		try 
		{
			ewait.until(ExpectedConditions.elementToBeClickable(generComprBtnLocator));
			click(generComprBtnLocator);
			ewait.until(ExpectedConditions.textToBe(puntVentTextLocator, strPuntoDeVenta));
			dropDownList(ddlPtoVentLocator, regis.pointOfSale);
			Thread.sleep(fiveHundred);
			dropDownList(ddlTipoComprLocator, regis.type);
			
			if(i == zero) 
			{
				ewait.until(ExpectedConditions.elementToBeClickable(cerrarBtnLocator));
				click(cerrarBtnLocator);
			}
			click(continuarBtnLocator);
			ewait.until(ExpectedConditions.elementToBeClickable(fechaLocator));
			String s=regis.date;
			type(s,fechaLocator);
			dropDownList(conceptoLocator,strProductos);
			click(continuarBtnLocator);
			ewait.until(ExpectedConditions.elementToBeClickable(condFrentIVALocator));
			dropDownList(condFrentIVALocator, regis.ivaCondition);
			
			dropDownList(tipoDocLocator, regis.typeDoc);
			type(regis.numDoc, dniLocator);
			
			click(contadoCheckLocator);
			Thread.sleep(oneHundred);
			click(continuarBtn2Locator);
			ewait.until(ExpectedConditions.elementToBeClickable(descripcionLocator));
			type(DESCRIPTION, descripcionLocator);
			type(regis.price, precioLocator);
			dropDownList(alicIVALocator, regis.iva);
			click(continuarBtn3Locator);
			//ewait.until(ExpectedConditions.elementToBeClickable(confirmarBtnLocator));
			Thread.sleep(oneThousand);
            //click(confirmarBtnLocator);
            //AcceptAlert();
            //ewait.until(ExpectedConditions.textToBe(comprGeneradTextLocator, "Comprobante Generado"));
		} 
		catch (org.openqa.selenium.TimeoutException e) 
		{
			System.out.println(strError1+ e.getMessage());
			return FALSE;
		}
		catch (InterruptedException e) 
		{
			System.out.println(strError2+ e.getMessage());
			return FALSE;
		}
		catch (org.openqa.selenium.NoSuchElementException e) 
		{
			System.out.println(strError3+ e.getMessage());
			regis.print();
			return FALSE;
		}
		catch (org.openqa.selenium.ElementClickInterceptedException e) 
		{
			System.out.println(strError4+ e.getMessage());
			regis.print();
			return FALSE;
		}
		catch (org.openqa.selenium.UnhandledAlertException e) 
		{
			System.out.println(strError4+ e.getMessage());
			regis.print();
			return FALSE;
		}
		return TRUE;
	}
		
	public void quit()
	{
		driver.quit();
	}
	
	
	
	//Funciones Driver
	public WebElement findElement(By locator) 
	{
		return driver.findElement(locator);
	}
	
	public ArrayList<String> getWindowHandles()
	{
		
		ArrayList<String> a = new ArrayList<String> (driver.getWindowHandles());	
		return a;
	}
	
	public void switchToWindow(ArrayList<String> a, int i) 
	{	
		driver.switchTo().window(a.get(i));
	}
	
	public void type(String inputText, By locator) 
	{	
		driver.findElement(locator).clear();
		driver.findElement(locator).sendKeys(inputText);
	}
	
	
	public void click(By locator) 
	{	
		driver.findElement(locator).click();
	}
	
	public void click(WebElement e) 
	{	
		e.click();
	}

	
	public boolean isDisplayed(By locator) 
	{	
		try 
		{
			return driver.findElement(locator).isDisplayed();
		} 
		catch (org.openqa.selenium.NoSuchElementException e) 
		{
			return false;
		}
	}
	
	
	public void visit(String url) 
	{
		driver.get(url);
	}
	
	public String getText(WebElement element) 
	{
		return element.getText();
	}
	
	public void AcceptAlert() 
	{	
		Alert alert = driver.switchTo().alert();
		alert.accept();
	}
	
	public String getText(By locator) 
	{
		return driver.findElement(locator).getText();
	}

	public void submit(By locator) 
	{
		driver.findElement(locator).submit();
	}
	
	public WebDriverWait newWait() 
	{	
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15)); 
		return wait;
	}
	
	public String dropDownList(By locator, String s) 
	{	
		Select selectList = new Select (findElement(locator));
		selectList.selectByVisibleText(s);
		return getText(selectList.getFirstSelectedOption());
	}
}


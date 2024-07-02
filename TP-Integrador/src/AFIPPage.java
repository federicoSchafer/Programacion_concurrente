import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

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
	ArrayList<Order> regisList;
	int orderIni;
	int numbOfOrders;
	
	public AFIPPage(String location, String url, ArrayList<Order> regisList, int ini, int numb) 
	{
		this.url = url;
		this.regisList = regisList;
		this.orderIni = ini;
		this.numbOfOrders = numb;
		System.setProperty("webdriver.chrome.driver", location);
		ChromeOptions options=new ChromeOptions();
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
	}
		
	@Override
	public void run() 
	{
		visit(this.url);
		User user = new User();
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
		
		
		
		for(int i=0 ; i<numbOfOrders; i++)
		{
			Order r;
			synchronized (this)
			{
				r = regisList.get(orderIni + i);
			}
			boolean res = hacerFactura(i, r);
			synchronized (this) 
			{
				regisList.get(orderIni + i).status = res;
				regisList.get(orderIni + i).print();
			}
			visit("https://fe.afip.gob.ar/rcel/jsp/menu_ppal.jsp");
		}
		
		quit();
	}
	
	
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
	
	
	By cuilLocator = By.id("F1:username");
	By siguienteBtnLocator = By.id("F1:btnSiguiente");
	By contrasenaLocator = By.id("F1:password");
	By ingresarBtnLocator = By.id("F1:btnIngresar");
	
	By pageLocator1 = By.xpath("//img[@src = 'frameworkAFIP/v1/img/logo_afip.png']");
	By misServiciosLocator = By.xpath("//*[@id=\"root\"]/div/main/section[1]/div/ul/li[3]");
	By comprobantesEnLineaLocator = By.xpath("//*[@id=\"serviciosMasUtilizados\"]/div/div/div/div[1]/a/div/h3");
	
	
	//novolveramostrar
	
	By guPaKaBtnLocator = By.xpath("//*[@id=\"contenido\"]/form/table/tbody/tr[4]/td/input[2]");
	By generComprBtnLocator = By.id("btn_gen_cmp");
	By cerrarBtnLocator = By.id("novolveramostrar");
	By puntVentTextLocator = By.xpath("//*[@id=\"contenido\"]/form/div/div/table/tbody/tr[1]/th");
	By ddlPtoVentLocator = By.id("puntodeventa");
	By ddlTipoComprLocator = By.id("universocomprobante");
	By continuarBtnLocator = By.xpath("//*[@id=\"contenido\"]/form/input[2]");
	
	By fechaLocator = By.id("fc");
	By conceptoLocator = By.id("idconcepto");
	
	
	By condFrentIVALocator = By.id("idivareceptor");
	By tipoDocLocator = By.id("idtipodocreceptor");
	By dniLocator = By.id("nrodocreceptor");
	By nombreLocator = By.id("razonsocialreceptor");
	By contadoCheckLocator = By.id("formadepago1");
	By continuarBtn2Locator = By.xpath("//*[@id=\"formulario\"]/input[2]");
	
	
	
	By descripcionLocator = By.id("detalle_descripcion1");
	By precioLocator = By.id("detalle_precio1");
	By alicIVALocator = By.id("detalle_tipo_iva1");
	By continuarBtn3Locator = By.xpath("//*[@id=\"contenido\"]/form/input[8]");
	
	By confirmarBtnLocator = By.id("btngenerar");
	By comprGeneradTextLocator = By.xpath("//*[@id=\"botones_comprobante\"]/b");
	By menuBtnLocator = By.xpath("//*[@id=\"contenido\"]/table/tbody/tr[2]/td/input");
	

	

	
	
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
			System.out.print("Error"+ e.getMessage());
		}	
	}
		
			
	
	
	
	public void clickOnComprobantes() throws InterruptedException 
	{	
		WebDriverWait ewait= newWait();
		try 
		{
			ewait.until(ExpectedConditions.presenceOfElementLocated(comprobantesEnLineaLocator));
			click(comprobantesEnLineaLocator);
			Thread.sleep(5000);
			ArrayList<String> tabs = getWindowHandles();
			switchToWindow(tabs, 1);
			click(guPaKaBtnLocator);
		}
		catch (org.openqa.selenium.TimeoutException e) 
		{
			System.out.println("Error"+ e.getMessage());	
		}
	}
	
	
	

	public boolean hacerFactura(int i, Order regis)
	{
		WebDriverWait ewait= newWait();
		try 
		{
			ewait.until(ExpectedConditions.elementToBeClickable(generComprBtnLocator));
			click(generComprBtnLocator);
			ewait.until(ExpectedConditions.textToBe(puntVentTextLocator, "Punto de Ventas a utilizar"));
			dropDownList(ddlPtoVentLocator, regis.pointOfSale);
			Thread.sleep(500);
			dropDownList(ddlTipoComprLocator, regis.type);
			
			if(i == 0) 
			{
				ewait.until(ExpectedConditions.elementToBeClickable(cerrarBtnLocator));
				click(cerrarBtnLocator);
			}
			click(continuarBtnLocator);
			ewait.until(ExpectedConditions.elementToBeClickable(fechaLocator));
			String s=regis.date;
			type(s,fechaLocator);
			dropDownList(conceptoLocator," Productos");
			click(continuarBtnLocator);
			ewait.until(ExpectedConditions.elementToBeClickable(condFrentIVALocator));
			dropDownList(condFrentIVALocator, regis.ivaCondition);
			
			dropDownList(tipoDocLocator, regis.typeDoc);
			type(regis.numDoc, dniLocator);
			
			click(contadoCheckLocator);
			Thread.sleep(100);
			click(continuarBtn2Locator);
			ewait.until(ExpectedConditions.elementToBeClickable(descripcionLocator));
			type(DESCRIPTION, descripcionLocator);
			type(regis.price, precioLocator);

			dropDownList(alicIVALocator, regis.iva);

			click(continuarBtn3Locator);

			Thread.sleep(1000);
		} 
		catch (org.openqa.selenium.TimeoutException e) 
		{
			System.out.println("Error"+ e.getMessage());
			return false;
		}
		catch (InterruptedException e) 
		{
			System.out.println("Error2: "+ e.getMessage());
			return false;
		}
		catch (org.openqa.selenium.NoSuchElementException e) 
		{
			System.out.println("Error3: "+ e.getMessage());
			regis.print();
			return false;
		}
		catch (org.openqa.selenium.ElementClickInterceptedException e) 
		{
			System.out.println("Error4: "+ e.getMessage());
			regis.print();
			return false;
		}
		catch (org.openqa.selenium.UnhandledAlertException e) 
		{
			System.out.println("Error4: "+ e.getMessage());
			regis.print();
			return false;
		}
		return true;
	}
	
	
	public void quit()
	{
		driver.quit();
	}
}


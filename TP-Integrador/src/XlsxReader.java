import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XlsxReader {
	
	
	private static  File file;
	private static  FileInputStream inputStream;
	private static  XSSFWorkbook newWorkBook;
	private static  XSSFSheet newSheet;
	
	
	public XlsxReader() {}
	
	final static int zero=0;
	final static int one=1;
	final static int two=2;
	final static int three=3;
	final static int four=4;
	final static int five=5;
	final static int six=6;
	final static int seven=7;
	final static int eight=8;
	final static int nine=9;
	final static int ten=10;

	final static String msgProcesado="PROCESADO";
	final static String msgError="ERROR";
	final static String strEspacio=" ";
	final static String comma=",";
	final static String dot=".";
	
	final static boolean FALSE=false;
	final static boolean TRUE = true;
	
	public ArrayList<Order> read() throws IOException
	{	
		int rowCount = newSheet.getLastRowNum() - newSheet.getFirstRowNum();
		ArrayList<Order> list = new ArrayList<Order>();

		
		for (int i = 1; i <= rowCount; i++) 
		{
			XSSFRow row = newSheet.getRow(i);
			Order regis = new Order();
			regis.id = i;

			int columnCount = ten;
			for (int j = 0; j < columnCount; j++) 
			{
				DataFormatter formatter = new DataFormatter();
				String data = formatter.formatCellValue(row.getCell(j));

				switch(j) 
				{
					case zero: 
						regis.date = data;
						break;
					case one: 
						regis.pointOfSale = strEspacio + data;
						break;
					case two: 
						regis.type = data;
						break;
					case three: 
						regis.ivaCondition = strEspacio + data;
						break;
					case four: 
						regis.typeDoc = data;
						break;
					case five: 
						regis.numDoc = data;
						break;
					case six: 
						regis.price = data;
						break;
					case seven: 
						regis.bonus = data;
						break;
					case eight: 
						regis.iva = strEspacio + data;
						break;
					case nine: 
						regis.status = FALSE;
						break;
					default: 	
						break;	
				}
			}
			regis.price=regis.price.replace(comma, dot);
			list.add(regis);
		}
		return list;
	}
	
	public void open(String filepath, String sheetname) throws IOException 
	{
		file = new File (filepath);
		inputStream = new FileInputStream(file);
		newWorkBook = new XSSFWorkbook(inputStream);
		newSheet = newWorkBook.getSheet(sheetname);
	}
	
	public void close() throws IOException
	{
		inputStream.close();
		newWorkBook.close();
	}
	
	public void writeOrder(Order order) throws IOException 
	{
		XSSFCell cell= newSheet.getRow(order.id).createCell(9);
		if(order.status == TRUE) 
		{
			cell.setCellValue(msgProcesado);
		} 
		else 
		{
			cell.setCellValue(msgError);
		}
		FileOutputStream outpustream = new FileOutputStream(file);
		newWorkBook.write(outpustream);
		outpustream.close();
	}
	
	
	
	
}
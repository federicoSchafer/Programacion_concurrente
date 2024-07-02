

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
	public XlsxReader() {}
	
	public static ArrayList<Order> read(String filepath, String sheetname) throws IOException{
		File file = new File (filepath);
		FileInputStream inputStream = new FileInputStream(file);
		XSSFWorkbook newWorkBook = new XSSFWorkbook(inputStream);
		XSSFSheet newSheet = newWorkBook.getSheet(sheetname);
		int rowCount = newSheet.getLastRowNum() - newSheet.getFirstRowNum();
		ArrayList<Order> list = new ArrayList<Order>();
		
		for (int i = 1; i <= rowCount; i++) 
		{
			XSSFRow row = newSheet.getRow(i);
			Order regis = new Order();
			regis.id = i;

			int columnCount = 10;
			for (int j = 0; j < columnCount; j++) 
			{
				DataFormatter formatter = new DataFormatter();
				String data = formatter.formatCellValue(row.getCell(j));

				switch(j) 
				{
					case 0: 
						regis.date = data;
						break;
					case 1: 
						regis.pointOfSale = " " + data;
						break;
					case 2: 
						regis.type = data;
						break;
					case 3: 
						regis.ivaCondition = " " + data;
						break;
					case 4: 
						regis.typeDoc = data;
						break;
					case 5: 
						regis.numDoc = data;
						break;
					case 6: 
						regis.price = data;
						break;
					case 7: 
						regis.bonus = data;
						break;
					case 8: 
						regis.iva = " " + data;
						break;
					case 9: 
						regis.status = false;
						break;
					default: 	
						break;	
				}
			}
			regis.price=regis.price.replace(',', '.');
			list.add(regis);
		}
		newWorkBook.close();
		return list;
	}
	
	public static void write(String filepath, String sheetname, ArrayList<Order> list ) throws IOException 
	{
		File file = new File (filepath);
		FileInputStream inputStream = new FileInputStream(file);
		XSSFWorkbook newWorkBook = new XSSFWorkbook(inputStream);
		XSSFSheet newSheet = newWorkBook.getSheet(sheetname);

		
		for (int i = 0; i < list.size(); i++) 
		{
			Order order = list.get(i);
			XSSFCell cell= newSheet.getRow(order.id).createCell(9);
			if(order.status == true) 
			{
				cell.setCellValue("PROCESADO");
			} 
			else 
			{
				cell.setCellValue("ERROR");
			}
		}
		inputStream.close();
		FileOutputStream outpustream = new FileOutputStream(file);
		newWorkBook.write(outpustream);
		outpustream.close();
		newWorkBook.close();
	}
}
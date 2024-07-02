
public class Order {
	
	public static final float MAX_PRICE = 80000;
	
	public int id;
	public String date;
	public String pointOfSale;
	public String type;
	public String ivaCondition;
	public String typeDoc;
	public String numDoc;
	public String price;
	public String bonus;
	public String iva;
	public Boolean status = false;
	private String strSeperador = " || ";

	public Order() {}

	public void print() {
		System.out.println(
			date + strSeperador +
			pointOfSale + strSeperador +
			type + strSeperador +
			ivaCondition + strSeperador +
			typeDoc + strSeperador +
			numDoc + strSeperador +
			price + strSeperador +
			bonus + strSeperador +
			iva + strSeperador +
			status
		);
	}
}
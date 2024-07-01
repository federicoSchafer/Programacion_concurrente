
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

	public Order() {}

	public void print() {
		System.out.println(
			date + " || " +
			pointOfSale + " || " +
			type + " || " +
			ivaCondition + " || " +
			typeDoc + " || " +
			numDoc + " || " +
			price + " || " +
			bonus + " || " +
			iva + " || " +
			status
		);
	}
	
//	public boolean priceMoreThanMaxWithoutDni()
//	{
//		if(Float.valueOf(price)>=MAX_PRICE)
//			return true;
//		else
//			return false;
//	}
}
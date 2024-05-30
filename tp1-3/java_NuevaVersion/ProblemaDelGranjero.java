import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class ProblemaDelGranjero 
{
	static final char LADO_A = 'A';
	static final char LADO_B = 'B';
	static final String GRANJERO = "GRANJERO";
	static final String ZORRO = "ZORRO";
	static final String POLLO = "POLLO";
	static final String MAIZ = "MAIZ";
	static final String SOLO = "SOLO";
	
	
	static Map<String, Character> personajes = new HashMap<>();
	static int transporte = 0;
	static Object lock = new Object();
	static String personajeATransportar;
	static boolean success = true;
	static boolean end = false;
	static Semaphore check = new Semaphore(0);
	
	public static class LadoHilo extends Thread
	{
		private char lado;
		
		public LadoHilo (char lado)
		{
			this.lado = lado;
		}
		
		public void run()
		{
			while(true)
			{
				String mensaje = "";
				synchronized (lock)
				{
					try
					{
						lock.wait();
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					if(!success)
					{
						check.release();
						return;
					}
				} 
				if(personajes.get(ZORRO) == personajes.get(POLLO) && personajes.get(GRANJERO) != personajes.get(ZORRO)
						&& personajes.get(ZORRO) == this.lado)
				{
					success = false;
					mensaje = "-El Zorro se come al pollo en el lado " + this.lado;
				}
				else if(personajes.get(POLLO) == personajes.get(MAIZ) && personajes.get(GRANJERO) != personajes.get(POLLO)
						&& personajes.get(POLLO) == this.lado)
				{
					success = false;
					mensaje = "-El Pollo se come al Maiz en el lado " + this.lado;
				}
				else
				{
					int cant = 0;
					for (String pj : personajes.keySet()) 
					{
						if(personajes.get(pj) == this.lado)
						{
							cant++;
							mensaje = mensaje.concat("-El " + pj + " se encuentra en el lado " + this.lado + "\n");
						}
					}
					if(cant == 4 && this.lado == LADO_B)
					{
						end = true;
						mensaje = mensaje.concat("-Todos se encuentran en el lado B");
					}
				}
				synchronized (lock)
				{
					System.out.println("--LADO " + lado + "--");
					System.out.println(mensaje);
				}
				check.release();
			}
		}
	}
	

	public static void main(String[] args) throws InterruptedException 
	{
		Scanner entrada = new Scanner(System.in);
		LadoHilo ladoA = new LadoHilo(LADO_A);
		LadoHilo ladoB = new LadoHilo(LADO_B);
		ladoA.setDaemon(true);
		ladoB.setDaemon(true);
		
		personajes.put(GRANJERO, LADO_A);
		personajes.put(ZORRO, LADO_A);
		personajes.put(POLLO, LADO_A);
		personajes.put(MAIZ, LADO_A);
		
		ladoA.start();
		ladoB.start();
		
		System.out.println("Granjero, zorro, pollo y maiz se encuentran en el lado A.");
		while(true)
		{
			System.out.println(">El granjero cruza con: "); 
			personajeATransportar = entrada.nextLine(); // debe ser zorro, pollo, maiz o solo
			personajeATransportar = personajeATransportar.toUpperCase();
			while (!entradaValida())
			{
				System.out.println("Error al ingresar personaje, las opciones son:");
				System.out.println("SOLO - ZORRO - POLLO - MAIZ\n");
				System.out.println(">El granjero cruza con: "); 
				personajeATransportar = entrada.nextLine();
				personajeATransportar = personajeATransportar.toUpperCase();
			}
			if(transportar())
			{
				System.out.println("-Fin del programa");
				entrada.close();
				return;
			}
			System.out.println();
		}
	}
	
	
	public static boolean transportar() throws InterruptedException
	{
		if(personajes.get(personajeATransportar) == personajes.get("GRANJERO"))
		{
			char nuevoLado = (personajes.get(personajeATransportar) == LADO_A) ? LADO_B : LADO_A;
			if (personajeATransportar.equals(GRANJERO))
				System.out.println("El granjero cruza solo al lado " + nuevoLado);
			else
				System.out.println("El granjero transporta al " + personajeATransportar + " al lado " + nuevoLado);
			personajes.put(personajeATransportar, nuevoLado);
			personajes.put(GRANJERO, nuevoLado);
			synchronized (lock)
			{
				lock.notifyAll();
			}
			check.acquire(2);
			if(!success || end)
			{
				return true;
			}
		}
		else 
			System.out.println("El "+ personajeATransportar + " no se encuentra del mismo lado que el granjero.");
		return false;
	}
	
	public static boolean entradaValida()
	{
		if(personajeATransportar.equals(MAIZ) || personajeATransportar.equals(POLLO) 
		   || personajeATransportar.equals(ZORRO) || personajeATransportar.equals(SOLO))
			{
				if(personajeATransportar.equals(SOLO))
					personajeATransportar = GRANJERO;
				return true;
			}
		return false;
	}

}

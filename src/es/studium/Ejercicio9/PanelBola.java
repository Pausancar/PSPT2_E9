package es.studium.Ejercicio9;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import javax.swing.JPanel;
public class PanelBola extends JPanel implements Runnable
{
	private static final long serialVersionUID = 1L;
	private int numBolas = 9;
	Thread[] hilosBola;
	Bola[] bola;
	Bolin bolin;
	Thread hiloBolin;
	boolean hasPerdido = false;
	boolean movidoBolin = false;
	boolean primerMovimiento;
	long tiempoFinal;
	boolean ganaste;
	public PanelBola()
	{
		hilosBola = new Thread[numBolas];
		bola = new Bola[numBolas];
		bolin = new Bolin(200,530,0,0,0);
		hiloBolin = new Thread(this);


		for (int i=0;i<hilosBola.length;i++)
		{
			hilosBola[i]= new Thread(this);

			Random aleatorio = new Random();
			int velocidad = aleatorio.nextInt(20)+1;
			int posX=aleatorio.nextInt(375)+50;
			int posY=aleatorio.nextInt(340)+50;
			Color color = new Color(aleatorio.nextInt(254),
					aleatorio.nextInt(254), aleatorio.nextInt(254));
			bola[i]= new Bola(posX, posY, velocidad, color);
		}
		for (int i=0; i<hilosBola.length; ++i)
		{
			hilosBola[i].start();
		}
		hiloBolin.start();
		setBackground(Color.white);	
	}
	public void run()
	{

		for (int i=0; i<hilosBola.length; ++i)
		{

			while (Thread.currentThread()== hilosBola[i])
			{
				try
				{
					Thread.sleep(bola[i].velocidad());//la velocidad es el tiempo que se queda parado
					bola[i].mover();

				}
				catch (InterruptedException e) {}
				repaint();
			}
		}
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		for(int i=0; i<bola.length;++i)
		{
			bola[i].pinta(g);
		}
		bolin.pinta(g);
		g.drawString("Salida", 225, 30);
		compararPosicion();
	}
	public void mover(int direccion)
	{

		bolin.mover(direccion);
		compararPosicion();
		if(bolin.ganaste)
		{
			ganaste=true;
			bolin.ganaste = false;

		}
	}
	public void compararPosicion()
	{
		int xbola, ybola, xbolin, ybolin;
		xbolin = bolin.getPosicionActualEjeX();
		ybolin= bolin.getPosicionActualEjeY();
		for(int i=0;i<hilosBola.length;i++)
		{
			xbola = bola[i].getPosicionActualEjeX();
			ybola = bola[i].getPosicionActualEjeY();

			if(((xbolin<xbola+50)&(xbolin>xbola))||((xbolin + 30<xbola +50)&(xbolin + 30>xbola)))
			{
				if(((ybolin<ybola + 50)&(ybolin>ybola))||((ybolin +30>ybola)&(ybolin + 30< ybola + 50)))
				{
					hasPerdido=true;
				}
			}
		}
	}
}
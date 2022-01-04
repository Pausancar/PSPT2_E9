package es.studium.Ejercicio9;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;
public class Bolin
{
	int x, y;
	private Color color;
	Random aleatorio = new Random();
	int posicionActualEjeX, posicionActualEjeY;
	boolean ganaste = false;
	public Bolin(int xx, int yy, int r, int g, int b)
	{
		this.x = xx;
		this.y = yy;
		color = new Color(r,g,b);
	}
	public void mover(int direccion)
	{
		if(ganaste==false)
		{
			switch(direccion)
			{

			case 0:
				if(x<450)
					x+=5;
				break;
			case 1:
				if(y<550)
					y+=5;
				break;
			case 2:
				if(x>0)
					x-=5;
				break;
			case 3:
				if(y>0)
					y-=5;
				if(y<=30)
				{
					ganaste=true;
				}
				break;
			}
		}
	}
	public void pinta(Graphics g)
	{
		Graphics2D g2db = (Graphics2D)g;
		g2db.setColor(color);
		g2db.fillOval(x, y, 30, 30);
	}
	public int getPosicionActualEjeX()
	{
		return x;
	}
	public int getPosicionActualEjeY()
	{
		return y;
	}
}
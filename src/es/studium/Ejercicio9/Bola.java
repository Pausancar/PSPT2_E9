package es.studium.Ejercicio9;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;
public class Bola
{
	private int x, y;
	private int velocidad;
	private Color color;
	Random aleatorio = new Random();
	int posicionActualEjeX, posicionActualEjeY;
	public Bola(int xx, int yy, int v, Color c)
	{
		this.x = xx;
		this.y = yy;
		this.velocidad = v;
		this.color = c;
	}
	public int velocidad()
	{
		return velocidad;
	}
	public void mover()
	{
		switch(aleatorio.nextInt(4))
		{
		case 0:
			if(x<450)
				x++;
			break;
		case 1:
			if(y<550)
				y++;
			break;
		case 2:
			if(x>0)
				x--;
			break;
		case 3:
			if(y>0)
				y--;
			break;
		}
	}
	public void pinta(Graphics g)
	{
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(color);
		g2d.fillOval(x, y, 50, 50);
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
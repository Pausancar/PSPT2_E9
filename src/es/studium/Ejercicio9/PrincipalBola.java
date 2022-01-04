package es.studium.Ejercicio9;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class PrincipalBola extends JFrame implements KeyListener, WindowListener, ActionListener
{
	static PanelBola panel = new PanelBola();
	static PrincipalBola f;
	private static final long serialVersionUID = 1L;

	static String driver = "com.mysql.cj.jdbc.Driver";
	static String url = "jdbc:mysql://localhost:3306/juegobola?serverTimezone=UTC";
	static String login = "root";
	static String password = "Studium2020";
	static String sentencia = "";
	static Connection connection = null;
	static Statement st = null;
	static ResultSet rs = null;
	
	static String nombre;
	static JFrame menuPrincipal = new JFrame("Menú Principal");
	static JFrame menuNuevaPartida = new JFrame("Nueva Partida");
	static JFrame menuRanking = new JFrame("Ranking");
	static JLabel lblNombre = new JLabel("Nombre");
	static JTextField txtNombre = new JTextField(20);
	static JButton btnAceptar = new JButton("Aceptar");
	static JButton btnNuevaPartida = new JButton("NUEVA PARTIDA");
	static JButton btnRanking = new JButton("RANKING");
	static JButton btnSalir = new JButton("SALIR");
	static JLabel lblRanking = new JLabel("RANKING 3 MEJORES TIEMPOS");
	static JTextArea txtAreaRanking = new JTextArea(4,10);
	static JDialog dialog = new JDialog(menuNuevaPartida);
	static JLabel lblDialog1 = new JLabel("");
	static JLabel lblDialog2 = new JLabel("");
	long diferenciaTiempo, tiempoInicial, tiempoFinal;



	public static void conectar() throws ClassNotFoundException, SQLException
	{
		Class.forName(driver);
		connection = DriverManager.getConnection(url, login, password);
		st = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
	}
	public PrincipalBola()
	{
		super();
		this.addKeyListener(null);
		menuPrincipal.setLayout(new FlowLayout());
		menuPrincipal.add(btnNuevaPartida);
		menuPrincipal.add(btnRanking);
		menuPrincipal.add(btnSalir);
		btnNuevaPartida.addActionListener(this);
		btnRanking.addActionListener(this);
		btnSalir.addActionListener(this);
		menuPrincipal.setBounds(100,100,350,100);
		menuPrincipal.setVisible(true);

		menuNuevaPartida.setLayout(new FlowLayout());
		menuNuevaPartida.add(lblNombre);
		menuNuevaPartida.add(txtNombre);
		menuNuevaPartida.add(btnAceptar);
		btnAceptar.addActionListener(this);
		menuNuevaPartida.setBounds(100,100,280,100);
		menuNuevaPartida.setVisible(false);

		menuRanking.setLayout(new FlowLayout());
		menuRanking.add(lblRanking);
		menuRanking.add(txtAreaRanking);
		menuRanking.setBounds(100,100,350,200);
		menuRanking.setVisible(false);

	}
	public static void main(String args[])
	{
		f = new PrincipalBola();
		f.addKeyListener(f);
		f.setTitle("Bolas");
		f.setContentPane(panel);
		f.setBounds(100,100,500,600);

		dialog.setLayout(new FlowLayout());
		dialog.setBounds(100,100,200, 100);
		dialog.add(lblDialog1);
		dialog.add(lblDialog2);
		dialog.addWindowListener(f);
		menuPrincipal.addWindowListener(f);
	}
	@Override
	public void keyTyped(KeyEvent e)
	{
	}
	@Override
	public void keyPressed(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
		case 37://flecha izqda
			panel.mover(2);
			break;
		case 38://flecha arriba
			panel.mover(3);
			break;
		case 39://flecha dcha
			panel.mover(0);
			break;
		case 40://flecha abajo
			panel.mover(1);
			break;
		}

		if(panel.ganaste)
		{

			tiempoFinal = System.currentTimeMillis();
			diferenciaTiempo = (tiempoFinal - tiempoInicial)/1000;
			try {
				conectar();
				sentencia = "INSERT INTO tiempo VALUES (null, '"+ nombre+ "','"+ diferenciaTiempo+ "')";
				st.executeUpdate(sentencia);
			} catch (ClassNotFoundException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			f.setVisible(false);
			lblDialog1.setText("¡Has ganado!");
			lblDialog2.setText("Tu tiempo ha sido "+diferenciaTiempo+" segundos");
			dialog.setVisible(true);
			panel.ganaste = false;
		}
		if(panel.hasPerdido)
		{
			lblDialog1.setText("Has perdido...");
			lblDialog2.setText("¡Sigue intentándolo!");
			dialog.setVisible(true);
			f.setVisible(false);

		}
	}


	@Override
	public void keyReleased(KeyEvent e)
	{
	}
	@Override
	public void windowOpened(WindowEvent e)
	{
	}
	@Override
	public void windowClosing(WindowEvent e)
	{
		if(dialog.isActive())
		{
			txtNombre.setText("");
			panel.bolin.x = 200;
			panel.bolin.y = 530;
			panel.hasPerdido = false;
		}
		if(menuPrincipal.isActive())
		{
			System.exit(0);
		}
	}
	@Override
	public void windowClosed(WindowEvent e)
	{
	}
	@Override
	public void windowIconified(WindowEvent e)
	{
	}
	@Override
	public void windowDeiconified(WindowEvent e)
	{
	}
	@Override
	public void windowActivated(WindowEvent e)
	{
	}
	@Override
	public void windowDeactivated(WindowEvent e)
	{
	}
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource().equals(btnAceptar))
		{
			nombre=txtNombre.getText();
			tiempoInicial = System.currentTimeMillis();
			f.setVisible(true);
		}
		if(e.getSource().equals(btnNuevaPartida))
		{
			menuNuevaPartida.setVisible(true);	
		}
		if(e.getSource().equals(btnRanking))
		{
			try
			{
				conectar();
				ResultSet rs;
				int puesto=0;
				sentencia = "SELECT * FROM tiempo"+" ORDER BY 3 ASC"+" LIMIT 3";
				rs = st.executeQuery(sentencia);
				txtAreaRanking.selectAll();
				txtAreaRanking.setText("");
				txtAreaRanking.append("Puesto\tNombre\tTiempo\n");
				while(rs.next())
				{
					puesto+=1;
					txtAreaRanking.append(puesto+"\t"+rs.getString("nombreJugador")+"\t"+rs.getInt("tiempoJugador")+"\n");
				}
			}
			catch (SQLException sqle)
			{
				System.out.println(sqle);
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			menuRanking.setVisible(true);
		}
		if(e.getSource().equals(btnSalir))
		{
			System.exit(0);
		}
	}
}
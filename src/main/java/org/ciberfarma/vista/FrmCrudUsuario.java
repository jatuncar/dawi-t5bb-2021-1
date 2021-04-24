package org.ciberfarma.vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.ciberfarma.modelo.Usuario;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;

public class FrmCrudUsuario extends JFrame {

	private JPanel contentPane;
	private JTextField txtCodigo;
	private JTextField txtNombre;
	private JTextField txtApellido;
	private JTextField txtUsuario;
	private JTextField txtClave;
	private JTextField txtFecha;
	private JTextField txtTipo;
	private JTextField txtEstado;
	private JTextArea txtS;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmCrudUsuario frame = new FrmCrudUsuario();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FrmCrudUsuario() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 365);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Código : ");
		lblNewLabel.setBounds(10, 11, 46, 14);
		contentPane.add(lblNewLabel);
		
		txtCodigo = new JTextField();
		txtCodigo.setBounds(68, 8, 86, 20);
		contentPane.add(txtCodigo);
		txtCodigo.setColumns(10);
		
		JLabel lblNombre = new JLabel("Nombre :");
		lblNombre.setBounds(10, 39, 46, 14);
		contentPane.add(lblNombre);
		
		txtNombre = new JTextField();
		txtNombre.setColumns(10);
		txtNombre.setBounds(68, 36, 184, 20);
		contentPane.add(txtNombre);
		
		JLabel lblApellido = new JLabel("Apellido :");
		lblApellido.setBounds(10, 67, 46, 14);
		contentPane.add(lblApellido);
		
		txtApellido = new JTextField();
		txtApellido.setColumns(10);
		txtApellido.setBounds(68, 64, 184, 20);
		contentPane.add(txtApellido);
		
		JLabel lblUsuario = new JLabel("Usuario :");
		lblUsuario.setBounds(10, 95, 46, 14);
		contentPane.add(lblUsuario);
		
		txtUsuario = new JTextField();
		txtUsuario.setColumns(10);
		txtUsuario.setBounds(68, 92, 86, 20);
		contentPane.add(txtUsuario);
		
		JLabel lblClave = new JLabel("Clave :");
		lblClave.setBounds(176, 95, 46, 14);
		contentPane.add(lblClave);
		
		txtClave = new JTextField();
		txtClave.setColumns(10);
		txtClave.setBounds(234, 92, 86, 20);
		contentPane.add(txtClave);
		
		JLabel lblFecha = new JLabel("Fch. Nac:");
		lblFecha.setBounds(10, 123, 46, 14);
		contentPane.add(lblFecha);
		
		txtFecha = new JTextField();
		txtFecha.setColumns(10);
		txtFecha.setBounds(68, 120, 86, 20);
		contentPane.add(txtFecha);
		
		JLabel lblTipo = new JLabel("Tipo :");
		lblTipo.setBounds(10, 151, 46, 14);
		contentPane.add(lblTipo);
		
		txtTipo = new JTextField();
		txtTipo.setColumns(10);
		txtTipo.setBounds(68, 148, 86, 20);
		contentPane.add(txtTipo);
		
		JLabel lblEstado = new JLabel("Estado :");
		lblEstado.setBounds(176, 151, 46, 14);
		contentPane.add(lblEstado);
		
		txtEstado = new JTextField();
		txtEstado.setColumns(10);
		txtEstado.setBounds(234, 148, 86, 20);
		contentPane.add(txtEstado);
		
		JButton btnRegistrar = new JButton("Registrar");
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				registro();
			}
		});
		btnRegistrar.setBounds(335, 35, 89, 23);
		contentPane.add(btnRegistrar);
		
		JButton btnConsultar = new JButton("Consultar");
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				consultar();
			}
		});
		btnConsultar.setBounds(335, 73, 89, 23);
		contentPane.add(btnConsultar);
		
		JButton btnListado = new JButton("Listado");
		btnListado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listado();
			}
		});
		btnListado.setBounds(335, 114, 89, 23);
		contentPane.add(btnListado);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 187, 414, 128);
		contentPane.add(scrollPane);
		
		txtS = new JTextArea();
		scrollPane.setViewportView(txtS);
	}

	void listado() {
		// Obtener un listado de los usuario
		EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("jpa_sesion01");
		EntityManager em = fabrica.createEntityManager();
		
		//TypedQuery<Usuario> consulta = em.createNamedQuery("Usuario.findAll", Usuario.class);
		//List<Usuario> lstUsuarios = consulta.getResultList();
		
		List<Usuario> lstUsuarios;
		if (txtTipo.getText().isEmpty()) {
			lstUsuarios = em.createNamedQuery("Usuario.findAll", Usuario.class).getResultList();
		} else {
			int tipo = Integer.parseInt(txtTipo.getText());
			lstUsuarios = em.createNamedQuery("Usuario.findAllxTipo", Usuario.class).
					setParameter("xtipo", tipo).getResultList();
		}
		
		// muestro el listado en el txt/pdf
		txtS.setText("Listado de Usuario\n");
		for (Usuario u : lstUsuarios) {
			txtS.append(u.getCodigo() + "\t" + u.getNombre() + "\t" + u.getApellido() +"\n");
		}
		
	}

	void consultar() {
		// obtener el código a buscar
		int codigo = Integer.parseInt(txtCodigo.getText());
		// buscar el código en los Usuarios(Entidad), si existe muestra los datos, sino avisa
		EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("jpa_sesion01");
		EntityManager em = fabrica.createEntityManager();
		
		Usuario u = em.find(Usuario.class, codigo);  
		
		if (u == null) {
			JOptionPane.showMessageDialog(this, "Usuario NO registrado");
		} else {
			txtNombre.setText(u.getNombre());
			txtApellido.setText(u.getApellido());
			txtUsuario.setText(u.getUsuario());
			txtClave.setText(u.getClave());
			txtFecha.setText(u.getFnacim());
			txtTipo.setText(u.getTipo()+"");
			txtEstado.setText(u.getEstado()+"");
		}
	}

	void registro() {
		// entradas
		String nombre = txtNombre.getText();
		String apellido = txtApellido.getText();
		String usuario  = txtUsuario.getText();
		String clave    = txtClave.getText();
		String fecha    = txtFecha.getText();
		int tipo = Integer.parseInt(txtTipo.getText());
		int estado = Integer.parseInt(txtEstado.getText());
		
		Usuario u = new Usuario();
		u.setNombre(nombre);
		u.setApellido(apellido);
		u.setUsuario(usuario);
		u.setClave(clave);
		u.setFnacim(fecha);
		u.setTipo(tipo);
		u.setEstado(estado);
		
		// proceso
		EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("jpa_sesion01");
		EntityManager em = fabrica.createEntityManager();
		
		try {
			em.getTransaction().begin();
			em.persist(u);
			em.getTransaction().commit();
			JOptionPane.showMessageDialog(this, "Usuario registrado");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error al registrar : " + e.getMessage());
		} finally {
			em.close();
		}
		
		
		
		
		
		
	}
}

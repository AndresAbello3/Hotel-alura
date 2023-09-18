package com.hotel.alura.view;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import com.hotel.alura.controller.HuespedController;
import com.hotel.alura.controller.ReservaController;
import com.hotel.alura.modelo.Huesped;
import com.hotel.alura.modelo.Reserva;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.SystemColor;
import java.util.List;
import java.util.Optional;
import javax.swing.JTabbedPane;
import java.awt.Toolkit;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

@SuppressWarnings("serial")

public class Busqueda extends JFrame {

	private JPanel contentPane;
	private JTextField txtBuscar;
	private JTable tablaHuesped;
	private JTable tablaReserva;
	private DefaultTableModel modeloReserva;
	private DefaultTableModel modeloHuesped;
	private JLabel labelAtras;
	private JLabel labelExit;
	
	int xMouse, yMouse;
	String reserva;
	String huespedes;
	
	private ReservaController reservaController;
	private HuespedController huespedController;




	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Busqueda frame = new Busqueda();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	public Busqueda() {
		
        this.reservaController = new ReservaController();
		this.huespedController = new HuespedController();
        
		initializeUI();
        setUpSearchListener();
	}
	
	
	
	private void initializeUI() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Busqueda.class.getResource("/imagenes/lupa2.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 571);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setUndecorated(true);
		setUndecorated(true);
		new JScrollPane(tablaReserva);
		
		
		txtBuscar = new JTextField();
		txtBuscar.setFocusable(false);
		txtBuscar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				 if (txtBuscar.getText().equals("Búsqueda...")) {
					 txtBuscar.setText("");
					 txtBuscar.setFocusable(true);
					 txtBuscar.requestFocus();
					 txtBuscar.setForeground(Color.black);
			     }
			}
		});
		
		txtBuscar.setFont(new Font("Roboto", Font.PLAIN, 16));
		txtBuscar.setText("Búsqueda...");
		txtBuscar.setBounds(540, 127, 193, 31);
		txtBuscar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		txtBuscar.setForeground(SystemColor.activeCaptionBorder);
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);
		
		
		JLabel lblNewLabel_4 = new JLabel("SISTEMA DE BÚSQUEDA");
		lblNewLabel_4.setForeground(new Color(12, 138, 199));
		lblNewLabel_4.setFont(new Font("Roboto Black", Font.BOLD, 24));
		lblNewLabel_4.setBounds(331, 62, 280, 42);
		contentPane.add(lblNewLabel_4);
		
		JTabbedPane panel = new JTabbedPane(JTabbedPane.TOP);
		panel.setBackground(new Color(12, 138, 199));
		panel.setFont(new Font("Roboto", Font.PLAIN, 16));
		panel.setBounds(20, 169, 865, 328);
		contentPane.add(panel);
		


		// AGREGAR LA TABLA DE RESERVAS A UN JScrollPane.
		tablaReserva = new JTable();
		JScrollPane scrollPaneReservas = new JScrollPane();
		scrollPaneReservas.setViewportView(tablaReserva);
		tablaReserva.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaReserva.setFont(new Font("Roboto", Font.PLAIN, 16));
		panel.addTab("Reservas", new ImageIcon(Busqueda.class.getResource("/imagenes/reservado.png")), scrollPaneReservas, null);
		modeloReserva = (DefaultTableModel) tablaReserva.getModel();
		modeloReserva.addColumn("Numero de Reserva");
		modeloReserva.addColumn("Fecha Check In");
		modeloReserva.addColumn("Fecha Check Out");
		modeloReserva.addColumn("Valor");
		modeloReserva.addColumn("Forma de Pago");
		tablaReserva.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		mostrarTablaReserva();
		

		// AGREGAR LA TABLA DE HUESPEDES A UN JScrollPane.
		tablaHuesped = new JTable();
        JScrollPane scrollPaneHuespedes = new JScrollPane();
        scrollPaneHuespedes.setViewportView(tablaHuesped);
        
		tablaHuesped.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaHuesped.setFont(new Font("Roboto", Font.PLAIN, 16));
		panel.addTab("Huéspedes", new ImageIcon(Busqueda.class.getResource("/imagenes/pessoas.png")), scrollPaneHuespedes, null);
		modeloHuesped = (DefaultTableModel) tablaHuesped.getModel();
		modeloHuesped.addColumn("Numero de Huesped");
		modeloHuesped.addColumn("Nombre");
		modeloHuesped.addColumn("Apellido");
		modeloHuesped.addColumn("Fecha de Nacimiento");
		modeloHuesped.addColumn("Nacionalidad");
		modeloHuesped.addColumn("Telefono");
		modeloHuesped.addColumn("Numero de Reserva");
		mostrarTablaHuesped();
		

		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/Ha-100px.png")));
		lblNewLabel_2.setBounds(56, 51, 104, 107);
		contentPane.add(lblNewLabel_2);
		
		JPanel header = new JPanel();
		header.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				headerMouseDragged(e);
			     
			}
		});
		header.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				headerMousePressed(e);
			}
		});
		header.setLayout(null);
		header.setBackground(Color.WHITE);
		header.setBounds(0, 0, 910, 36);
		contentPane.add(header);
		
		JPanel btnAtras = new JPanel();
		btnAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();				
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnAtras.setBackground(new Color(12, 138, 199));
				labelAtras.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) {
				 btnAtras.setBackground(Color.white);
			     labelAtras.setForeground(Color.black);
			}
		});
		btnAtras.setLayout(null);
		btnAtras.setBackground(Color.WHITE);
		btnAtras.setBounds(0, 0, 53, 36);
		header.add(btnAtras);
	

		labelAtras = new JLabel("<");
		labelAtras.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtras.setFont(new Font("Roboto", Font.PLAIN, 23));
		labelAtras.setBounds(0, 0, 53, 36);
		btnAtras.add(labelAtras);
		
		JPanel btnexit = new JPanel();
		btnexit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();
			}
			@Override
			public void mouseEntered(MouseEvent e) { //Al usuario pasar el mouse por el botón este cambiará de color
				btnexit.setBackground(Color.red);
				labelExit.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) { //Al usuario quitar el mouse por el botón este volverá al estado original
				 btnexit.setBackground(Color.white);
			     labelExit.setForeground(Color.black);
			}
		});
		btnexit.setLayout(null);
		btnexit.setBackground(Color.WHITE);
		btnexit.setBounds(857, 0, 53, 36);
		header.add(btnexit);
		
		labelExit = new JLabel("X");
		labelExit.setHorizontalAlignment(SwingConstants.CENTER);
		labelExit.setForeground(Color.BLACK);
		labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));
		labelExit.setBounds(0, 0, 53, 36);
		btnexit.add(labelExit);
		
		JSeparator separator_1_2 = new JSeparator();
		separator_1_2.setForeground(new Color(12, 138, 199));
		separator_1_2.setBackground(new Color(12, 138, 199));
		separator_1_2.setBounds(539, 159, 193, 2);
		contentPane.add(separator_1_2);
		
		
		// Crea un JLabel para mostrar el icono GIF
		JLabel lblBuscando = new JLabel();
		lblBuscando.setBounds(740, 120, 122, 45);
		lblBuscando.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/buscar.gif")));
		contentPane.add(lblBuscando);

		
		JPanel btnEditar = new JPanel();
		btnEditar.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
			int filaReservas = tablaReserva.getSelectedRow();			
			int filaHuespedes = tablaHuesped.getSelectedRow();
			if(filaReservas >=0) {
				actualizarReserva();
				limpiarTabla();
				mostrarTablaReserva();
				mostrarTablaHuesped();			
			}else if(filaHuespedes >= 0 ){
				actualizarHuesped();
				mostrarTablaHuesped();
				mostrarTablaReserva();
			}
		}
	});
		btnEditar.setLayout(null);
		btnEditar.setBackground(new Color(12, 138, 199));
		btnEditar.setBounds(635, 508, 122, 35);
		btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEditar);
		
		JLabel lblEditar = new JLabel("EDITAR");
		lblEditar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditar.setForeground(Color.WHITE);
		lblEditar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEditar.setBounds(0, 0, 122, 35);
		btnEditar.add(lblEditar);
		
		JPanel btnEliminar = new JPanel();
		btnEliminar.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int filaReserva = tablaReserva.getSelectedRow();
				int filaHuesped = tablaHuesped.getSelectedRow();
				if(filaReserva >= 0) {
					reserva = tablaReserva.getValueAt(filaReserva, 0).toString();
					int botonConfirmarReserva = JOptionPane.showConfirmDialog(null, "¿Desea borrar la reserva?");
					if(botonConfirmarReserva == JOptionPane.YES_OPTION) {
						String dato = tablaReserva.getValueAt(filaReserva, 0).toString();
						reservaController.eliminar(Integer.valueOf(dato));
						JOptionPane.showMessageDialog(contentPane, "Registro eliminado con exito!");
						limpiarTabla();
						mostrarTablaReserva();
						mostrarTablaHuesped();
					}
				} else if(filaHuesped >=0) {
					huespedes = tablaHuesped.getValueAt(filaHuesped,0).toString();
					int botonConfirmarHuesped = JOptionPane.showConfirmDialog(null, "Desea Borrar al huesped?");
					if(botonConfirmarHuesped == JOptionPane.YES_OPTION) {
						String dato = tablaHuesped.getValueAt(filaHuesped, 0).toString();
						huespedController.eliminar(Integer.valueOf(dato));
						JOptionPane.showMessageDialog(contentPane,"Huesped borrado!");
						limpiarTabla();
						mostrarTablaHuesped();
						mostrarTablaReserva();
						
					}
				}else {
					JOptionPane.showMessageDialog(null,"Hubo un error al eliminar");

				}
			}
		});
		btnEliminar.setLayout(null);
		btnEliminar.setBackground(new Color(12, 138, 199));
		btnEliminar.setBounds(767, 508, 122, 35);
		btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEliminar);
		
		JLabel lblEliminar = new JLabel("ELIMINAR");
		lblEliminar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEliminar.setForeground(Color.WHITE);
		lblEliminar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEliminar.setBounds(0, 0, 122, 35);
		btnEliminar.add(lblEliminar);
		setResizable(false);
		
		
		setVisible(true);
	}
	
	
	
	private void setUpSearchListener() {
	//BUSQUEDA EN TIEMPO REAL
		txtBuscar.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				 buscarEnTiempoReal();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				 buscarEnTiempoReal();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
			// No es necesario implementar este método para un JTextField.
			}
		});
	}
	
	

	    private List<Reserva> mostrarReserva(){
	    	return this.reservaController.mostrar();
	    }
	    
	    private List<Reserva> buscarIDreserva() {
	        String textoBusqueda = txtBuscar.getText();
	        List<Reserva> reservas = mostrarReserva();
	        List<Reserva> resultados = new ArrayList<>();

	        for (Reserva reserva : reservas) {
	            if (String.valueOf(reserva.getId()).contains(textoBusqueda)) {
	                resultados.add(reserva);
	            }
	        }

	        return resultados;
	    }
	    
		private void mostrarTablaReserva() {
			List<Reserva> reserva = mostrarReserva();
			modeloReserva.setRowCount(0);
			try {
				for(Reserva reservas : reserva ) {
					modeloReserva.addRow(new Object[] {
							reservas.getId(), reservas.getDataEntrada(), reservas.getDataSalida(),reservas.getValorReserva(),
							reservas.getFormaPago()
					});
				}
			} catch (Exception e) {
				throw e;
			}
		}
		
		
		
		private void actualizarReserva() {
		    Optional.ofNullable(modeloReserva.getValueAt(tablaReserva.getSelectedRow(), tablaReserva.getSelectedColumn()))
		        .ifPresent(fila -> {
		            LocalDate diaEntrada;
		            LocalDate diaSalida;

		            try {
		                DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		                diaEntrada = LocalDate.parse(modeloReserva.getValueAt(tablaReserva.getSelectedRow(), 1).toString(), dateFormat);
		                diaSalida = LocalDate.parse(modeloReserva.getValueAt(tablaReserva.getSelectedRow(), 2).toString(), dateFormat);
		            } catch (DateTimeException e) {
		                throw new RuntimeException(e);
		            }

		            String valor = calcularPrecioDeReserva(diaEntrada, diaSalida);
		            String formaPago = (String) modeloReserva.getValueAt(tablaReserva.getSelectedRow(), 4);
		            Integer id = Integer.valueOf(modeloReserva.getValueAt(tablaReserva.getSelectedRow(), 0).toString());

		            if (tablaReserva.getSelectedColumn() == 0) {
		                JOptionPane.showMessageDialog(this, "No se puede editar los id");
		            } else {
		                if (!valor.isEmpty()) {
		                    this.reservaController.actualizarReserva(diaEntrada, diaSalida, valor, formaPago, id);
		                    JOptionPane.showMessageDialog(this, String.format("Registro modificado con éxito"));
		                } else {
		                    JOptionPane.showMessageDialog(this, "No se puede actualizar debido a un error en los datos ingresados!!!");
		                }
		            }
		        });
		}
      
		public String calcularPrecioDeReserva(LocalDate diaEntrada, LocalDate diaSalida) {
		    if (diaEntrada != null && diaSalida != null) {
		        if (diaEntrada.isEqual(diaSalida)) {
		            // Si la fecha de entrada es igual a la fecha de salida, asigna un valor estándar de 20
		            return "S/ 20";
		        } else if (diaEntrada.isBefore(diaSalida)) {
		            int dias = (int) ChronoUnit.DAYS.between(diaEntrada, diaSalida);
		            int precioPorNoche = 30;
		            int total = dias * precioPorNoche;
		            return "S/ " + total;
		        } else {
		            // Si la fecha de entrada es después de la fecha de salida, muestra un mensaje de error
		            JOptionPane.showMessageDialog(
		                null,
		                "Error, La fecha de entrada no puede ser después de la fecha de salida",
		                "Error en la fecha",
		                JOptionPane.ERROR_MESSAGE
		            );
		            // Deja el valor sin cambios
		            return ""; 
		        }
		    } else {
		        return "";
		    }
		}
		
		
		
		 private void actualizarHuesped() {
		    	Optional.ofNullable(modeloHuesped.getValueAt(tablaHuesped.getSelectedRow(), tablaHuesped.getSelectedColumn()))
		    	.ifPresentOrElse(filaHuespedes ->{
		    		
		    		String nombre = (String) modeloHuesped.getValueAt(tablaHuesped.getSelectedRow(), 1);
		    		String apellido = (String) modeloHuesped.getValueAt(tablaHuesped.getSelectedRow(), 2);
		    		LocalDate fechaNacimiento = LocalDate.parse(modeloHuesped.getValueAt(tablaHuesped.getSelectedRow(),3 ).toString());
		    		String nacionalidad = (String) modeloHuesped.getValueAt(tablaHuesped.getSelectedRow(), 4);
		    		String telefono = (String) modeloHuesped.getValueAt(tablaHuesped.getSelectedRow(), 5);
		    		Integer id_reserva = Integer.valueOf(modeloHuesped.getValueAt(tablaHuesped.getSelectedRow(),6).toString());
		    		Integer id = Integer.valueOf(modeloHuesped.getValueAt(tablaHuesped.getSelectedRow(),0).toString());
		    		
		    		
		    		if(tablaHuesped.getSelectedColumn()== 6) {
		     JOptionPane.showMessageDialog(this, "No se pueden modificar los id");  
            } else {
            	 this.huespedController.actualizar(nombre, apellido, fechaNacimiento, nacionalidad, telefono, id_reserva, id);
      		   JOptionPane.showMessageDialog(this, String.format( "Registro modificado con exito"));  
            }
		    
		    	}, ()-> JOptionPane.showInternalMessageDialog(this, "Por favor"));
		 }
			    		
		 
		private void limpiarTabla() {
			((DefaultTableModel)tablaHuesped.getModel()).setRowCount(0);
			((DefaultTableModel)tablaReserva.getModel()).setRowCount(0);

		}
		
		private	List<Huesped> mostrarHuesped(){
			return this.huespedController.mostrarHuesped();
		}
		
		private List<Huesped> buscarHuespedId() {
		    String textoBusqueda = txtBuscar.getText();
		    List<Huesped> huespedes = mostrarHuesped();
		    List<Huesped> resultados = new ArrayList<>();

		    for (Huesped huesped : huespedes) {
		        if (String.valueOf(huesped.getIdReserva()).contains(textoBusqueda)) {
		            resultados.add(huesped);
		        }
		    }

		    return resultados;
		}
		
		private void mostrarTablaHuesped() {
			List<Huesped> huespedes = mostrarHuesped();
			modeloHuesped.setRowCount(0);
			try {
				for(Huesped huespedes1 : huespedes ) {
					modeloHuesped.addRow(new Object[] {
							huespedes1.getId(), huespedes1.getNombre(), huespedes1.getApellido(),huespedes1.getFechaNacimiento(),
							huespedes1.getNacionalidad(),huespedes1.getTelefono(),huespedes1.getIdReserva()
					});
				}
			} catch (Exception e) {
				throw e;
			}
		}
		
		

		private void buscarEnTiempoReal() {
		    limpiarTabla();

		    String textoBusqueda = txtBuscar.getText().trim();

		    if (textoBusqueda.isEmpty()) {
		        mostrarTablaReserva();
		        mostrarTablaHuesped();
		    } else {
		        if (esNumero(textoBusqueda)) {
		            // Realiza la búsqueda por ID
		            buscarEnReservas(textoBusqueda);
		            buscarEnHuespedes(textoBusqueda);
		        } else {
		            // Realiza la búsqueda por apellidos
		            buscarEnHuespedesPorApellido(textoBusqueda);
		        
		        }
		    }
		}

		private boolean esNumero(String texto) {
		    try {
		        Integer.parseInt(texto);
		        return true;
		    } catch (NumberFormatException e) {
		        return false;
		    }
		}
		
		
		private void buscarEnReservas(String textoBusqueda) {
		    List<Reserva> reservas = buscarIDreserva(); // Cambiar a buscarIDreserva para buscar por ID
		    for (Reserva reserva : reservas) {
		        modeloReserva.addRow(new Object[] {
		            reserva.getId(), reserva.getDataEntrada(), reserva.getDataSalida(),
		            reserva.getValorReserva(), reserva.getFormaPago()
		        });
		    }
		}

		private void buscarEnHuespedes(String textoBusqueda) {
		    List<Huesped> huespedes = buscarHuespedId(); // Cambiar a buscarHuespedId para buscar por ID
		    for (Huesped huesped : huespedes) {
		        modeloHuesped.addRow(new Object[] {
		            huesped.getId(), huesped.getNombre(), huesped.getApellido(),
		            huesped.getFechaNacimiento(), huesped.getNacionalidad(),
		            huesped.getTelefono(), huesped.getIdReserva()
		        });
		    }
		}
		
		
		private void buscarEnHuespedesPorApellido(String apellido) {
		    List<Huesped> huespedes = buscarApellidoEnHuespedes(apellido);
		    for (Huesped huesped : huespedes) {
		        // Muestra la ID de reserva correspondiente en la tabla de Reservas
		        mostrarIDReservaEnTablaReservas(huesped.getIdReserva());
		        // Agrega los datos del huésped en la tabla de Huéspedes
		        modeloHuesped.addRow(new Object[] {
		            huesped.getId(), huesped.getNombre(), huesped.getApellido(),
		            huesped.getFechaNacimiento(), huesped.getNacionalidad(),
		            huesped.getTelefono(), huesped.getIdReserva()
		        });
		    }
		}

		// Método para buscar por apellido en la lista de huéspedes:
		private List<Huesped> buscarApellidoEnHuespedes(String apellido) {
		    List<Huesped> huespedes = mostrarHuesped();
		    List<Huesped> resultados = new ArrayList<>();

		    for (Huesped huesped : huespedes) {
		        if (huesped.getApellido().toLowerCase().contains(apellido.toLowerCase())) {
		            resultados.add(huesped);
		        }
		    }

		    return resultados;
		}

		
		
		// Método para mostrar la ID de reserva y otros datos en la tabla de Reservas:
		private void mostrarIDReservaEnTablaReservas(int idReserva) {
		    List<Reserva> reservas = mostrarReserva();
		    
		    for (Reserva reserva : reservas) {
		        if (reserva.getId() == idReserva) {
		            modeloReserva.addRow(new Object[] {
		                reserva.getId(), reserva.getDataEntrada(), reserva.getDataSalida(),
		                reserva.getValorReserva(), reserva.getFormaPago()
		            });
		        }
		    }
		}
		
		

		 private void headerMousePressed(java.awt.event.MouseEvent evt) {
		        xMouse = evt.getX();
		        yMouse = evt.getY();
		    }

		    private void headerMouseDragged(java.awt.event.MouseEvent evt) {
		        int x = evt.getXOnScreen();
		        int y = evt.getYOnScreen();
		        this.setLocation(x - xMouse, y - yMouse);
		    }
	}

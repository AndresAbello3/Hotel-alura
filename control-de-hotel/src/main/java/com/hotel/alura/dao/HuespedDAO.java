package com.hotel.alura.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

import com.hotel.alura.modelo.Huesped;

public class HuespedDAO {
	
final private Connection con;
	
	public HuespedDAO(Connection con){
		super();
		this.con = con;
	}
	
	
	
	public void guardar(Huesped huesped) {
		 try {
			 String sql = "INSERT INTO registro_huespedes (nombre, apellido, fecha_nacimiento, nacionalidad"
						+ ", telefono, id_reserva) VALUES(?,?,?,?,?,?)";
		 	try(PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
				preparedStatement.setString(1, huesped.getNombre());
				preparedStatement.setString(2, huesped.getApellido());
				preparedStatement.setObject(3, huesped.getFechaNacimiento());
				preparedStatement.setString(4, huesped.getNacionalidad());
				preparedStatement.setString(5, huesped.getTelefono());
				preparedStatement.setInt(6, huesped.getIdReserva());
				preparedStatement.execute();
				
				try(ResultSet rst = preparedStatement.getGeneratedKeys()){
					while (rst.next()) {
						huesped.setId(rst.getInt(1));
					}
				}	
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		 
	}
	

	
	public List<Huesped> mostrar(){
		List<Huesped> huesped = new ArrayList<Huesped>();
		try {
			String sql = "SELECT id, nombre, apellido, fecha_nacimiento, nacionalidad,"
					+ "telefono, id_reserva FROM registro_huespedes";
			
			try(PreparedStatement preparedStatement = con.prepareStatement(sql)){
				preparedStatement.execute();
				transformarResultado(huesped,preparedStatement);
			}
			return huesped;
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}
	
	
	
	
	
	public List<Huesped> buscarId(String id){
		List<Huesped> huesped = new ArrayList<Huesped>();
		try {
			String sql = "SELECT rh.id, rh.nombre, rh.apellido, rh.fecha_nacimiento, rh.nacionalidad, rh.telefono, rh.id_reserva " +
                    "FROM registro_huespedes rh " +
                    "INNER JOIN registro_reservas rr ON rh.id_reserva = rr.ID " +
                    "WHERE rr.ID = ?";
			try(PreparedStatement preparedStatement = con.prepareStatement(sql)){
				preparedStatement.setString(1, id);
				preparedStatement.execute();
				transformarResultado(huesped,preparedStatement);
			}
			return huesped;
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}
	
	

	public void actualizar(String nombre, String apellido, LocalDate fechaNacimiento, String nacionalidad, String telefono,
			Integer idReserva, Integer id) {
		try (PreparedStatement preparedStatement = con.prepareStatement("" + "UPDATE registro_huespedes SET nombre=?, apellido=?, fecha_nacimiento=?, nacionalidad=?," + "telefono=?, id_reserva=? WHERE  id= ?")) {
			preparedStatement.setString(1, nombre);
			preparedStatement.setString(2, apellido);
			preparedStatement.setObject(3, fechaNacimiento);
			preparedStatement.setString(4, nacionalidad);
			preparedStatement.setString(5, telefono);
			preparedStatement.setInt(6, idReserva);
			preparedStatement.setInt(7, id);
			preparedStatement.execute();
		} catch (SQLException e) {	
			throw new RuntimeException(e);
		}
	}
	
	
	
	
	public void eliminar(Integer id) {
		try(PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM registro_huespedes WHERE id = ?")) {
			preparedStatement.setInt(1, id);
			preparedStatement.execute();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
	} 
	 
}
	
	
	private void transformarResultado(List<Huesped> huesped, PreparedStatement pstm) throws SQLException{
		try(ResultSet rst = pstm.executeQuery()){
			while(rst.next()) {
				int id = rst.getInt("id");
				String nombre = rst.getString("nombre");
				String apellido = rst.getString("apellido");
	            LocalDate fechaNacimiento = rst.getDate("fecha_nacimiento").toLocalDate().plusDays(0);
				String nacionalidad = rst.getString("nacionalidad");
				String telefono = rst.getString("telefono");
				int idReserva = rst.getInt("id_reserva");
				
	          Huesped huespedes = new Huesped(id, nombre, apellido, fechaNacimiento, nacionalidad, telefono, idReserva);
	          huesped.add(huespedes);
			}
		}
	}
}





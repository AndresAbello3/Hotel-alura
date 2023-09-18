package com.hotel.alura.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

import com.hotel.alura.modelo.Reserva;

public class ReservaDAO {

	final private Connection con;
	
	public ReservaDAO(Connection con){
		super();
		this.con = con;
	}
	
	
	public void guardar (Reserva reserva) {
		try {
		String sql = "INSERT INTO registro_reservas (dia_entrada, dia_salida, valor_reserva, forma_pago)"
				+ "VALUES(?,?,?,?)";
		
		try (PreparedStatement preparedStatement = con.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)){
			
			preparedStatement.setObject(1, reserva.getDataEntrada());
			preparedStatement.setObject(2, reserva.getDataSalida());
			preparedStatement.setString(3, reserva.getValorReserva());
			preparedStatement.setString(4, reserva.getFormaPago());
			preparedStatement.executeUpdate();
			
			try(ResultSet resultSet = preparedStatement.getGeneratedKeys()){
				while (resultSet.next()) {
					reserva.setId(resultSet.getInt(1));
				}
			}
		}
		} catch (SQLException e) {
			throw new RuntimeException("x" + e.getMessage(),e);
		}
	}
	
	
	
	
	public List<Reserva> mostrar(){
		List<Reserva> reservas = new ArrayList<Reserva>();
		try {
			String sql = "SELECT id, dia_entrada, dia_salida, valor_reserva, forma_pago FROM registro_reservas";
			
			try(PreparedStatement preparedStatement = con.prepareStatement(sql)){
				preparedStatement.execute();
				
				transformarResultado(reservas,preparedStatement);
			}
			return reservas;
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}
	
	
	
	
	public List<Reserva> buscarId(String id){
		List<Reserva> reservas = new ArrayList<Reserva>();
		try {
			String sql = "SELECT id, dia_entrada, dia_salida, valor_reserva, forma_pago FROM registro_reservas WHERE id= ?";
			
			try(PreparedStatement preparedStatement = con.prepareStatement(sql)){
				preparedStatement.setString(1, id);
				preparedStatement.execute();
				
				transformarResultado(reservas,preparedStatement);
			}
			return reservas;
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}
	
	
	
	
	
	public void actualizar(LocalDate diaEntrada, LocalDate diaSalida, String valorReserva, String formaPago, Integer id) {
		try (PreparedStatement preparedStatement = con.prepareStatement("UPDATE registro_reservas SET "
				+ "dia_entrada=?, dia_salida=?, valor_reserva=?,forma_pago=? WHERE  id= ?")) {
			preparedStatement.setObject(1, java.sql.Date.valueOf(diaEntrada));
			preparedStatement.setObject(2, java.sql.Date.valueOf(diaSalida));
			preparedStatement.setString(3, valorReserva);
			preparedStatement.setString(4, formaPago);
			preparedStatement.setInt(5, id);
			preparedStatement.execute();
		} catch (SQLException e) {
			
			throw new RuntimeException("x" + e.getMessage(),e);
		}
	}
	
	
	public void eliminar(Integer id) {
	    try {
	    	// ELIMINAR LOS REGISTROS RELACIONADOS EN LA TABLA registro_huespedes
	        final PreparedStatement deleteHuespedesStatement = con.prepareStatement("DELETE FROM registro_huespedes WHERE ID_RESERVA = ?");
	        deleteHuespedesStatement.setInt(1, id);
	        deleteHuespedesStatement.executeUpdate();

	        // LUEGO SE ELIMINAN LAS  RESERVAS RELACIONADAS AL ID_RESERVA
	        final PreparedStatement deleteReservaStatement = con.prepareStatement("DELETE FROM registro_reservas WHERE ID = ?");
	        deleteReservaStatement.setInt(1, id);
	        deleteReservaStatement.executeUpdate();
	    } catch (SQLException e) {
	        throw new RuntimeException(e);
	    }
	}
	
	
	
	private void transformarResultado(List<Reserva> reservas, PreparedStatement preparedStatement) throws SQLException{
		try(ResultSet rst = preparedStatement.getResultSet()){
			while(rst.next()) {
				int id = rst.getInt("id");
				LocalDate diaEntrada = rst.getDate("dia_entrada").toLocalDate().plusDays(0);
				LocalDate diaSalida = rst.getDate("dia_salida").toLocalDate().plusDays(0);
				String valorReserva = rst.getString("valor_reserva");
				String formaPago = rst.getString("forma_pago");

				Reserva producto = new Reserva(id,diaEntrada,diaSalida,valorReserva,formaPago);
				reservas.add(producto);
			}
		}
	}
}








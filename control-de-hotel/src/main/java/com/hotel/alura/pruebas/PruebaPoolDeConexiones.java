package com.hotel.alura.pruebas;

import java.sql.Connection;
import java.sql.SQLException;

import com.hotel.alura.factory.ConnectionFactory;

public class PruebaPoolDeConexiones {

	public static void main(String[] args) throws SQLException {
		ConnectionFactory connectionFactory = new ConnectionFactory();
		
		for(int i = 0; i < 20; i++) {
			try (Connection conexion = connectionFactory.recuperaConexion()) {
			}
			
			System.out.println("Abriendo la conexión de número " + (i + 1));
		}
	}
	
}

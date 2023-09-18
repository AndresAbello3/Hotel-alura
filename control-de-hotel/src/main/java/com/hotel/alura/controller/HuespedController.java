package com.hotel.alura.controller;

import java.time.LocalDate;
import java.util.List;

import com.hotel.alura.dao.HuespedDAO;
import com.hotel.alura.modelo.Huesped;

import com.hotel.alura.factory.ConnectionFactory;

public class HuespedController {
	
	private HuespedDAO huespedDAO;
	
	public HuespedController() {
		var factory = new ConnectionFactory();
		this.huespedDAO = new HuespedDAO(factory.recuperaConexion());
	}
	
	
	public void guardar(Huesped huesped) {
		this.huespedDAO.guardar(huesped);
	}
	
	
	public List<Huesped> mostrarHuesped(){
		return this.huespedDAO.mostrar();
	}
	
	
	public List<Huesped> buscarHuesped(String id){
		return this.huespedDAO.buscarId(id);
	}
	
	
	public void actualizar(String nombre, String apellido, LocalDate fechaNacimiento, String nacionalidad, String telefono,
			Integer idReserva, Integer id) {
		this.huespedDAO.actualizar(nombre, apellido, fechaNacimiento, nacionalidad, telefono, idReserva, id);
	}
	
	
	public void eliminar(Integer idReserva) {
		this.huespedDAO.eliminar(idReserva);
	}

}
















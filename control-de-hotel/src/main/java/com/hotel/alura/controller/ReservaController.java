package com.hotel.alura.controller;

import java.time.LocalDate;
import java.util.List;

import com.hotel.alura.dao.ReservaDAO;
import com.hotel.alura.factory.ConnectionFactory;
import com.hotel.alura.modelo.Reserva;


public class ReservaController {
	
	private ReservaDAO reservaDAO;
	
    public ReservaController() {
    	var factory = new ConnectionFactory();
		this.reservaDAO = new ReservaDAO(factory.recuperaConexion());
	}

    public void guardar(Reserva reserva) {
  	  this.reservaDAO.guardar(reserva);
    }
    
    
    public List<Reserva> mostrar(){
  	  return this.reservaDAO.mostrar();
    }
    
    
    public List<Reserva> buscar(String id){
  	  return this.reservaDAO.buscarId(id);
    }
    
    
    public void actualizarReserva(LocalDate diaEntrada, LocalDate diaSalida, String valorReserva, String formaPago, Integer id) {
  	  this.reservaDAO.actualizar(diaEntrada, diaSalida, valorReserva, formaPago, id);
    }
    
    
    public void eliminar(Integer id) {
		this.reservaDAO.eliminar(id);
	}
 

}

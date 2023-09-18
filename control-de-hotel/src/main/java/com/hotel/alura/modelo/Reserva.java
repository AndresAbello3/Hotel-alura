package com.hotel.alura.modelo;

import java.time.LocalDate;

public class Reserva {


	private Integer id;
	
	private LocalDate diaEntrada;
	
	private LocalDate diaSalida;
	
	private String valorReserva;
	
	private String formaPago;
	
	
	public Reserva(LocalDate diaEntrada, LocalDate diaSalida, String valorReserva, String formaPago) {
		super();
		this.diaEntrada = diaEntrada;
		this.diaSalida = diaSalida;
		this.valorReserva = valorReserva;
		this.formaPago = formaPago;
	}
	
	public Reserva(Integer id, LocalDate diaEntrada, LocalDate diaSalida, String valorReserva, String formaPago) {
		super();
		this.id = id;
		this.diaEntrada = diaEntrada;
		this.diaSalida = diaSalida;
		this.valorReserva = valorReserva;
		this.formaPago = formaPago;
	}
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public LocalDate getDataEntrada() {
		return diaEntrada;
	}
	public void setDataEntrada(LocalDate diaEntrada) {
		this.diaEntrada = diaEntrada;
	}
	public LocalDate getDataSalida() {
		return diaSalida;
	}
	public void setFechaSalida(LocalDate diaSalida) {
		this.diaSalida = diaSalida;
	}
	public String getValorReserva() {
		return valorReserva;
	}
	public void setValorReserva(String valorReserva) {
		this.valorReserva = valorReserva;
	}
	public String getFormaPago() {
		return formaPago;
	}
	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}
	
}



















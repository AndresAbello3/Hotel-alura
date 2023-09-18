package com.hotel.alura;

import java.awt.EventQueue;

import com.hotel.alura.view.MenuPrincipal;

public class ControlDeUsuariosMain {

	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuPrincipal frame = new MenuPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}	
}

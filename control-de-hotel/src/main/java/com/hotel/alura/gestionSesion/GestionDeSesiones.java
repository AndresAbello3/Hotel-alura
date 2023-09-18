package com.hotel.alura.gestionSesion;

import com.hotel.alura.view.Login;

public class GestionDeSesiones {

	 private static String usuarioActual;

	    public static void iniciarSesion(String usuario) {
	        usuarioActual = usuario;
	    }

	    public static void cerrarSesion() {
	    	usuarioActual = null;
	        Login login = new Login();
	        login.setVisible(true);
	    }

	    public static boolean estaLogueado() {
	        return usuarioActual != null;
	    }

	    public static String obtenerUsuarioActual() {
	        return usuarioActual;
	    }
	
}

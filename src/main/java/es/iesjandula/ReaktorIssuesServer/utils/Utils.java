package es.iesjandula.ReaktorIssuesServer.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clase que se usará para añadir funciones/utilidades que se usarán en el codigo para poder modularizarlo
 */
public class Utils
{
	//Funcion que devuelve un String con el Dia, Mes, Año, Hora y Minuto en el momento que se ejecuta.
	public static String getAhora()
	{
    	Date today = new Date();
    	SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    	return formatter.format(today);
	}
	
}
package es.iesjandula.ReaktorIssuesServer.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.iesjandula.ReaktorIssuesServer.models.Tic;

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
	
	public static List<Tic> filtrarCorreo (List<Tic> listaTics, String correo)
	{
		List<Tic> ticFiltrada = new ArrayList<Tic>();
		for (Tic tic : listaTics)
		{
			if(tic.getCorreo().equals(correo))
			{
				ticFiltrada.add(tic);
			}
		}
		return ticFiltrada;
	}
	
	public static List<Tic> filtrarAula (List<Tic> listaTics, String aula)
	{
		List<Tic> ticFiltrada = new ArrayList<Tic>();
		for (Tic tic : listaTics)
		{
			if(tic.getAula().equals(aula))
			{
				ticFiltrada.add(tic);
			}
		}
		return ticFiltrada;
	}
	
	public static List<Tic> filtrarMensaje (List<Tic> listaTics, String mensajeDescriptivo)
	{
		List<Tic> ticFiltrada = new ArrayList<Tic>();
		for (Tic tic : listaTics)
		{
			if(tic.getDescripcionIncidencia().equals(mensajeDescriptivo))
			{
				ticFiltrada.add(tic);
			}
		}
		return ticFiltrada;
	}
	
	public static List<Tic> filtrarEstado (List<Tic> listaTics, String estado)
	{
		List<Tic> ticFiltrada = new ArrayList<Tic>();
		for (Tic tic : listaTics)
		{
			if(tic.getEstado().equals(estado.toUpperCase()))
			{
				ticFiltrada.add(tic);
			}
		}
		return ticFiltrada;
	}
	
	public static List<Tic> filtrarSolucion(List<Tic> listaTics, String solucion)
	{
		List<Tic> ticFiltrada = new ArrayList<Tic>();
		for (Tic tic : listaTics)
		{
			if(tic.getSolucion().equals(solucion))
			{
				ticFiltrada.add(tic);
			}
		}
		return ticFiltrada;
	}
	
}
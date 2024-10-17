package es.iesjandula.ReaktorIssuesServer.utils;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.iesjandula.ReaktorIssuesServer.dto.DtoTic;


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
	
	public static List<DtoTic> filtro(List<DtoTic> listaTics, String correo, String aula, String mensaje, String estado, String nombreProfesor, String solucion)
	{
		List<DtoTic> filtrado = new ArrayList<>();
	    estado = estado.toUpperCase();
	    List<DtoTic> filtradoDescendente = new ArrayList<DtoTic>();
	    
	    // Iterar sobre cada elemento en la lista original
	    for (DtoTic tic : listaTics)
	    {
	        boolean coincide = true;

	        // Verificar cada criterio de filtro
	        if (!correo.isBlank() && !tic.getId().getCorreo().equalsIgnoreCase(correo))
	        {
	            coincide = false;
	        }
	        if (!aula.isBlank() && !tic.getId().getAula().equalsIgnoreCase(aula))
	        {
	            coincide = false;
	        }
	        if (!mensaje.isBlank() && !tic.getDescripcionIncidencia().contains(mensaje))
	        {
	            coincide = false;
	        }
	        if (!estado.isBlank() && !tic.getEstado().toString().equals(estado))
	        {
	            coincide = false;
	        }
	        if (solucion != null && !solucion.isBlank() && (tic.getSolucion() == null || !tic.getSolucion().contains(solucion)))
	        {
	            coincide = false;
	        }

	        // Si cumple con todos los filtros especificados, agregarlo a la lista filtrada
	        if (coincide)
	        {
	            filtrado.add(tic);
	        }
	    }
	    
	    for (int i = filtrado.size()-1; i >= 0; i--)
    	{
    		filtradoDescendente.add(filtrado.get(i));
		}
	    
	    return filtradoDescendente;
	}
}
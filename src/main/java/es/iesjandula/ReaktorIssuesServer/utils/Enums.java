package es.iesjandula.ReaktorIssuesServer.utils;

public class Enums
{
	public enum Estado
	{
	    PENDIENTE,
	    EN_CURSO,
	    FINALIZADO,
		CANCELADO;
	}
	public enum Usuarios
	{
		ADMINISTRADOR,
		USUARIO;
	}
	
	// Método para convertir un String a un valor del Enum Estado
	public static Estado stringToEnum(String estadoStr)
	{
	    Estado estado = null;
	    if(!estadoStr.isEmpty())
	    {
	        estado = Estado.valueOf(estadoStr.toUpperCase()); // Se convierte a mayúsculas para que coincida
	    }
	    return estado;
	}
}

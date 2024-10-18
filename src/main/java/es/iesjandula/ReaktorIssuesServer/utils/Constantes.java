package es.iesjandula.ReaktorIssuesServer.utils;

/**
 * Clase que se usará para añadir constantes que se usan frecuentemente en el codigo
 */
public final class Constantes
{
	// Constructor privado para evitar la creación de instancias
    private Constantes(){}
    
    //Constante que aparecerá cuando se quiera obtener los datos de la base de datos y ésta esté vacía
    public static final String DATABASE_EMPTY = "No hay datos en la Base de Datos";
    
    public static final String CANCEL_FAILURE_NOT_EXISTS = "No puedes cancelar un Tic que no existe o está Finalizado";
    
    public static final String UPDATE_FAILURE_NOT_EXISTS_FINALIZATED_CANCELL = "No puedes modificar un Tic que no existe, esta Finalizado o Cancelado";
    
    public static final String UPDATE_TIC_FAILURE = "No se ha podido modificar la lista de Tics";
    
    public static final String UPDATE_FAILURE_NOT_EXISTS = "La incidencia TIC no existe en la base de datos.";
    
    public static final String CORREO_ANDALUCIA = "@g.educaand.es";
    
    public static final String CORREO_JANDULA = "@iesjandula.es";
    
    public static final String TDE = "fbenchi274@g.educaand.es";
}
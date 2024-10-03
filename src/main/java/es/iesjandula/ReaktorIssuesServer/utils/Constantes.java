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
    
    //Constante que aparecerá cuando ocurra un error en el servidor
    public static final Integer SERVER_ERROR = 500;
    
    //Constante que aparecerá cuando no se puede encontrar el recurso en el servidor
    public static final Integer NOT_FOUND = 404;
    
    //Constante que aparecerá cuando la solicitud falla debido a un error del cliente
    public static final Integer BAD_REQUEST = 400;
}
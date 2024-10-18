package es.iesjandula.ReaktorIssuesServer.models;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import es.iesjandula.ReaktorIssuesServer.utils.Constantes;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class IssuesTicId implements Serializable
{
	/**
	 * Generated serialVersion
	 */
	private static final long serialVersionUID = 8692520355413945147L;
	private String correo;
    private String aula;
    private String fechaDeteccion;
	
    public IssuesTicId(String correo, String aula)
    {
		super();
		this.correo = correo;
		this.aula = aula;
		this.fechaDeteccion = getAhora();
	}
    
    public void setCorreo(String correo)
    {
        if (correo == null || correo.trim().isEmpty())
        {
            throw new IllegalArgumentException("El correo no puede estar vacío");
        }
        if(!correo.contains(Constantes.CORREO_ANDALUCIA) && !correo.contains(Constantes.CORREO_JANDULA))
        {
        	throw new IllegalArgumentException("El correo debe pertenecer al sistema educativo de Andalucía o al IES Jándula.");
        }
        this.correo = correo;
    }

    public void setAula(String aula)
    {
        if (aula == null || aula.trim().isEmpty())
        {
            throw new IllegalArgumentException("El aula no puede ser vacía");
        }
        this.aula = aula;
    }
    
  //Funcion que devuelve un String con el Dia, Mes, Año, Hora y Minuto en el momento que se ejecuta.
  	public static String getAhora()
  	{
      	Date today = new Date();
      	SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
      	return formatter.format(today);
  	}
  	
}
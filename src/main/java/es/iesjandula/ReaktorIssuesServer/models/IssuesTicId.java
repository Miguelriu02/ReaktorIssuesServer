package es.iesjandula.ReaktorIssuesServer.models;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    
  //Funcion que devuelve un String con el Dia, Mes, Año, Hora y Minuto en el momento que se ejecuta.
  	public static String getAhora()
  	{
      	Date today = new Date();
      	SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
      	return formatter.format(today);
  	}
}
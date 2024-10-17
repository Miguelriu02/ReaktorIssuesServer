package es.iesjandula.ReaktorIssuesServer.models;

import java.io.Serializable;

import es.iesjandula.ReaktorIssuesServer.utils.Utils;
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
		this.fechaDeteccion = Utils.getAhora();
	}
}
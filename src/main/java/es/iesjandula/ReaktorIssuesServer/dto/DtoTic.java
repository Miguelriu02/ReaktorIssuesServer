package es.iesjandula.ReaktorIssuesServer.dto;

import es.iesjandula.ReaktorIssuesServer.models.IssuesTicId;
import es.iesjandula.ReaktorIssuesServer.utils.Enums.Estado;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO que representa una incidencia TIC registrada en un aula.
 * 
 * Atributos: 
 * id: Identificador de la incidencia.
 * correo: Correo del usuario que genera el Tic
 * numeroAula: Número del aula donde ocurre la incidencia.
 * nombreProfesor: Nombre del profesor que reporta la incidencia.
 * fechaDeteccion: Fecha en la que se detecta la incidencia.
 * descripcionIncidencia: Descripción de la incidencia.
 * estado: Diferentes estados por los que pasa el Tic.
 * finalizadaPor: Puede ser Administrador o Usuario.
 * solucion: Solución dada por la persona que finaliza el Tic
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoTic
{	
	private IssuesTicId id;

	private String descripcionIncidencia;

	private Estado estado;
	
	private String finalizadaPor;
	
	private String solucion;
	
	private String fechaSolucion;

	public DtoTic(IssuesTicId id, String descripcionIncidencia)
	{
		super();
		this.id = id;
		this.descripcionIncidencia = descripcionIncidencia;
	}
	
	public void setDescripcionIncidencia(String descripcionIncidencia)
    {
        if (descripcionIncidencia == null || descripcionIncidencia.trim().isEmpty())
        {
            throw new IllegalArgumentException("La descripcion del tic no debería de estar vacía");
        }
        this.descripcionIncidencia = descripcionIncidencia;
    }
	
	public void setFinalizadaPor(String finalizadaPor)
    {
        if (finalizadaPor == null || finalizadaPor.trim().isEmpty())
        {
            throw new IllegalArgumentException("Quien finaliza el tic no debería de estar vacío");
        }
        this.finalizadaPor = finalizadaPor;
    }
	
	public void setSolucion(String solucion)
    {
        if (solucion == null || solucion.trim().isEmpty())
        {
            throw new IllegalArgumentException("La solucion no debe estar vacía");
        }
        this.solucion = solucion;
    }
}

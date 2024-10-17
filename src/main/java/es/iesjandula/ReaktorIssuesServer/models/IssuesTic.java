package es.iesjandula.ReaktorIssuesServer.models;

import es.iesjandula.ReaktorIssuesServer.utils.Enums.Estado;
import es.iesjandula.ReaktorIssuesServer.utils.Utils;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//Anotación que indica que esta clase es una entidad JPA
@IdClass(IssuesTic.class)
@Entity
//Esta anotacion especifica el nombre de la tabla de la base de datos
@Table(name = "tic")
//Lombok generará automáticamente los métodos getter y setter, contructor con parametros y sin parámetros
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssuesTic
{
	// Anotación que indica que este campo es la clave primaria y se auto-genera
	@EmbeddedId
	private IssuesTicId id;

	@Column
	private String descripcionIncidencia; // Descripción de la Incidencia
	
	@Enumerated(EnumType.STRING)
	@Column
	private Estado estado; // Estado de la incidencia, por defecto no finalizada
	
	@Column
	private String finalizadaPor; // Nombre de la persona que finaliza la Incidencia
	
	@Column
	private String solucion; //Solución dada para el problema
	
	@Column
	private String fechaSolucion; //Fecha en el momento en el que se ha solucionado

	// Constructor que permite crear una instancia de Tic sin el campo 'id'
	public IssuesTic(IssuesTicId id)
	{
		super();
		this.id = id;
		this.estado = Estado.PENDIENTE;
	}
}
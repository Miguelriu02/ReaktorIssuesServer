package es.iesjandula.ReaktorIssuesServer.models;

import es.iesjandula.ReaktorIssuesServer.utils.Enums.Estado;
import es.iesjandula.ReaktorIssuesServer.utils.Utils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//Anotación que indica que esta clase es una entidad JPA
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
	@Id
	@Column // Indica que este campo se mapeará a una columna en la tabla
	private String correo; //Correo del usuario que genera el Tic
	
	@Id
	@Column
	private String aula; // Número del aula o nombre donde se detectó la incidencia
	
	@Id
	@Column
	private String fechaDeteccion; // Fecha en que se detectó la incidencia

	@Column
	private String descripcionIncidencia; // Descripción de la Incidencia

	@Column
	private Estado estado; // Estado de la incidencia, por defecto no finalizada
	
	@Column
	private String finalizadaPor; // Nombre de la persona que finaliza la Incidencia
	
	@Column
	private String solucion; //Solución dada para el problema
	
	@Column
	private String fechaSolucion; //Fecha en el momento en el que se ha solucionado

	// Constructor que permite crear una instancia de Tic sin el campo 'id'
	public IssuesTic(String correo, String numeroAula, String descripcionIncidencia)
	{
		super();
		this.correo = correo;
		this.aula = numeroAula;
		this.descripcionIncidencia = descripcionIncidencia;
		this.fechaDeteccion = Utils.getAhora();
		this.estado = Estado.PENDIENTE;
	}
}
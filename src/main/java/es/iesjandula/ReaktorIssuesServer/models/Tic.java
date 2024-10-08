package es.iesjandula.ReaktorIssuesServer.models;

import es.iesjandula.ReaktorIssuesServer.utils.Utils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
public class Tic
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	// Anotación que indica que este campo es la clave primaria
	@Column
	private String correo; // Identificador único para cada incidencia TIC

	@Column // Indica que este campo se mapeará a una columna en la tabla
	private String numeroAula; // Número del aula o nombre donde se detectó la incidencia

	@Column
	private String nombreProfesor; // Nombre del profesor que reportó la incidencia

	@Column
	private String fechaDeteccion = Utils.getAhora(); // Fecha en que se detectó la incidencia

	@Column
	private String descripcionIncidencia; // Descripción de la Incidencia

	@Column
	private Estado estado = Estado.PENDIENTE; // Estado de la incidencia, por defecto no finalizada
	
	@Column
	private String finalizadaPor; // Nombre de la persona que finaliza la Incidencia
	
	@Column
	private String solucion; //Solución dada para el problema

	// Constructor que permite crear una instancia de Tic sin el campo 'id'
	public Tic(String correo, String numeroAula, String nombreProfesor, String descripcionIncidencia)
	{
		super();
		this.correo = correo;
		this.numeroAula = numeroAula;
		this.nombreProfesor = nombreProfesor;
		this.descripcionIncidencia = descripcionIncidencia;
	}
	
	public enum Estado
	{
	    PENDIENTE,
	    EN_CURSO,
	    FINALIZADO,
		CANCELADA;
	}

}
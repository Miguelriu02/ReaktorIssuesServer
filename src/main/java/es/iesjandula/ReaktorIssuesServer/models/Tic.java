package es.iesjandula.ReaktorIssuesServer.models;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tic")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tic
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column
	private String numeroAula;
	
	@Column
	private String nombreProfesor;
	
	@Column
	private Date fechaDeteccion;
	
	@Column
	private String descripcionIncidencia;
	
	@Column
	private boolean finalizada;
}

package es.iesjandula.ReaktorIssuesServer.models;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
	@Autowired
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

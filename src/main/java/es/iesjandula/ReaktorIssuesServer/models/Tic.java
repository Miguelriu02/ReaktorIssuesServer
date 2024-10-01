package es.iesjandula.ReaktorIssuesServer.models;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tic
{
	private String numeroAula;
	
	private String nombreProfesor;
	
	private Date fechaDeteccion;
	
	private String descripcionIncidencia;
}

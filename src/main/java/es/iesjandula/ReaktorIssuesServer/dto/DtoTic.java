package es.iesjandula.ReaktorIssuesServer.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoTic 
{
	private Integer id;
	
	private String numeroAula;
	
	private String nombreProfesor;
	
	private Date fechaDeteccion;
	
	private String descripcionIncidencia;
	
	private boolean finalizada;
}

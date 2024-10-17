package es.iesjandula.ReaktorIssuesServer.dto;

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
	private String correo;

	private String aula;

	private String fechaDeteccion;

	private String descripcionIncidencia;

	private Estado estado;
	
	private String finalizadaPor;
	
	private String solucion;
	
	private String fechaSolucion;
}

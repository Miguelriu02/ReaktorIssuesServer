package es.iesjandula.ReaktorIssuesServer.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO que representa una incidencia TIC registrada en un aula.
 * 
 * Atributos: 
 * id: Identificador de la incidencia.
 * numeroAula: Número del aula donde ocurre la incidencia.
 * nombreProfesor: Nombre del profesor que reporta la incidencia.
 * fechaDeteccion: Fecha en la que se detecta la incidencia.
 * descripcionIncidencia: Descripción de la incidencia.
 * finalizada: Indica si la incidencia ha sido resuelta.
 */

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
	
	private String finalizadaPor;
}

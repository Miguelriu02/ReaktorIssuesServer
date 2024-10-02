package es.iesjandula.ReaktorIssuesServer.rest;

<<<<<<< Updated upstream
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("incidencias/tic")
@Slf4j
public class RestHandlerIssuesServer
{
	
=======
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.format.annotation.DateTimeFormat;


import es.iesjandula.ReaktorIssuesServer.error.IssuesServerError;
import es.iesjandula.ReaktorIssuesServer.models.Tic;import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Date;

import org.springframework.format.an

import es.iesjandula.ReaktorIssuesServer.error.IssuesServerError;
import es.iesjandula.ReaktorIssuesServer.models.Tic;notation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.iesjandula.ReaktorIssuesServer.error.IssuesServerError;
import es.iesjandula.ReaktorIssuesServer.models.Tic;

@RestController
@RequestMapping("incidencias/tic")
public class RestHandlerIssuesServer
{
	@RequestMapping(method = RequestMethod.POST, value = "/tic")
	public ResponseEntity<?> handleTic(
	        @RequestParam(required = true) String numeroAula,
	        @RequestParam(required = true) String nombreProfesor,
	        @RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaDeteccion,
	        @RequestParam(required = true) String descripcionIncidencia) {
	    try {
	        // Crear un objeto Tic a partir de los parámetros
	        Tic tic = new Tic(numeroAula, nombreProfesor, fechaDeteccion, descripcionIncidencia);

	        // Aquí puedes manejar la información de 'tic' según sea necesario
	        log.info("Tic Info: Aula: {}, Profesor: {}, Fecha: {}, Descripción: {}",
	                 tic.getNumeroAula(), tic.getNombreProfesor(), tic.getFechaDeteccion(), tic.getDescripcionIncidencia());

	        // Aquí podrías guardar el objeto 'tic' en la base de datos o realizar otras acciones necesarias

	        return ResponseEntity.ok().body("Tic recibido correctamente");
	    } catch (Exception exception) {
	        int errorId = 1001; // Ejemplo de ID de error
	        String errorMessage = "Error procesando el Tic";
	        
	        IssuesServerError issuesServerError = new IssuesServerError(errorId, errorMessage, exception);
	        log.error(errorMessage, issuesServerError);

	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(issuesServerError.getMapError());
	    }
	}

>>>>>>> Stashed changes
}

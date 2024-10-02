package es.iesjandula.ReaktorIssuesServer.rest;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.iesjandula.ReaktorIssuesServer.error.IssuesServerError;
import es.iesjandula.ReaktorIssuesServer.models.Tic;
import es.iesjandula.ReaktorIssuesServer.repository.ITicRepository;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("incidencias/tic")
@Slf4j
public class RestHandlerIssuesServer
{
	
	@Autowired
	private ITicRepository ticRepository ;
	
	@RequestMapping(method = RequestMethod.POST, value = "/tic")
	public ResponseEntity<?> postTic(
	        @RequestParam(required = true) String numeroAula,
	        @RequestParam(required = true) String nombreProfesor,
	        @RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaDeteccion,
	        @RequestParam(required = true) String descripcionIncidencia)
	{
	    try
	    {
	        
	        Tic tic = new Tic(numeroAula, nombreProfesor, fechaDeteccion, descripcionIncidencia, false);

	        
	        log.info("Tic Info: Aula: {}, Profesor: {}, Fecha: {}, Descripción: {}",
	                 tic.getNumeroAula(), tic.getNombreProfesor(), tic.getFechaDeteccion(), tic.getDescripcionIncidencia());

	        return ResponseEntity.ok().body("Tic recibido correctamente");
	    }
	    catch (Exception exception)
	    {
	    	String message = "No se ha podido subir la Incidencia Tic";
			log.error(message, exception);
			IssuesServerError serverError= new IssuesServerError(0, message, exception);
			return ResponseEntity.status(500).body(serverError.getMapError());
	    }
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getTics")
	public ResponseEntity<?> obtenerIncidenciasTic()
	{
		try
		{
			return ResponseEntity.ok().body(this.ticRepository.getTics()) ;
		}
		catch (Exception exception)
		{
			String message = "No se ha podido obtener la lista de Tics de la Base de Datos";
			log.error(message, exception);
			IssuesServerError serverError= new IssuesServerError(1, message, exception);
			return ResponseEntity.status(500).body(serverError.getMapError());
		}
	}
}

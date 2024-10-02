package es.iesjandula.ReaktorIssuesServer.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	        @RequestParam(value = "numeroAula", required = true) String numeroAula,
	        @RequestParam(value = "nombreProfesor", required = true) String nombreProfesor,
	        @RequestParam(value = "fechaDeteccion", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaDeteccion,
	        @RequestParam(value = "descripcion", required = true) String descripcionIncidencia)
	{
	    try
	    {
	        
	        Tic tic = new Tic(numeroAula, nombreProfesor, fechaDeteccion, descripcionIncidencia, false);
	        ticRepository.save(tic);
	        
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
			List<Tic> tics = this.ticRepository.findAll();
			return ResponseEntity.ok().body(tics);
		}
		catch (Exception exception)
		{
			String message = "No se ha podido obtener la lista de Tics de la Base de Datos";
			log.error(message, exception);
			IssuesServerError serverError= new IssuesServerError(1, message, exception);
			return ResponseEntity.status(500).body(serverError.getMapError());
		}
	}
	
	@RequestMapping(method = RequestMethod.PUT, value= "/deleteTics")
	public ResponseEntity<?> eliminarIncidenciasTics( @RequestParam(value = "id", required = true) Integer id)
	{
		try
		{
			List<Tic> listaTics = new ArrayList<Tic>();
			listaTics= this.ticRepository.getTics();
			
			for (Tic tic : listaTics) {
				if(tic.getId().equals(id))
				{
					tic.setFinalizada(true);
					ticRepository.save(tic);
				}
			}
		
			return ResponseEntity.ok().body("Tic con ID"+ id+ "ha sido modificada correctamente") ;
		}
		catch (Exception exception)
		{
			String message = "No se ha podido modificar la lista de Tics";
			log.error(message, exception);
			IssuesServerError serverError= new IssuesServerError(1, message, exception);
			return ResponseEntity.status(500).body(serverError.getMapError());
		}
	}
}
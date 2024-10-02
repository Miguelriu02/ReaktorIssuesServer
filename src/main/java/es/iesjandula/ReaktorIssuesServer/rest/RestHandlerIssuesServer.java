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
	
	// Método para recibir nuevas incidencias TIC mediante una solicitud POST
	@RequestMapping(method = RequestMethod.POST, value = "/tic")
	public ResponseEntity<?> postTic(
	        @RequestParam(value = "numeroAula", required = true) String numeroAula,
	        @RequestParam(value = "nombreProfesor", required = true) String nombreProfesor,
	        @RequestParam(value = "fechaDeteccion", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaDeteccion,
	        @RequestParam(value = "descripcion", required = true) String descripcionIncidencia)
	{
	    try
	    {
	    	// Creación de una nueva instancia de Tic con los datos recibidos
	        Tic tic = new Tic(numeroAula, nombreProfesor, fechaDeteccion, descripcionIncidencia, false);
	        
	        // Guarda la nueva incidencia en la base de datos
	        ticRepository.save(tic);
	        
	        // Registro de la información en los logs
	        log.info("Tic Info: Aula: {}, Profesor: {}, Fecha: {}, Descripción: {}",
	                 tic.getNumeroAula(), tic.getNombreProfesor(), tic.getFechaDeteccion(), tic.getDescripcionIncidencia());
	        
	        // Devuelve un mensaje de éxito
	        return ResponseEntity.ok().body("Tic recibido correctamente");
	    }
	    catch (Exception exception)
	    {
			// Manejo de errores: en caso de que ocurra una excepción, se registra el error
	    	String message = "No se ha podido subir la Incidencia Tic";
			log.error(message, exception);
			
			// Se crea un objeto IssuesServerError para enviar detalles del error
			IssuesServerError serverError= new IssuesServerError(0, message, exception);
			return ResponseEntity.status(500).body(serverError.getMapError());
	    }
	}
	
	// Método GET para obtener todas las incidencias TIC guardadas en la base de datos
	@RequestMapping(method = RequestMethod.GET, value = "/getTics")
	public ResponseEntity<?> obtenerIncidenciasTic()
	{
		try
		{
			// Obtiene todas las incidencias TIC de la base de datos
			List<Tic> tics = this.ticRepository.findAll();
			
			// Devuelve con la lista de incidencias obtenidas
			return ResponseEntity.ok().body(tics);
		}
		catch (Exception exception)
		{
			// Manejo de errores: en caso de que ocurra una excepción, se registra el error
			String message = "No se ha podido obtener la lista de Tics de la Base de Datos";
			log.error(message, exception);
			
			// Se crea un objeto IssuesServerError para enviar detalles del error
			IssuesServerError serverError= new IssuesServerError(1, message, exception);
			return ResponseEntity.status(500).body(serverError.getMapError());
		}
	}
	
	// Método PUT para marcar una incidencia TIC como finalizada, con un endpoint llamado "/deleteTics"
	@RequestMapping(method = RequestMethod.PUT, value= "/deleteTics")
	public ResponseEntity<?> eliminarIncidenciasTics( @RequestParam(value = "id", required = true) Integer id)
	{
		try
		{
			// Obtener todas las incidencias TIC desde el repositorio
			List<Tic> listaTics = new ArrayList<Tic>();
			listaTics= this.ticRepository.getTics();
			
			// Recorrer la lista de TICs y buscar la incidencia con el ID proporcionado
			for (Tic tic : listaTics) {
				if(tic.getId().equals(id))
				{
					tic.setFinalizada(true);
					ticRepository.save(tic);
				}
			}
			
			// Responder indicando que la incidencia fue modificada correctamente
			return ResponseEntity.ok().body("Tic con ID"+ id+ "ha sido modificada correctamente") ;
		}
		catch (Exception exception)
		{
			// Manejo de errores: en caso de que ocurra una excepción, se registra el error
			String message = "No se ha podido modificar la lista de Tics";
			log.error(message, exception);
			
			// Se crea un objeto IssuesServerError para enviar detalles del error
			IssuesServerError serverError= new IssuesServerError(1, message, exception);
			return ResponseEntity.status(500).body(serverError.getMapError());
		}
	}
}
package es.iesjandula.ReaktorIssuesServer.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.iesjandula.ReaktorIssuesServer.error.IssuesServerError;
import es.iesjandula.ReaktorIssuesServer.models.Tic;
import es.iesjandula.ReaktorIssuesServer.models.Tic.Estado;
import es.iesjandula.ReaktorIssuesServer.repository.ITicRepository;
import es.iesjandula.ReaktorIssuesServer.utils.Constantes;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("incidencias/tic")
@Slf4j
public class RestHandlerIssuesServer
{
	@Autowired
	private ITicRepository ticRepository;

	// Método para recibir nuevas incidencias TIC mediante una solicitud POST
	@RequestMapping(method = RequestMethod.POST, value = "/createTic")
	public ResponseEntity<?> enviarIncidenciaTic(
			@RequestParam(value = "correo", required = true) String correo,
			@RequestParam(value = "numeroAula", required = true) String numeroAula,
			@RequestParam(value = "nombreProfesor", required = true) String nombreProfesor,
			@RequestParam(value = "descripcion", required = true) String descripcionIncidencia)
	{
		try
		{
			// Creación de una nueva instancia de Tic con los datos recibidos
			Tic tic = new Tic(correo,numeroAula, nombreProfesor, descripcionIncidencia);

			// Guarda la nueva incidencia en la base de datos
			ticRepository.saveAndFlush(tic);

			// Registro de la información en los logs
			log.info("Tic Info: Aula: " + tic.getNumeroAula() + ", Profesor: " + tic.getNombreProfesor() + ", Fecha: "
					+ tic.getFechaDeteccion() + ", Descripción: " + tic.getDescripcionIncidencia());

			// Devuelve un mensaje de éxito
			return ResponseEntity.ok().body("Tic recibido correctamente");
		}
		catch (Exception exception)
		{
			// Manejo de errores: en caso de que ocurra una excepción, se registra el error
			String message = "No se ha podido subir la Incidencia Tic";
			log.error(message, exception);

			// Se crea un objeto IssuesServerError para enviar detalles del error
			IssuesServerError serverError = new IssuesServerError(0, message, exception);
			return ResponseEntity.status(Constantes.SERVER_ERROR).body(serverError.getMapError());
		}
	}

	// Método GET para obtener todas las incidencias TIC guardadas en la base de
	// datos
	@RequestMapping(method = RequestMethod.GET, value = "/getTics")
	public ResponseEntity<?> obtenerIncidenciasTic()
	{
		try
		{
			// Obtiene todas las incidencias TIC de la base de datos
			List<Tic> tics = this.ticRepository.findAll();
			if(tics.isEmpty() || tics == null)
			{
				log.error(Constantes.DATABASE_EMPTY);
				return ResponseEntity.status(Constantes.BAD_REQUEST).body(Constantes.DATABASE_EMPTY);
			}
			// Devuelve con la lista de incidencias obtenidas
			return ResponseEntity.ok().body(tics);
		}
		catch (Exception exception)
		{
			// Manejo de errores: en caso de que ocurra una excepción, se registra el error
			String message = "No se ha podido obtener la lista de Tics de la Base de Datos";
			log.error(message, exception);

			// Se crea un objeto IssuesServerError para enviar detalles del error
			IssuesServerError serverError = new IssuesServerError(1, message, exception);
			return ResponseEntity.status(Constantes.SERVER_ERROR).body(serverError.getMapError());
		}
	}

	// Método PUT para marcar una incidencia TIC como finalizada
	@RequestMapping(method = RequestMethod.PUT, value = "/updateTic")
	public ResponseEntity<?> actualizarTic(
	        @RequestParam(value = "correo", required = true) String correo, 
	        @RequestParam(value = "nombre", required = true) String finalizadaPor,
	        @RequestParam(value = "solucion", required = true) String solucion)
	{
	    
	    String logMessage = "Tic con Correo: " + correo + " ha sido modificada correctamente";
	    try {
	        // Obtener todas las incidencias TIC desde el repositorio
	        List<Tic> listaTics = this.ticRepository.getTics();

	        // Verificar si la lista es nula o está vacía
	        if (listaTics == null || listaTics.isEmpty())
	        {
	            log.error(Constantes.DATABASE_EMPTY);
	            return ResponseEntity.status(Constantes.BAD_REQUEST).body(Constantes.DATABASE_EMPTY);
	        }

	        // Variable para verificar si se encontró el ID
	        boolean ticEncontrada = false;

	        // Recorrer la lista de TICs y buscar la incidencia con el ID proporcionado
	        for (Tic tic : listaTics)
	        {
	            if (tic.getCorreo().equals(correo))
	            {
	                tic.setEstado(Estado.FINALIZADO);
	                tic.setFinalizadaPor(finalizadaPor);
	                tic.setSolucion(solucion);
	                ticRepository.saveAndFlush(tic);
	                ticEncontrada = true;
	                break;  // Salir del bucle una vez que se encuentra
	            }
	        }
	        if (!ticEncontrada)
	        {
	            logMessage = Constantes.UPDATE_FAILURE_NOT_EXISTS;
	            log.error(logMessage);
	            return ResponseEntity.status(Constantes.NOT_FOUND).body(logMessage);
	        }

	        // Responder indicando que la incidencia fue modificada correctamente
	        log.info(logMessage);
	        return ResponseEntity.ok().body(logMessage);
	        
	    }
	    catch (Exception exception)
	    {
	        // Manejo de errores: en caso de que ocurra una excepción, se registra el error
	        String message = Constantes.UPDATE_TIC_FAILURE;
	        log.error(message, exception);

	        // Se crea un objeto IssuesServerError para enviar detalles del error
	        IssuesServerError serverError = new IssuesServerError(1, message, exception);
	        return ResponseEntity.status(Constantes.SERVER_ERROR).body(serverError.getMapError());
	    }
	}
	
	// Método PUT para marcar una incidencia TIC como finalizada
		@RequestMapping(method = RequestMethod.PUT, value = "/cancelTic")
		public ResponseEntity<?> cancelarTic(
		        @RequestParam(value = "id", required = true) Integer id, 
		        @RequestParam(value = "identificador", required = true) String identificadorUA,
		        @RequestParam(value = "motivo", required = true) String motivo)
		{
			String logMessage = "Tic con ID: " + id + " ha sido cancelado correctamente";
		    try {
		        // Obtener todas las incidencias TIC desde el repositorio
		        List<Tic> listaTics = this.ticRepository.getTics();

		        // Verificar si la lista es nula o está vacía
		        if (listaTics == null || listaTics.isEmpty())
		        {
		            log.error(Constantes.DATABASE_EMPTY);
		            return ResponseEntity.status(Constantes.BAD_REQUEST).body(Constantes.DATABASE_EMPTY);
		        }

		        // Variable para verificar si se encontró el ID
		        boolean ticEncontrada = false;

		        // Recorrer la lista de TICs y buscar la incidencia con el ID proporcionado
		        for (Tic tic : listaTics)
		        {
		            if (tic.getId().equals(id))
		            {
		                tic.setEstado(Estado.CANCELADA);
		                tic.setFinalizadaPor(identificadorUA);
		                tic.setSolucion(motivo);
		                ticRepository.saveAndFlush(tic);
		                ticEncontrada = true;
		                break;  // Salir del bucle una vez que se encuentra
		            }
		        }
		        if (!ticEncontrada)
		        {
		            logMessage = Constantes.UPDATE_FAILURE_NOT_EXISTS;
		            log.error(logMessage);
		            return ResponseEntity.status(Constantes.NOT_FOUND).body(logMessage);
		        }

		        // Responder indicando que la incidencia fue modificada correctamente
		        log.info(logMessage);
		        return ResponseEntity.ok().body(logMessage);
		        
		    }
		    catch (Exception exception)
		    {
		        // Manejo de errores: en caso de que ocurra una excepción, se registra el error
		        String message = Constantes.UPDATE_TIC_FAILURE;
		        log.error(message, exception);

		        // Se crea un objeto IssuesServerError para enviar detalles del error
		        IssuesServerError serverError = new IssuesServerError(1, message, exception);
		        return ResponseEntity.status(Constantes.SERVER_ERROR).body(serverError.getMapError());
		    }
		}
}
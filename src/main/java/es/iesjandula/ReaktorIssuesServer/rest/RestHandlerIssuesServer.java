package es.iesjandula.ReaktorIssuesServer.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.iesjandula.ReaktorIssuesServer.error.IssuesServerError;
import es.iesjandula.ReaktorIssuesServer.repository.ITicRepository;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("incidencias/tic")
@Slf4j
public class RestHandlerIssuesServer
{
	
	@Autowired
	private ITicRepository ticRepository ;
	
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
			IssuesServerError serverError= new IssuesServerError(0, message, exception);
			return ResponseEntity.status(500).body(serverError.getMapError());
		}
	}
}

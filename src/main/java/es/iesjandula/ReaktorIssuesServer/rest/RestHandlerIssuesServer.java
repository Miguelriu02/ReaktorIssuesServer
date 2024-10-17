package es.iesjandula.ReaktorIssuesServer.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.iesjandula.ReaktorIssuesServer.dto.DtoTic;
import es.iesjandula.ReaktorIssuesServer.repository.ITicRepository;
import es.iesjandula.ReaktorIssuesServer.utils.Constantes;
import es.iesjandula.ReaktorIssuesServer.utils.Enums.Estado;
import es.iesjandula.ReaktorIssuesServer.utils.IssuesServerError;
import es.iesjandula.ReaktorIssuesServer.utils.Utils;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/incidencias")
@Slf4j
public class RestHandlerIssuesServer {

    @Autowired
    private ITicRepository iTicRepository;

    // Método para recibir nuevas incidencias TIC mediante una solicitud POST usando DtoTic y @RequestBody
    @RequestMapping(method = RequestMethod.POST, value = "/")
    public ResponseEntity<?> enviarIncidenciaTic(@RequestBody DtoTic dtoTic)
    {
        try
        {
            // Guardar la nueva incidencia en la base de datos
            this.iTicRepository.saveAndFlush(dtoTic);

            // Registro de la información en los logs
            log.info("Tic Info: " + dtoTic);

            return ResponseEntity.ok().build();
        }
        catch (Exception exception)
        {
            String message = "No se ha podido subir la Incidencia Tic";
            log.error(message, exception);
            IssuesServerError serverError = new IssuesServerError(0, message, exception);
            return ResponseEntity.status(500).body(serverError.getMapError());
        }
    }
    
    // Método para filtrar las Incidencias Tic
    @RequestMapping(method = RequestMethod.GET, value = "/filtrarTic")
    public ResponseEntity<?> filtrarTic(
            @RequestParam(value = "usuario", required = true) String usuario,
            @RequestParam(value = "correo", required = false, defaultValue = "") String correo,
            @RequestParam(value = "aula", required = false, defaultValue = "") String aula,
            @RequestParam(value = "mensajeDescriptivo", required = false, defaultValue = "") String mensaje,
            @RequestParam(value = "estado", required = false, defaultValue = "") String estado,
            @RequestParam(value = "nombreProfesor", required = false, defaultValue = "") String nombreProfesor,
            @RequestParam(value = "solucion", required = false, defaultValue = "") String solucion) 
    {
        try
        {
            // Obtiene todas las incidencias TIC de la base de datos
            List<DtoTic> tics = this.iTicRepository.findAll();
            List<DtoTic> ticsFiltradas;

            if (tics.isEmpty() || tics == null)
            {
                log.error(Constantes.DATABASE_EMPTY);
                return ResponseEntity.status(400).body(Constantes.DATABASE_EMPTY);
            }
            if (usuario.toLowerCase().equals("admin") || usuario.toLowerCase().equals("administrador") || usuario.toLowerCase().equals("tde"))
            {
                ticsFiltradas = Utils.filtro(tics, correo, aula, mensaje, estado, nombreProfesor, solucion);
            }
            else
            {
                ticsFiltradas = this.iTicRepository.filtrarCorreo(correo);
            }
            return ResponseEntity.ok().body(ticsFiltradas);
        }
        catch (Exception exception)
        {
            // Manejo de errores
            String message = "No se ha podido obtener la lista de Tics de la Base de Datos";
            log.error(message, exception);
            IssuesServerError serverError = new IssuesServerError(1, message, exception);
            return ResponseEntity.status(500).body(serverError.getMapError());
        }
    }

    // Método PUT para actualizar una incidencia TIC usando DtoTic y @RequestBody
    @RequestMapping(method = RequestMethod.PUT, value = "/")
    public ResponseEntity<?> actualizarTic(@RequestBody DtoTic dtoTic, @RequestParam boolean cancelar)
    {
    	String logMessage = "Tic con Correo: " + dtoTic.getCorreo() + " ha sido modificada correctamente";
        
        try
        {
        	DtoTic dtoTicPut = this.iTicRepository.findById(dtoTic.getCorreo(), dtoTic.getAula(), dtoTic.getFechaDeteccion());
        	
        	if (dtoTicPut.getEstado().equals(Estado.FINALIZADO))
        	{
                log.error(Constantes.UPDATE_FAILURE_NOT_EXISTS_FINALIZATED_CANCELL);
                return ResponseEntity.status(404).body(Constantes.UPDATE_FAILURE_NOT_EXISTS_FINALIZATED_CANCELL);
            }

            // Actualizar el estado y otros campos según corresponda
            if (dtoTicPut.getEstado().equals(Estado.PENDIENTE))
            {
                dtoTicPut.setEstado(Estado.EN_CURSO);
            }
            else if (dtoTicPut.getEstado().equals(Estado.EN_CURSO))
            {
                dtoTicPut.setEstado(Estado.FINALIZADO);
                dtoTicPut.setFinalizadaPor(dtoTic.getFinalizadaPor());
                dtoTicPut.setSolucion(dtoTic.getSolucion());
                dtoTicPut.setFechaSolucion(Utils.getAhora());
            }
            else if(cancelar && dtoTicPut.getEstado().equals(Estado.PENDIENTE) || dtoTicPut.getEstado().equals(Estado.EN_CURSO))
            {
            	dtoTicPut.setEstado(Estado.CANCELADA);
                dtoTicPut.setFinalizadaPor(dtoTic.getCorreo());
                dtoTicPut.setSolucion(dtoTic.getSolucion());
                dtoTicPut.setFechaSolucion(Utils.getAhora());
            }

            this.iTicRepository.saveAndFlush(dtoTicPut);

            log.info(logMessage);
            return ResponseEntity.ok().body(logMessage);
        }
        catch (Exception exception)
        {
            // Manejo de errores
            String message = Constantes.UPDATE_TIC_FAILURE;
            log.error(message, exception);
            IssuesServerError serverError = new IssuesServerError(1, message, exception);
            return ResponseEntity.status(500).body(serverError.getMapError());
        }
    }
}
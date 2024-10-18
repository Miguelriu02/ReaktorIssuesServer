package es.iesjandula.ReaktorIssuesServer.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.iesjandula.ReaktorIssuesServer.dto.DtoTic;
import es.iesjandula.ReaktorIssuesServer.models.IssuesTic;
import es.iesjandula.ReaktorIssuesServer.models.IssuesTicId;
import es.iesjandula.ReaktorIssuesServer.repository.ITicRepository;
import es.iesjandula.ReaktorIssuesServer.utils.Constantes;
import es.iesjandula.ReaktorIssuesServer.utils.Enums;
import es.iesjandula.ReaktorIssuesServer.utils.Enums.Estado;
import es.iesjandula.ReaktorIssuesServer.utils.IssuesServerError;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/incidencias")
@Slf4j
public class RestHandlerIssuesServer {

    @Autowired
    private ITicRepository iTicRepository;

    // Método para recibir nuevas incidencias TIC mediante una solicitud POST usando DtoTic y @RequestBody
    @RequestMapping(method = RequestMethod.POST, value = "/", consumes = "application/json")
    public ResponseEntity<?> enviarIncidenciaTic(@RequestBody DtoTic dtoTic)
    {
        try
        {
        	IssuesTicId ticId = new IssuesTicId(dtoTic.getId().getCorreo(), dtoTic.getId().getAula());
        	IssuesTic tic = new IssuesTic(ticId, dtoTic.getDescripcionIncidencia());
        	
            // Guardar la nueva incidencia en la base de datos
            this.iTicRepository.saveAndFlush(tic);

            // Registro de la información en los logs
            log.info("Tic Info: " + dtoTic);

            return ResponseEntity.ok().build();
        }
        catch (Exception exception)
        {
            String message = "No se ha podido subir la Incidencia Tic";
            log.error(message, exception);
            IssuesServerError serverError = new IssuesServerError(0, message, exception);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(serverError.getMapError());
        }
    }
    // Método para filtrar las Incidencias Tic
    @RequestMapping(method = RequestMethod.GET, value = "/filtrarTic")
    public ResponseEntity<?> filtrarTic(
            @RequestParam(value = "usuario", required = false, defaultValue = "") String usuario,
            @RequestParam(value = "correo", required = false) String correo,
            @RequestParam(value = "aula", required = false) String aula,
            @RequestParam(value = "fechaDeteccion", required = false) String fechaDeteccion,
            @RequestParam(value = "mensajeDescriptivo", required = false) String mensaje,
            @RequestParam(value = "estado", required = false, defaultValue = "") String estadoStr,
            @RequestParam(value = "finalizadaPor", required = false) String finalizadaPor,
            @RequestParam(value = "solucion", required = false) String solucion,
            @RequestParam(value = "fechaSolucion", required = false) String fechaSolucion) 
    {
        try
        {
            // Obtiene todas las incidencias TIC de la base de datos
            List<DtoTic> tics = this.iTicRepository.getTics();
            List<DtoTic> ticsFiltradas;
            
            if (tics.isEmpty() || tics == null)
            {
                log.error(Constantes.DATABASE_EMPTY);
                return ResponseEntity.status(400).body(Constantes.DATABASE_EMPTY);
            }
            if (usuario.toLowerCase().equals("admin") || usuario.toLowerCase().equals("administrador") || usuario.toLowerCase().equals("tde"))
            {
            	Estado estado = Enums.stringToEnum(estadoStr);
                ticsFiltradas = this.iTicRepository.filtrar(correo, aula, fechaDeteccion,mensaje, estado, finalizadaPor, solucion, fechaSolucion);
            }
            else
            {
                ticsFiltradas = this.iTicRepository.findByCorreo(correo);
            }
            return ResponseEntity.ok().body(ticsFiltradas);
        }
        catch (Exception exception)
        {
            // Manejo de errores
            String message = "No se ha podido obtener la lista de Tics de la Base de Datos";
            log.error(message, exception);
            IssuesServerError serverError = new IssuesServerError(1, message, exception);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(serverError.getMapError());
        }
    }
    // Método PUT para actualizar una incidencia TIC usando DtoTic y @RequestBody
    @RequestMapping(method = RequestMethod.PUT, value = "/", consumes = "application/json")
    public ResponseEntity<?> actualizarTic(
    		@RequestBody DtoTic dtoTic, 
    		@RequestParam(value = "admin", required = false) boolean admin,
    		@RequestParam(value = "cancelar", required = false) boolean cancelar)
    {
        String logMessage = "TIC con Correo: " + dtoTic.getId().getCorreo() + " ha sido modificada correctamente";
        
        try
        {
            // Buscar la incidencia TIC en la base de datos
            IssuesTic tic = this.iTicRepository.findByPrimary(dtoTic.getId().getCorreo(), dtoTic.getId().getAula(), dtoTic.getId().getFechaDeteccion());

            // Verificación si la TIC existe
            if (tic == null)
            {
                log.error(Constantes.UPDATE_FAILURE_NOT_EXISTS);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Constantes.UPDATE_FAILURE_NOT_EXISTS);
            }
            
            // Verificar el estado actual de la TIC
            if (tic.getEstado().equals(Estado.FINALIZADO) || tic.getEstado().equals(Estado.CANCELADO))
            {
                log.error(Constantes.UPDATE_FAILURE_NOT_EXISTS_FINALIZATED_CANCELL);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Constantes.UPDATE_FAILURE_NOT_EXISTS_FINALIZATED_CANCELL);
            }

            // Lógica para actualizar o cancelar el estado
            if (cancelar && tic.getId().getCorreo().equals(dtoTic.getFinalizadaPor()))
            {
                // Solo se puede cancelar si el estado es PENDIENTE o EN_CURSO
                if (tic.getEstado().equals(Estado.PENDIENTE) || tic.getEstado().equals(Estado.EN_CURSO))
                {
                    tic.setEstado(Estado.CANCELADO);
                    tic.setFinalizadaPor(dtoTic.getFinalizadaPor());
                    tic.setSolucion(dtoTic.getSolucion());
                    tic.setFechaSolucion(IssuesTicId.getAhora());
                    logMessage = "TIC con Correo: " + dtoTic.getId().getCorreo() + " ha sido cancelada correctamente";
                }
            }
            else if(admin)
            {
                // Si cancelar es false, actualizar el estado si es PENDIENTE o EN_CURSO
                if (tic.getEstado().equals(Estado.PENDIENTE))
                {
                    tic.setEstado(Estado.EN_CURSO);
                }
                else if (tic.getEstado().equals(Estado.EN_CURSO))
                {
                    tic.setEstado(Estado.FINALIZADO);
                    tic.setFinalizadaPor(dtoTic.getFinalizadaPor());
                    tic.setSolucion(dtoTic.getSolucion());
                    tic.setFechaSolucion(IssuesTicId.getAhora());
                    logMessage = "TIC con Correo: " + dtoTic.getId().getCorreo() + " ha sido finalizado correctamente";
                }
            }
            else
            {
            	logMessage = "No puedes modificar el estado de la Incidencia sin ser el administrador o la misma persona que la creó.";
            }

            // Guardar la TIC actualizada en la base de datos
            this.iTicRepository.save(tic);
            log.info(logMessage);
            return ResponseEntity.ok().body(logMessage);

        }
        catch (Exception exception)
        {
            // Manejo de errores
            String message = Constantes.UPDATE_TIC_FAILURE;
            log.error(message, exception);
            IssuesServerError serverError = new IssuesServerError(1, message, exception);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(serverError.getMapError());
        }
    }
}
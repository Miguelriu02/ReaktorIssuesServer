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
import es.iesjandula.ReaktorIssuesServer.error.IssuesServerError;
import es.iesjandula.ReaktorIssuesServer.models.Tic;
import es.iesjandula.ReaktorIssuesServer.models.Tic.Estado;
import es.iesjandula.ReaktorIssuesServer.repository.ITicRepository;
import es.iesjandula.ReaktorIssuesServer.utils.Constantes;
import es.iesjandula.ReaktorIssuesServer.utils.Utils;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/incidencias")
@Slf4j
public class RestHandlerIssuesServer {

    @Autowired
    private ITicRepository ticRepository;

    // Método para recibir nuevas incidencias TIC mediante una solicitud POST usando DtoTic y @RequestBody
    @RequestMapping(method = RequestMethod.POST, value = "/")
    public ResponseEntity<?> enviarIncidenciaTic(@RequestBody DtoTic dtoTic) {
        try {
            // Crear una instancia de Tic usando los datos del DtoTic
            Tic tic = new Tic(
                dtoTic.getCorreo(),
                dtoTic.getAula(),
                dtoTic.getNombreProfesor(),
                dtoTic.getDescripcionIncidencia()
            );

            tic.setEstado(dtoTic.getEstado() != null ? dtoTic.getEstado() : Tic.Estado.PENDIENTE);

            // Guardar la nueva incidencia en la base de datos
            ticRepository.saveAndFlush(tic);

            // Registro de la información en los logs
            log.info("Tic Info: Aula: " + tic.getAula() + ", Profesor: " + tic.getNombreProfesor() + ", Fecha: "
                    + tic.getFechaDeteccion() + ", Descripción: " + tic.getDescripcionIncidencia());

            return ResponseEntity.ok().body("Tic recibido correctamente");
        } catch (Exception exception) {
            String message = "No se ha podido subir la Incidencia Tic";
            log.error(message, exception);
            IssuesServerError serverError = new IssuesServerError(0, message, exception);
            return ResponseEntity.status(Constantes.SERVER_ERROR).body(serverError.getMapError());
        }
    }

    // Método GET para obtener todas las incidencias TIC guardadas en la base de datos
    @RequestMapping(method = RequestMethod.GET, value = "/")
    public ResponseEntity<?> obtenerIncidenciasTic() {
        try {
            // Obtiene todas las incidencias TIC de la base de datos
            List<DtoTic> tics = this.ticRepository.findAll();
            if (tics.isEmpty() || tics == null) {
                log.error(Constantes.DATABASE_EMPTY);
                return ResponseEntity.status(Constantes.BAD_REQUEST).body(Constantes.DATABASE_EMPTY);
            }
            // Devuelve la lista de incidencias obtenidas
            return ResponseEntity.ok().body(tics);
        } catch (Exception exception) {
            // Manejo de errores
            String message = "No se ha podido obtener la lista de Tics de la Base de Datos";
            log.error(message, exception);
            IssuesServerError serverError = new IssuesServerError(1, message, exception);
            return ResponseEntity.status(Constantes.SERVER_ERROR).body(serverError.getMapError());
        }
    }

    // Método PUT para actualizar una incidencia TIC usando DtoTic y @RequestBody
    @RequestMapping(method = RequestMethod.PUT, value = "/")
    public ResponseEntity<?> actualizarTic(@RequestBody DtoTic dtoTic) {
        String logMessage = "Tic con Id: " + dtoTic.getId() + " ha sido modificada correctamente";
        try {
            Tic tic = this.ticRepository.findById(dtoTic.getId()).orElse(null);

            if (tic == null || tic.getEstado().equals(Estado.FINALIZADO)) {
                log.error(Constantes.UPDATE_FAILURE_NOT_EXISTS_FINALIZATED_CANCELL);
                return ResponseEntity.status(Constantes.NOT_FOUND).body(Constantes.UPDATE_FAILURE_NOT_EXISTS_FINALIZATED_CANCELL);
            }

            // Actualizar el estado y otros campos según corresponda
            if (tic.getEstado().equals(Estado.PENDIENTE)) {
                tic.setEstado(Estado.EN_CURSO);
            } else if (tic.getEstado().equals(Estado.EN_CURSO)) {
                tic.setEstado(Estado.FINALIZADO);
                tic.setFinalizadaPor(dtoTic.getFinalizadaPor());
                tic.setSolucion(dtoTic.getSolucion());
                tic.setFechaSolucion(Utils.getAhora());
            }

            ticRepository.saveAndFlush(tic);

            log.info(logMessage);
            return ResponseEntity.ok().body(logMessage);
        } catch (Exception exception) {
            // Manejo de errores
            String message = Constantes.UPDATE_TIC_FAILURE;
            log.error(message, exception);
            IssuesServerError serverError = new IssuesServerError(1, message, exception);
            return ResponseEntity.status(Constantes.SERVER_ERROR).body(serverError.getMapError());
        }
    }

    // Método DELETE para cancelar una incidencia TIC usando DtoTic y @RequestBody
    @RequestMapping(method = RequestMethod.DELETE, value = "/")
    public ResponseEntity<?> cancelarTic(@RequestBody DtoTic dtoTic) {
        String logMessage = "Tic con ID: " + dtoTic.getId() + " ha sido cancelado correctamente";
        try {
            DtoTic tic = this.ticRepository.findById(dtoTic.getId()).orElse(null);

            if (tic == null) {
                log.error(Constantes.CANCEL_FAILURE_NOT_EXISTS);
                return ResponseEntity.status(Constantes.NOT_FOUND).body(Constantes.CANCEL_FAILURE_NOT_EXISTS);
            }

            if (tic.getEstado().equals(Estado.FINALIZADO)) {
                logMessage = "No puedes cancelar un Tic finalizado";
                log.error(logMessage);
                return ResponseEntity.status(Constantes.BAD_REQUEST).body(logMessage);
            }

            // Cambiar el estado a CANCELADA
            tic.setEstado(Estado.CANCELADA);
            tic.setFinalizadaPor(dtoTic.getFinalizadaPor());
            tic.setSolucion(dtoTic.getSolucion());  // Motivo de la cancelación
            tic.setFechaSolucion(Utils.getAhora());

            ticRepository.saveAndFlush(tic);

            log.info(logMessage);
            return ResponseEntity.ok().body(logMessage);
        } catch (Exception exception) {
            // Manejo de errores
            String message = Constantes.UPDATE_TIC_FAILURE;
            log.error(message, exception);
            IssuesServerError serverError = new IssuesServerError(1, message, exception);
            return ResponseEntity.status(Constantes.SERVER_ERROR).body(serverError.getMapError());
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
            @RequestParam(value = "solucion", required = false, defaultValue = "") String solucion) {
        try {
            // Obtiene todas las incidencias TIC de la base de datos
            List<DtoTic> tics = this.ticRepository.findAll();
            List<DtoTic> ticsFiltradas;

            if (tics.isEmpty() || tics == null) {
                log.error(Constantes.DATABASE_EMPTY);
                return ResponseEntity.status(Constantes.BAD_REQUEST).body(Constantes.DATABASE_EMPTY);
            }

            if (usuario.toLowerCase().equals("admin") || usuario.toLowerCase().equals("administrador") || usuario.toLowerCase().equals("tde")) {
                ticsFiltradas = Utils.filtro(tics, correo, aula, mensaje, estado, nombreProfesor, solucion);
            } else {
                ticsFiltradas = Utils.filtrarCorreo(tics, correo);
            }

            return ResponseEntity.ok().body(ticsFiltradas);
        } catch (Exception exception) {
            // Manejo de errores
            String message = "No se ha podido obtener la lista de Tics de la Base de Datos";
            log.error(message, exception);
            IssuesServerError serverError = new IssuesServerError(1, message, exception);
            return ResponseEntity.status(Constantes.SERVER_ERROR).body(serverError.getMapError());
        }
    }
}

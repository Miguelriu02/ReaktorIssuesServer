package es.iesjandula.ReaktorIssuesServer.repository;

// Importar las clases necesarias para el uso de listas y Spring Data JPA
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

// Importamos el DTO Tic 
import es.iesjandula.ReaktorIssuesServer.dto.DtoTic;
import es.iesjandula.ReaktorIssuesServer.models.IssuesTic;
import es.iesjandula.ReaktorIssuesServer.utils.Enums;


// Definir la interfaz ITicRepository, que extiende JpaRepository para operaciones Crear, Modificar, Leer y Eliminar sobre la entidad Tic
public interface ITicRepository extends JpaRepository<IssuesTic, String> {

    // Método para obtener todos los tics como DtoTic
    @Query("SELECT new es.iesjandula.ReaktorIssuesServer.dto.DtoTic(t.id, t.descripcionIncidencia, t.estado, t.finalizadaPor, t.solucion, t.fechaSolucion) "
            + "FROM IssuesTic t")
    List<DtoTic> getTics();

    // Método para buscar por clave primaria (correo, aula, fechaDeteccion)
    @Query("SELECT t FROM IssuesTic t WHERE "
            + "t.id.correo = :correo AND "
            + "t.id.aula = :aula AND "
            + "t.id.fechaDeteccion = :fechaDeteccion")    
    IssuesTic findByPrimary(
            @Param("correo") String correo,
            @Param("aula") String aula,
            @Param("fechaDeteccion") String fechaDeteccion);

    // Método para buscar registros por correo
    @Query("SELECT new es.iesjandula.ReaktorIssuesServer.dto.DtoTic(t.id, t.descripcionIncidencia, t.estado, t.finalizadaPor, t.solucion, t.fechaSolucion) "
            + "FROM IssuesTic t WHERE t.id.correo = :correo")
    List<DtoTic> findByCorreo(@Param("correo") String correo);
    
    // Método de filtrado dinámico
    @Query("SELECT new es.iesjandula.ReaktorIssuesServer.dto.DtoTic(t.id, t.descripcionIncidencia, t.estado, t.finalizadaPor, t.solucion, t.fechaSolucion) "
            + "FROM IssuesTic t WHERE "
            + "(:correo IS NULL OR t.id.correo = :correo) AND "
            + "(:aula IS NULL OR t.id.aula = :aula) AND "
            + "(:fechaDeteccion IS NULL OR t.id.fechaDeteccion LIKE %:fechaDeteccion%) AND "
            + "(:descripcion IS NULL OR t.descripcionIncidencia LIKE %:descripcion%) AND "
            + "(:estado IS NULL OR t.estado = :estado) AND "
            + "(:finalizador IS NULL OR t.finalizadaPor = :finalizador) AND "
            + "(:solucion IS NULL OR t.solucion LIKE %:solucion%) AND "
            + "(:fechaSolucion IS NULL OR t.fechaSolucion LIKE %:fechaSolucion%) AND "
            + "(:fechaInicio IS NULL OR :fechaFin IS NULL OR t.id.fechaDeteccion BETWEEN :fechaInicio AND :fechaFin)")
    List<DtoTic> filtrar(
        @Param("correo") String correo,
        @Param("aula") String aula,
        @Param("fechaDeteccion") String fechaDeteccion,
        @Param("descripcion") String descripcion,
        @Param("estado") Enums.Estado estado,
        @Param("finalizador") String finalizador,
        @Param("solucion") String solucion,
        @Param("fechaSolucion") String fechaSolucion,
        @Param("fechaInicio") String fechaInicio,
        @Param("fechaFin") String fechaFin);

}
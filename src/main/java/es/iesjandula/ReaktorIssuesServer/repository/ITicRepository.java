package es.iesjandula.ReaktorIssuesServer.repository;

// Importar las clases necesarias para el uso de listas y Spring Data JPA
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

// Importamos el DTO Tic 
import es.iesjandula.ReaktorIssuesServer.dto.DtoTic;
import es.iesjandula.ReaktorIssuesServer.models.IssuesTic;


// Definir la interfaz ITicRepository, que extiende JpaRepository para operaciones Crear, Modificar, Leer y Eliminar sobre la entidad Tic
public interface ITicRepository extends JpaRepository<IssuesTic, String> {

    @Query("SELECT new es.iesjandula.ReaktorIssuesServer.dto.DtoTic(t.id, t.descripcionIncidencia, t.estado, t.finalizadaPor, t.solucion, t.fechaSolucion) FROM IssuesTic t")
    List<DtoTic> getTics();

    @Query("SELECT t FROM IssuesTic t WHERE t.id.correo = :correo AND t.id.aula = :aula AND t.id.fechaDeteccion = :fechaDeteccion")
    IssuesTic findByPrimary(
            @Param("correo") String correo,
            @Param("aula") String aula,
            @Param("fechaDeteccion") String fechaDeteccion);

    @Query("SELECT t FROM IssuesTic t WHERE t.id.correo = :correo")
    List<DtoTic> findByCorreo(@Param("correo") String correo);
}
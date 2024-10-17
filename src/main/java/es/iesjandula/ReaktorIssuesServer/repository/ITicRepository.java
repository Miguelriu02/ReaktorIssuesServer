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
public interface ITicRepository extends JpaRepository<IssuesTic, String>
{
	// Definimos una consulta personalizada para recuperar una lista de objetos Tic
	@Query("SELECT new es.iesjandula.ReaktorIssuesServer.dto.DtoTic(t) FROM DtoTic t")
	List<DtoTic> getTics(); // Método que devuelve una lista de Tics seleccionados mediante la consulta
	
	@Query("SELECT new es.iesjandula.ReaktorIssuesServer.dto.DtoTic(t) FROM DtoTic t Where t.correo = :correo and t.aula = :aula and t.fechaDeteccion = :fechaDeteccion")
	IssuesTic findByPrimary(
			@Param("correo") String correo,
			@Param("aula") String aula,
			@Param("fechaDeteccion") String fechaDeteccion);
	
	@Query("SELECT new es.iesjandula.ReaktorIssuesServer.dto.DtoTic(t) FROM DtoTic t Where t.correo = :correo")
	List<DtoTic> filtrarCorreo(@Param("correo") String correo);
}
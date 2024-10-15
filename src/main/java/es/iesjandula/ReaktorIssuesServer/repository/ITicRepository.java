package es.iesjandula.ReaktorIssuesServer.repository;

// Importar las clases necesarias para el uso de listas y Spring Data JPA
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

// Importar el modelo Tic
import es.iesjandula.ReaktorIssuesServer.models.Tic;

// Definir la interfaz ITicRepository, que extiende JpaRepository para operaciones Crear, Modificar, Leer y Eliminar sobre la entidad Tic
public interface ITicRepository extends JpaRepository<Tic, String>
{
	// Definimos una consulta personalizada para recuperar una lista de objetos Tic
	@Query("SELECT new es.iesjandula.ReaktorIssuesServer.models.Tic(t.id, t.correo, t.aula, t.nombreProfesor, t.fechaDeteccion, t.descripcionIncidencia, t.estado, t.finalizadaPor, t.solucion, t.fechaSolucion) FROM Tic t")
	List<Tic> getTics(); // Método que devuelve una lista de Tics seleccionados mediante la consulta
}
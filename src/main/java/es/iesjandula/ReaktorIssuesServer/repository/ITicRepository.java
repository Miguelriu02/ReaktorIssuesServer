package es.iesjandula.ReaktorIssuesServer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import es.iesjandula.ReaktorIssuesServer.models.Tic;

public interface ITicRepository extends JpaRepository<Tic, String>
{
	@Query("SELECT new es.iesjandula.ReaktorIssuesServer.models.Tic(t.id, t.numeroAula, t.nombreProfesor, t.fechaDeteccion, t.descripcionIncidencia, t.finalizada) FROM Tic t")
	List<Tic> getTics();
}

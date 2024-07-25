package br.com.petshoptchutchucao.agenda.model.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import br.com.petshoptchutchucao.agenda.model.entities.schedule.Task;

public interface TaskRepository extends MongoRepository<Task, String>{

	@Query(value = "{'name': {$regex: ?0}}", delete = true)
	void deleteAllByName(String name);

	Task findByName(String name);

}

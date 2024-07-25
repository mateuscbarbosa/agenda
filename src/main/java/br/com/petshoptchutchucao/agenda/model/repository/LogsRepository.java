package br.com.petshoptchutchucao.agenda.model.repository;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import br.com.petshoptchutchucao.agenda.model.entities.logs.Logs;

public interface LogsRepository extends MongoRepository<Logs, String> {

	@Query(value="{'dateTime': {'$lt' : ?0 }}", exists = true)
	boolean existsByDateTime(LocalDateTime thirtyDaysBefore);

	@Query(value="{'dateTime': {'$lt' : ?0 }}", count = true)
	Integer countByDateTime(LocalDateTime dateTime);
	
	@Query(value = "{'dateTime': {'$lt' : ?0 }}", delete = true)
	void cleanMonthLogs(LocalDateTime dateTime);

}

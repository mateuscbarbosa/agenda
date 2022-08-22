package br.com.petshoptchutchucao.agenda.repository;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import br.com.petshoptchutchucao.agenda.model.Logs;

public interface LogsRepository extends MongoRepository<Logs, String> {

	@Query(value="{'dateTime': {'$lt' : ?0 }}", exists = true)
	boolean existsByDateTime(LocalDateTime thirtyDaysBefore);

	@Query(value="{'dateTime': {'$lt' : ?0 }}", count = true)
	Integer countByDateTime(LocalDateTime dateTime);
	
	@Query(value = "{'dateTime': {'$lt' : ?0 }}", delete = true)
	//({birth: {$lt: new Date(ISODate("2010-08-16T00:00:00.000Z").getTime()-1000*86400*2)}})
	void cleanMonthLogs(LocalDateTime dateTime);

}

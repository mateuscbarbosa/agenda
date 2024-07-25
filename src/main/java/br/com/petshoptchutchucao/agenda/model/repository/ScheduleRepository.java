package br.com.petshoptchutchucao.agenda.model.repository;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import br.com.petshoptchutchucao.agenda.model.entities.schedule.Schedule;

public interface ScheduleRepository extends MongoRepository<Schedule, String>{

	@Query(value = "{'date': ?0, 'time': ?1}", exists = true)
	Boolean existsByTime(LocalDate date, LocalTime time);

	@Query(value="{'observation': {$regex: ?0}}", delete=true)
	void deleteAllByObservation(String observation);

}

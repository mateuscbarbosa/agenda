package br.com.petshoptchutchucao.agenda.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.petshoptchutchucao.agenda.model.Schedule;

public interface ScheduleRepository extends MongoRepository<Schedule, String>{

}

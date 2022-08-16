package br.com.petshoptchutchucao.agenda.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.petshoptchutchucao.agenda.model.Logs;

public interface LogsRepository extends MongoRepository<Logs, String> {

}

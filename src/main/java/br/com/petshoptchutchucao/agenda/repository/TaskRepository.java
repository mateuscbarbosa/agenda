package br.com.petshoptchutchucao.agenda.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.petshoptchutchucao.agenda.model.Task;

public interface TaskRepository extends MongoRepository<Task, String>{

}

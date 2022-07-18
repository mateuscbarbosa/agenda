package br.com.petshoptchutchucao.agenda.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import br.com.petshoptchutchucao.agenda.model.Pet;

public interface PetRepository extends MongoRepository<Pet, String>{

	@Query(value = "{'name': {$regex: ?0}}", delete = true)
	void deleteAllByName(String name);

}

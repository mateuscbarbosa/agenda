package br.com.petshoptchutchucao.agenda.model.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import br.com.petshoptchutchucao.agenda.model.entities.customer.Pet;

public interface PetRepository extends MongoRepository<Pet, String>{

	@Query(value = "{'name': {$regex: ?0}}", delete = true)
	void deleteAllByName(String name);

	Pet findByName(String name);

	@Query("{'customerId': ?0}")
	List<Pet> findAllbyCustomerId(String customerId);

}

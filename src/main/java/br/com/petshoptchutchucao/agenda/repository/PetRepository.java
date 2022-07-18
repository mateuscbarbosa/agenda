package br.com.petshoptchutchucao.agenda.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.petshoptchutchucao.agenda.model.Pet;

public interface PetRepository extends MongoRepository<Pet, String>{

}

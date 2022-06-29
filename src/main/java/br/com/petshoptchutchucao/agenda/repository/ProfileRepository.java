package br.com.petshoptchutchucao.agenda.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.petshoptchutchucao.agenda.model.Profile;

public interface ProfileRepository extends MongoRepository<Profile, String>{

}

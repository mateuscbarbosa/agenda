package br.com.petshoptchutchucao.agenda.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import br.com.petshoptchutchucao.agenda.model.Profile;

public interface ProfileRepository extends MongoRepository<Profile, String>{

	@Query("{'_id': ?0}")
	Optional<Profile> getById(Integer id);
}

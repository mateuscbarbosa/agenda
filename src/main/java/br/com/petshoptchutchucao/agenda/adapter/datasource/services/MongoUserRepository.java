package br.com.petshoptchutchucao.agenda.adapter.datasource.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import br.com.petshoptchutchucao.agenda.model.entities.user.User;

public interface MongoUserRepository extends MongoRepository<User, String>{

	@Query("{'status': 'ATIVO'}")
	Page<User> findAll(Pageable pageable);
	
	@Query("{'email': ?0}")
	Optional<User> findByEmail(String email);

	@Query(value = "{'email': {$regex: ?0}}", delete = true)
	void deleteAllByEmail(String email);

	boolean existsByEmail(String email);

}

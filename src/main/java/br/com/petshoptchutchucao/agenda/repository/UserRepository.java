package br.com.petshoptchutchucao.agenda.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import br.com.petshoptchutchucao.agenda.model.User;

public interface UserRepository extends MongoRepository<User, String>{

	@Query("{'status': 'ATIVO'}")
	Page<User> findAll(Pageable pageable);
	
	@Query("{'email': ?0}")
	Optional<User> findByEmail(String email);

	@Query(value = "{'email': {$regex: ?0}}", delete = true)
	void deleteAllByEmail(String email);

	boolean existsByEmail(String email);

}

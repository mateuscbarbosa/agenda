package br.com.petshoptchutchucao.agenda.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import br.com.petshoptchutchucao.agenda.model.entities.customer.Customer;

public interface CustomerRepository extends MongoRepository<Customer, String>{

	@Query("{'status': 'ATIVO'}")
	Page<Customer> findAllActive(Pageable pagination);

	@Query(value = "{'name': {$regex: ?0}}", delete = true)
	void deleteAllByName(String string);

	Customer findByName(String name);

}

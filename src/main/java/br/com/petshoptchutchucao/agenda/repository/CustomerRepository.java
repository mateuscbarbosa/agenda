package br.com.petshoptchutchucao.agenda.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import br.com.petshoptchutchucao.agenda.model.Customer;

public interface CustomerRepository extends MongoRepository<Customer, String>{

	@Query("{'status': 'ATIVO'}")
	Page<Customer> findAllActive(Pageable pagination);

}

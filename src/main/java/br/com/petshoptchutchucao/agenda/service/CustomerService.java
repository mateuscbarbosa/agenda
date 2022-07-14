package br.com.petshoptchutchucao.agenda.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.petshoptchutchucao.agenda.dto.CustomerOutputDto;
import br.com.petshoptchutchucao.agenda.model.Customer;
import br.com.petshoptchutchucao.agenda.repository.CustomerRepository;

@Service
public class CustomerService {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private CustomerRepository customerRepository;

	public Page<CustomerOutputDto> list(Pageable pagination) {
		Page<Customer> customers = customerRepository.findAllActive(pagination);
		return customers.map(c -> modelMapper.map(c, CustomerOutputDto.class));
	}
	
	
}

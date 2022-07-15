package br.com.petshoptchutchucao.agenda.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.petshoptchutchucao.agenda.dto.CustomerFormDto;
import br.com.petshoptchutchucao.agenda.dto.CustomerOutputDto;
import br.com.petshoptchutchucao.agenda.dto.CustomerUpdateFormDto;
import br.com.petshoptchutchucao.agenda.infra.BusinessRulesException;
import br.com.petshoptchutchucao.agenda.model.Customer;
import br.com.petshoptchutchucao.agenda.model.Status;
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

	@Transactional
	public CustomerOutputDto register(CustomerFormDto customerForm) {
		Customer customer = modelMapper.map(customerForm, Customer.class);
		
		customer.setStatus(Status.ATIVO);
		
		customerRepository.save(customer);
		
		return modelMapper.map(customer, CustomerOutputDto.class);
	}

	@Transactional
	public CustomerOutputDto update(CustomerUpdateFormDto customerForm) {
		Customer customer = customerRepository.findById(customerForm.getId()).orElseThrow(() -> new BusinessRulesException("ID do Cliente não encontrado"));
		
		customer.updateInfo(customerForm.getId(),
							customerForm.getName(),
							customerForm.getAddress(),
							customerForm.getPets(),
							customerForm.getContactNumbers(),
							customerForm.getStatus());
		
		customerRepository.save(customer);
		
		return modelMapper.map(customer, CustomerOutputDto.class);
	}
	
	
}

package br.com.petshoptchutchucao.agenda.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.petshoptchutchucao.agenda.dto.PetFormDto;
import br.com.petshoptchutchucao.agenda.dto.PetOutputDto;
import br.com.petshoptchutchucao.agenda.model.Pet;
import br.com.petshoptchutchucao.agenda.repository.PetRepository;

@Service
public class PetService {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PetRepository petRepository;
	
	public Page<PetOutputDto> list(Pageable pagination) {
		Page<Pet> pets = petRepository.findAll(pagination);
		return pets.map(p -> modelMapper.map(p, PetOutputDto.class));
	}

	@Transactional
	public PetOutputDto register(PetFormDto petForm) {
		Pet pet = modelMapper.map(petForm, Pet.class);
		
		petRepository.save(pet);
		
		return modelMapper.map(pet, PetOutputDto.class);
	}

}

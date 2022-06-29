package br.com.petshoptchutchucao.agenda.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mongodb.MongoException;

import br.com.petshoptchutchucao.agenda.dto.UserFormDto;
import br.com.petshoptchutchucao.agenda.dto.UserOutputDto;
import br.com.petshoptchutchucao.agenda.infra.PasswordGeneratorPassay;
import br.com.petshoptchutchucao.agenda.model.Profile;
import br.com.petshoptchutchucao.agenda.model.User;
import br.com.petshoptchutchucao.agenda.repository.ProfileRepository;
import br.com.petshoptchutchucao.agenda.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProfileRepository profileRepository;
	
	public Page<UserOutputDto> list(Pageable pagination){
		
		Page<User> users = userRepository.findAll(pagination);
		return users.map(u -> modelMapper.map(u, UserOutputDto.class));
	}

	@Transactional
	public UserOutputDto register(UserFormDto userForm) {
		
		User user = modelMapper.map(userForm, User.class);
		user.setPassword(new PasswordGeneratorPassay().generatePassword());
		
		List<Profile> profiles = new ArrayList<>();
		
		for (int i = 0;  i < userForm.getProfiles().length; i++) {
			Profile profile = profileRepository.getById(userForm.getProfiles()[i])
												.orElseThrow(() -> new MongoException("Perfil não encontrado"));
												
			profiles.add(profile);
		}
		user.setProfiles(profiles);
		
		userRepository.save(user);
				
		return modelMapper.map(user, UserOutputDto.class);
	}
	
}

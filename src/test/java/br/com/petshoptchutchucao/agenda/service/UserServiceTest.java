package br.com.petshoptchutchucao.agenda.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.mongodb.MongoException;

import br.com.petshoptchutchucao.agenda.dto.UserFormDto;
import br.com.petshoptchutchucao.agenda.dto.UserOutputDto;
import br.com.petshoptchutchucao.agenda.model.Profile;
import br.com.petshoptchutchucao.agenda.model.User;
import br.com.petshoptchutchucao.agenda.repository.ProfileRepository;
import br.com.petshoptchutchucao.agenda.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	private Integer profilesVetor[] = new Integer[1];
	private List<Profile> profilesList = new ArrayList<>();

	@Mock
	private ModelMapper modelMapper;

	@Mock
	private ProfileRepository profileRepository;
	
	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserService service;

	@Test
	void couldNotRegisterAUserWithInsuficientData() {
		profilesVetor[0]=0;
		
		UserFormDto userForm = new UserFormDto("","",profilesVetor);

		User user = new User(userForm.getEmail(), userForm.getName(), profilesList);

		Mockito.when(modelMapper.map(userForm, User.class)).thenReturn(user);

		Mockito.when(profileRepository.getById(profilesVetor[0])).thenThrow(MongoException.class);
		
		assertThrows(MongoException.class, () -> service.register(userForm));

	}
	
	@Test
	void couldRegisterAUserWithCompleteData() {
		profilesVetor[0]=0;
		Profile profile = new Profile();
		UserFormDto userForm = new UserFormDto("teste@teste.com.br", "Teste", profilesVetor);
		
		Mockito.when(profileRepository.getById(profilesVetor[0])).thenReturn(Optional.of(profile));
		
		User user = new User(userForm.getEmail(),userForm.getName(),profilesList);
		
		Mockito.when(modelMapper.map(userForm, User.class)).thenReturn(user);
		
		Mockito.when(modelMapper.map(user, UserOutputDto.class)).thenReturn(new UserOutputDto(null, user.getEmail(), user.getName()));
		
		UserOutputDto userOutput = service.register(userForm);
		
		Mockito.verify(userRepository).save(Mockito.any());
		
		assertEquals(userForm.getEmail(), userOutput.getEmail());
		assertEquals(userForm.getName(), userOutput.getName());
		
	}
}

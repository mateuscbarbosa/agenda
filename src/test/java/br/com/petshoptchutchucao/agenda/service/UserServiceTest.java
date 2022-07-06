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
import br.com.petshoptchutchucao.agenda.dto.UserUpdateFormDto;
import br.com.petshoptchutchucao.agenda.model.Profile;
import br.com.petshoptchutchucao.agenda.model.Status;
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
	void couldNotRegisterAnUserWithInsuficientData() {
		profilesVetor[0] = 0;

		UserFormDto userForm = new UserFormDto("", "", profilesVetor);

		User user = new User(userForm.getEmail(), userForm.getName(), profilesList, Status.ATIVO);

		Mockito.when(modelMapper.map(userForm, User.class)).thenReturn(user);

		Mockito.when(profileRepository.getById(profilesVetor[0])).thenThrow(MongoException.class);

		assertThrows(MongoException.class, () -> service.register(userForm));
	}

	@Test
	void couldRegisterAnUserWithCompleteData() {
		profilesVetor[0] = 0;
		Profile profile = new Profile();
		UserFormDto userForm = new UserFormDto("teste@teste.com.br", "Teste", profilesVetor);

		Mockito.when(profileRepository.getById(profilesVetor[0])).thenReturn(Optional.of(profile));

		User user = new User(userForm.getEmail(), userForm.getName(), profilesList, Status.ATIVO);

		Mockito.when(modelMapper.map(userForm, User.class)).thenReturn(user);

		Mockito.when(modelMapper.map(user, UserOutputDto.class))
				.thenReturn(new UserOutputDto(null, user.getEmail(), user.getName()));

		UserOutputDto userOutput = service.register(userForm);

		Mockito.verify(userRepository).save(Mockito.any());

		assertEquals(userForm.getEmail(), userOutput.getEmail());
		assertEquals(userForm.getName(), userOutput.getName());

	}

	@Test
	void couldNotUpdateAnInexisteUserId() {
		profilesVetor[0] = 10;
		UserUpdateFormDto userUpdate = new UserUpdateFormDto("123456",
															"teste@email.com.br",
															"Teste",
															profilesVetor,
															Status.ATIVO,
															"$3nh@1");

		Mockito.when(userRepository.findById(userUpdate.getId())).thenThrow(MongoException.class);

		assertThrows(MongoException.class, () -> service.update(userUpdate));
	}

	@Test
	void couldUpdateAnExistentUserId() {
		profilesVetor[0] = 10;
		
		Profile profile = new Profile();

		UserUpdateFormDto userUpdate = new UserUpdateFormDto("123456",
															"teste@email.com.br",
															"Teste",
															profilesVetor,
															Status.ATIVO,
															"$3nh@1");
		
		Mockito.when(profileRepository.getById(profilesVetor[0])).thenReturn(Optional.of(profile));
		
		User user = modelMapper.map(userUpdate, User.class);

		Mockito.when(modelMapper.map(userUpdate, User.class)).thenReturn(user);

		Mockito.when(userRepository.findById(userUpdate.getId()).get()).thenReturn(user);
		
		Mockito.when(modelMapper.map(user, UserOutputDto.class)).thenReturn(new UserOutputDto(null, user.getEmail(), user.getName()));

		UserOutputDto userOutput = service.update(userUpdate);

		Mockito.verify(userRepository).save(Mockito.any());

		assertEquals(userUpdate.getEmail(), userOutput.getEmail());
		assertEquals(userUpdate.getName(), userOutput.getName());
	}

}

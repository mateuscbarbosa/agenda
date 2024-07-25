package br.com.petshoptchutchucao.agenda.model.repository;

import br.com.petshoptchutchucao.agenda.model.entities.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findById(String id);

    Page<User> findAll(Pageable pageable);

    Optional<User> findByEmail(String email);

    void deleteAllByEmail(String email);

    boolean existsByEmail(String email);
}

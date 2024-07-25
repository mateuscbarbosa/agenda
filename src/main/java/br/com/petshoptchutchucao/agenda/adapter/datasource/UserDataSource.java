package br.com.petshoptchutchucao.agenda.adapter.datasource;

import br.com.petshoptchutchucao.agenda.adapter.datasource.services.MongoUserRepository;
import br.com.petshoptchutchucao.agenda.model.entities.user.User;
import br.com.petshoptchutchucao.agenda.model.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public class UserDataSource implements UserRepository {

    private final MongoUserRepository mongoUserRepository;

    public UserDataSource(MongoUserRepository mongoUserRepository) {
        this.mongoUserRepository = mongoUserRepository;
    }

    @Override
    public User save(User user) {
        return this.mongoUserRepository.save(user);
    }

    @Override
    public Optional<User> findById(String id) {
        return this.mongoUserRepository.findById(id);
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return this.mongoUserRepository.findAll(pageable);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return this.mongoUserRepository.findByEmail(email);
    }

    @Override
    public void deleteAllByEmail(String email) {
        this.mongoUserRepository.deleteAllByEmail(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return this.mongoUserRepository.existsByEmail(email);
    }
}

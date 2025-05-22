package Moon.adapters.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Moon.adapters.persons.entity.PersonEntity;
import Moon.adapters.users.entity.UserEntity;
import Moon.domain.models.Person;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByEmail(String email);
    UserEntity findByEmail(String email); 
    UserEntity findByPerson(PersonEntity person);
    
}
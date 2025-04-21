package Moon.adapters.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import Moon.adapters.users.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByEmail(String email);
    UserEntity findByEmail(String email); 
    UserEntity findByDocument(long document); 
    
}
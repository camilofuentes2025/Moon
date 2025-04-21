package Moon.ports;

import Moon.domain.models.User;

public interface UserPort {
	
	boolean existEmail(String email); 
    void saveUser(User user);
    User findByPersonDocument(long document);
    User findByEmail(String email);
    
}


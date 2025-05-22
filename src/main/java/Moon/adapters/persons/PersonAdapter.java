package Moon.adapters.persons;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import Moon.adapters.persons.entity.PersonEntity;
import Moon.adapters.persons.repository.PersonRepository;
import Moon.domain.models.Person;
import Moon.ports.PersonPort;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Service
public class PersonAdapter implements PersonPort {
	@Autowired
	private PersonRepository personRepository;
	@Override
	public boolean existPerson(long document) {
		return personRepository.existsByDocument(document);
	}

	@Override
	public void savePerson(Person person) {
	    System.out.println("Verificando si el documento existe en BD: " + person.getDocument()); // 🔍 Depuración
	    if (existPerson(person.getDocument())) {
	        throw new RuntimeException("El documento " + person.getDocument() + " ya está registrado.");
	    }

	    PersonEntity personEntity = personAdapter(person);
	    personRepository.save(personEntity);
	    person.setDocument(personEntity.getDocument());
	}



	@Override
	public Person findByDocument(long document) {
		PersonEntity personEntity = personRepository.findByDocument(document);
		return personAdapter(personEntity);
	}

	private Person personAdapter(PersonEntity personEntity) {
		if (personEntity == null) {
            return null;
		}
		Person person= new Person();
		person.setDocument(personEntity.getDocument());
		person.setName(personEntity.getName());
		person.setPhone(personEntity.getPhone());
		person.setAge(personEntity.getAge());
		return person;
		
	}
	
	private PersonEntity personAdapter(Person person) {
		if (person == null) {
            return null;
		}
		PersonEntity personEntity = new PersonEntity();
		personEntity.setDocument(person.getDocument());
		personEntity.setName(person.getName());
		personEntity.setPhone(person.getPhone());
		personEntity.setAge(person.getAge());
		return personEntity;
	}
}
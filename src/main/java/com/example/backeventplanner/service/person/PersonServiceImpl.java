package com.example.backeventplanner.service.person;

import com.example.backeventplanner.persistence.person.Person;
import com.example.backeventplanner.persistence.person.PersonRepo;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepo personRepo;

    public PersonServiceImpl(PersonRepo personRepo) {
        this.personRepo = personRepo;
    }

    @Override
    public Boolean checkedUserName(String userName) {
        Person byUserName = personRepo.findByUserName(userName);
        boolean check = byUserName == null;
        return check;
    }
}

package de.phoenix.wgtest.services;

import de.phoenix.wgtest.model.management.*;
import de.phoenix.wgtest.repository.management.AsdRepository;
import de.phoenix.wgtest.repository.management.PersonRepository;
import de.phoenix.wgtest.repository.management.RoleRepository;
import one.util.streamex.StreamEx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    AsdRepository asdRepository;

    @Autowired
    RoleRepository roleRepository;

    public Person getPersonById(Long id) {
        return personRepository.findById(id).orElse(null);
    }

    public Asd getAsdById(Long id) {
        return asdRepository.findById(id).orElse(null);
    }

    public List<Person> getAllGuardians() {
        Role role = roleRepository.findByTypeAndSpecification(ERole.GUARDIAN, null).orElse(null);
        if (role == null) {
            return List.of();
        }
        return StreamEx.of(role.getPersonRoles())
                .map(PersonRole::getPerson)
                .distinct()
                .toList();
    }

    public List<Asd> getAllAsds() {
        return asdRepository.findAll();
    }

    public List<Person> getAllChilddoctors() {
        Role role = roleRepository.findByTypeAndSpecification(ERole.CHILDDOCTOR, null).orElse(null);
        if (role == null) {
            return List.of();
        }
        return StreamEx.of(role.getPersonRoles())
                .map(PersonRole::getPerson)
                .distinct()
                .toList();
    }
}

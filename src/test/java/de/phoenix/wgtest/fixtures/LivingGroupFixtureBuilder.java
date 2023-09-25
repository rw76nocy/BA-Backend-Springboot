package de.phoenix.wgtest.fixtures;

import de.phoenix.wgtest.model.management.AppointmentType;
import de.phoenix.wgtest.model.management.Child;
import de.phoenix.wgtest.model.management.LivingGroup;
import de.phoenix.wgtest.model.management.Person;
import lombok.Setter;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Setter
public class LivingGroupFixtureBuilder extends EntityFixtureBuilder<LivingGroup> {

    private String name;
    private List<Child> children;
    private List<Person> employees;
    private AppointmentType defaultType;
    private List<AppointmentType> appointmentTypes;

    public LivingGroupFixtureBuilder(TestEntityManager testEntityManager, FixturesFactory fixturesFactory) {
        super(testEntityManager, fixturesFactory);
    }

    private String getName() {
        return Optional.ofNullable(name).orElse(UUID.randomUUID().toString());
    }

    private List<Child> getChildren() {
        return Optional.ofNullable(children).orElse(new ArrayList<>());
    }

    private List<Person> getEmployees() {
        return Optional.ofNullable(employees).orElse(new ArrayList<>());
    }

    private AppointmentType getDefaultType() {
        return Optional.ofNullable(defaultType).orElseGet(() -> getFixturesFactory()
                .newAppointmentTypeFixtureBuilder()
                .buildAndPersist()
        );
    }

    private List<AppointmentType> getAppointmentTypes() {
        return Optional.ofNullable(appointmentTypes).orElseGet(() -> List.of(getDefaultType()));
    }

    @Override
    public LivingGroup build() {
        LivingGroup lg = new LivingGroup();
        lg.setName(getName());
        lg.setChildren(getChildren());
        lg.setEmployees(getEmployees());
        lg.setDefaultType(getDefaultType());
        lg.setAppointmentTypes(getAppointmentTypes());
        return lg;
    }
}

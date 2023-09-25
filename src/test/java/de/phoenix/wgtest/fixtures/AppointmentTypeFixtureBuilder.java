package de.phoenix.wgtest.fixtures;

import de.phoenix.wgtest.model.management.AppointmentType;
import lombok.Setter;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

@Setter
public class AppointmentTypeFixtureBuilder extends EntityFixtureBuilder<AppointmentType> {

    private String name;
    private String color;

    public AppointmentTypeFixtureBuilder(TestEntityManager testEntityManager, FixturesFactory fixturesFactory) {
        super(testEntityManager, fixturesFactory);
    }

    private String getName() {
        return Optional.ofNullable(name).orElse("Sonstiges");
    }

    private String getColor() {
        return Optional.ofNullable(color).orElse("#09afff");
    }

    @Override
    public AppointmentType build() {
        AppointmentType at = new AppointmentType();
        at.setName(getName());
        at.setColor(getColor());
        return at;
    }
}

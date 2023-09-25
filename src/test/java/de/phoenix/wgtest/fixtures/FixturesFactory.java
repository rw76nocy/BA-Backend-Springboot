package de.phoenix.wgtest.fixtures;

import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.transaction.support.TransactionTemplate;

public class FixturesFactory {

    private final TestEntityManager testEntityManager;

    final TransactionTemplate transactionTemplate;

    public FixturesFactory() {
        this(null);
    }

    public FixturesFactory(TestEntityManager testEntityManager) {
        this(testEntityManager, null);
    }

    public FixturesFactory(TestEntityManager testEntityManager, TransactionTemplate transactionTemplate) {
        this.testEntityManager = testEntityManager;
        this.transactionTemplate = transactionTemplate;
    }

    public LivingGroupFixtureBuilder newLivingGroupFixtureBuilder() {
        return new LivingGroupFixtureBuilder(testEntityManager, this);
    }

    public AppointmentTypeFixtureBuilder newAppointmentTypeFixtureBuilder() {
        return new AppointmentTypeFixtureBuilder(testEntityManager, this);
    }
}

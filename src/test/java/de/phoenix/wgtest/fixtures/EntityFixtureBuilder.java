package de.phoenix.wgtest.fixtures;

import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public abstract class EntityFixtureBuilder<T> {

    @Getter(AccessLevel.PROTECTED)
    private final FixturesFactory fixturesFactory;

    @Getter(AccessLevel.PROTECTED)
    private final TestEntityManager testEntityManager;

    public EntityFixtureBuilder(TestEntityManager testEntityManager, FixturesFactory fixturesFactory) {
        this.testEntityManager = testEntityManager;
        this.fixturesFactory = fixturesFactory;
    }

    public T buildAndPersist() {
        if (this.testEntityManager != null) {
            if (TransactionSynchronizationManager.isActualTransactionActive()) {
                return buildAndPersistInternal();
            } else {
                return fixturesFactory.transactionTemplate.execute(status -> buildAndPersistInternal());
            }
        } else {
            T instance = build();
            updateAfterPersisted(instance);
            return instance;
        }
    }

    private T buildAndPersistInternal() {
        T instance = build();
        instance = this.testEntityManager.persist(instance);
        updateAfterPersisted(instance);
        return this.testEntityManager.persistAndFlush(instance);
    }

    protected void updateAfterPersisted(T persistedInstance) {
        //to be implemented by subclasses if needed
    }

    public abstract T build();
}

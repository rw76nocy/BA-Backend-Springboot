package de.phoenix.wgtest.repository;

import static org.assertj.core.api.Assertions.assertThat;

import de.phoenix.wgtest.fixtures.FixturesFactory;
import de.phoenix.wgtest.model.management.LivingGroup;
import de.phoenix.wgtest.repository.management.LivingGroupRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

public class LivingGroupRepositoryIT extends AbstractRepositoryIT {

    @Autowired
    private LivingGroupRepository livingGroupRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private TransactionTemplate transactionTemplate;

    private FixturesFactory fixturesFactory;

    @Before
    public void init() {
        fixturesFactory = new FixturesFactory(testEntityManager, transactionTemplate);
    }

    @Test
    public void test() {
        List<LivingGroup> list = livingGroupRepository.findAll();
        assertThat(list).hasSize(0);
    }

    @Test
    public void test2() {
        LivingGroup lg1 = fixturesFactory.newLivingGroupFixtureBuilder().buildAndPersist();
        LivingGroup lg2 = fixturesFactory.newLivingGroupFixtureBuilder().buildAndPersist();
        LivingGroup lg3 = fixturesFactory.newLivingGroupFixtureBuilder().buildAndPersist();

        assertThat(livingGroupRepository.findAll()).hasSize(3);
    }
}

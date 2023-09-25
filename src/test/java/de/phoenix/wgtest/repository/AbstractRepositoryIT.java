package de.phoenix.wgtest.repository;

import de.phoenix.wgtest.TestConfiguration;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = NONE)
@DataJpaTest
@Testcontainers
@Import({TestConfiguration.class, BCryptPasswordEncoder.class})
@ActiveProfiles("test")
public abstract class AbstractRepositoryIT {

}

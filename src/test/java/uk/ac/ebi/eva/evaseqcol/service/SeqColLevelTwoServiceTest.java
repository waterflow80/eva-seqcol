package uk.ac.ebi.eva.evaseqcol.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import uk.ac.ebi.eva.evaseqcol.entities.SeqColLevelTwoEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("seqcol")
@Testcontainers
class SeqColLevelTwoServiceTest {

    private String LEVEL_0_DIGEST = "Y2ujWD8fTeC86uKbL22N2jyMYrcX0cN0";

    @Autowired
    private SeqColLevelTwoService levelTwoService;

    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15.2");

    @DynamicPropertySource
    static void dataSourceProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "update");
    }

    @Test
    @Disabled
    void getSeqColLevelTwoByDigest() {
        // The object to fetch should already be in the database
        Optional<SeqColLevelTwoEntity> levelTwoEntity = levelTwoService.getSeqColLevelTwoByDigest(LEVEL_0_DIGEST);
        assertTrue(levelTwoEntity.isPresent());
        assertTrue(levelTwoEntity.get().getLengths().size() > 0);
    }
}
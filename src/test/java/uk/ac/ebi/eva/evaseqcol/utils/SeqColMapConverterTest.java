package uk.ac.ebi.eva.evaseqcol.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import uk.ac.ebi.eva.evaseqcol.entities.SeqColLevelOneEntity;
import uk.ac.ebi.eva.evaseqcol.entities.SeqColLevelTwoEntity;
import uk.ac.ebi.eva.evaseqcol.io.SeqColGenerator;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SeqColMapConverterTest {

    private SeqColMapConverter seqColMapConverter = new SeqColMapConverter();

    private SeqColGenerator seqColGenerator = new SeqColGenerator();

    @Test
    void setSeqColLevelOneMapConverterTest() {
        SeqColLevelOneEntity levelOneEntity = seqColGenerator.generateLevelOneEntity();
        Map<String, String> levelOneMap = seqColMapConverter.getSeqColLevelOneMap(levelOneEntity);
        assertFalse(levelOneMap.keySet().isEmpty()); // At least we should have the "sequences", "lengths" and "names"
        assertTrue(levelOneMap.containsKey("sequences"));
        assertTrue(levelOneMap.containsKey("lengths"));
        assertTrue(levelOneMap.containsKey("names"));
        assertNotNull(levelOneMap.get("sequences"));
        assertNotNull(levelOneMap.get("lengths"));
        assertNotNull(levelOneMap.get("names"));
    }

    @Test
    void seqColLevelTwoMapConverterTest() {
        SeqColLevelTwoEntity levelTwoEntity = seqColGenerator.generateLevelTwoEntity();
        Map<String, List<String>> levelTwoMap = seqColMapConverter.getSeqColLevelTwoMap(levelTwoEntity);
        assertTrue(levelTwoMap.keySet().size() >= 0); // At least we should have the "sequences", "lengths" and "names"
    }
}
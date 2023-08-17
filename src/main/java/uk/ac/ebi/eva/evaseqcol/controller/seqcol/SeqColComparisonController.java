package uk.ac.ebi.eva.evaseqcol.controller.seqcol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uk.ac.ebi.eva.evaseqcol.entities.SeqColComparisonResultEntity;
import uk.ac.ebi.eva.evaseqcol.exception.SeqColNotFoundException;
import uk.ac.ebi.eva.evaseqcol.service.SeqColService;

import java.util.List;
import java.util.TreeMap;

@RestController
@RequestMapping("/comparison")
public class SeqColComparisonController {

    private SeqColService seqColService;

    @Autowired
    public SeqColComparisonController(SeqColService seqColService) {
        this.seqColService = seqColService;
    }

    @GetMapping("/{digest1}/{digest2}")
    public ResponseEntity<?> compareSequenceCollections(
            @PathVariable String digest1, @PathVariable String digest2) {
        try {
            SeqColComparisonResultEntity comparisonResult = seqColService.compareSeqCols(digest1, digest2);
            return new ResponseEntity<>(comparisonResult, HttpStatus.OK);
        } catch (NoSuchFieldException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (SeqColNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{digest1}")
    public ResponseEntity<?> compareSequenceCollections(
            @PathVariable String digest1, @RequestBody TreeMap<String, List<String>> seqColLevelTwo
    ) {
        try {
            SeqColComparisonResultEntity comparisonResult = seqColService.compareSeqCols(digest1, seqColLevelTwo);
            return new ResponseEntity<>(comparisonResult, HttpStatus.OK);
        } catch (SeqColNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

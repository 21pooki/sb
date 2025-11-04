package com.example.hr.controller;

import com.example.hr.model.Candidate;
import com.example.hr.service.CandidateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/hr/candidates")
public class CandidateController {

    private final CandidateService candidateService;

    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @GetMapping // GET http://localhost:8081/api/hr/candidates
    public List<Candidate> getAllCandidates() {
        return candidateService.getAllCandidates();
    }

    @GetMapping("/{id}") // GET http://localhost:8081/api/hr/candidates/1
    public ResponseEntity<Candidate> getCandidateById(@PathVariable("id") Long id) {
        Optional<Candidate> candidate = candidateService.getCandidateById(id);
        return candidate.map(ResponseEntity::ok)
                        .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping // POST http://localhost:8081/api/hr/candidates
    public ResponseEntity<Candidate> createCandidate(@RequestBody Candidate candidate) {
        Candidate createdCandidate = candidateService.createCandidate(candidate);
        return new ResponseEntity<>(createdCandidate, HttpStatus.CREATED);
    }

    @PutMapping("/{id}") // PUT http://localhost:8081/api/hr/candidates/1
    public ResponseEntity<Candidate> updateCandidate(@PathVariable("id") Long id, @RequestBody Candidate candidateDetails) {
        Candidate updatedCandidate = candidateService.updateCandidate(id, candidateDetails);
        if (updatedCandidate != null) {
            return ResponseEntity.ok(updatedCandidate);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}") // DELETE http://localhost:8081/api/hr/candidates/1
    public ResponseEntity<Void> deleteCandidate(@PathVariable("id") Long id) {
        if (candidateService.getCandidateById(id).isPresent()) {
            candidateService.deleteCandidate(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/status/{status}") // GET http://localhost:8081/api/hr/candidates/status/Applied
    public List<Candidate> getCandidatesByStatus(@PathVariable("status") String status) {
        return candidateService.getCandidatesByStatus(status);
    }

    // --- Core HR Functionality: Send Interview Invitation ---
    @PostMapping("/{id}/send-interview-invite") // POST http://localhost:8081/api/hr/candidates/1/send-interview-invite
    public ResponseEntity<String> sendInterviewInvite(@PathVariable("id") Long id) {
        String result = candidateService.sendInterviewInvitation(id);
        if (result.contains("sent")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
    }
}
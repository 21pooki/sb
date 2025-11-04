//package com.example.hr.service;
//
//import com.example.hr.model.Candidate;
//import com.example.hr.repository.CandidateRepository;
//import org.springframework.stereotype.Service;
//// import org.springframework.mail.SimpleMailMessage; // Uncomment for real email
//// import org.springframework.mail.javamail.JavaMailSender; // Uncomment for real email
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class CandidateService {
//
//    private final CandidateRepository candidateRepository;
//    // private final JavaMailSender mailSender; // Uncomment for real email
//
//    // Adjust constructor if using JavaMailSender
//    public CandidateService(CandidateRepository candidateRepository /*, JavaMailSender mailSender */) {
//        this.candidateRepository = candidateRepository;
//        // this.mailSender = mailSender; // Uncomment for real email
//    }
//
//    public List<Candidate> getAllCandidates() {
//        return candidateRepository.findAll();
//    }
//
//    public Optional<Candidate> getCandidateById(Long id) {
//        return candidateRepository.findById(id);
//    }
//
//    public Candidate createCandidate(Candidate candidate) {
//        // Default status if not provided
//        if (candidate.getStatus() == null || candidate.getStatus().isEmpty()) {
//            candidate.setStatus("Applied");
//        }
//        return candidateRepository.save(candidate);
//    }
//
//    public Candidate updateCandidate(Long id, Candidate candidateDetails) {
//        Optional<Candidate> candidateOptional = candidateRepository.findById(id);
//        if (candidateOptional.isPresent()) {
//            Candidate candidate = candidateOptional.get();
//            candidate.setFirstName(candidateDetails.getFirstName());
//            candidate.setLastName(candidateDetails.getLastName());
//            candidate.setEmail(candidateDetails.getEmail());
//            candidate.setPhone(candidateDetails.getPhone());
//            candidate.setStatus(candidateDetails.getStatus());
//            return candidateRepository.save(candidate);
//        }
//        return null; // Candidate not found
//    }
//
//    public void deleteCandidate(Long id) {
//        candidateRepository.deleteById(id);
//    }
//
//    public List<Candidate> getCandidatesByStatus(String status) {
//        return candidateRepository.findByStatus(status);
//    }
//
//    /**
//     * Sends an interview invitation message to a candidate (simulated).
//     * Updates candidate status to "Interview Scheduled".
//     */
//    public String sendInterviewInvitation(Long candidateId) {
//        Optional<Candidate> candidateOptional = candidateRepository.findById(candidateId);
//
//        if (candidateOptional.isEmpty()) {
//            return "Candidate not found!";
//        }
//
//        Candidate candidate = candidateOptional.get();
//        String recipientEmail = candidate.getEmail();
//        String candidateName = candidate.getFirstName() + " " + candidate.getLastName();
//        String subject = "Interview Invitation - " + candidateName;
//        String messageBody = "Dear " + candidateName + ",\n\n"
//                           + "You have been selected for an interview for the position you applied for. "
//                           + "We will contact you shortly to schedule a suitable time.\n\n"
//                           + "Best regards,\n"
//                           + "The HR Team";
//
//        // --- Simulated Email Sending ---
//        System.out.println("--- SIMULATING EMAIL SEND ---");
//        System.out.println("To: " + recipientEmail);
//        System.out.println("Subject: " + subject);
//        System.out.println("Body:\n" + messageBody);
//        System.out.println("-----------------------------");
//
//        // --- For Real Email Sending (uncomment and configure application.properties) ---
//        /*
//        SimpleMailMessage email = new SimpleMailMessage();
//        email.setTo(recipientEmail);
//        email.setSubject(subject);
//        email.setText(messageBody);
//        mailSender.send(email);
//        */
//
//        candidate.setStatus("Interview Scheduled");
//        candidateRepository.save(candidate); // Update status in DB
//
//        return "Interview invitation sent to " + candidate.getEmail() + " and status updated.";
//    }
//}





package com.example.hr.service;

import com.example.hr.model.Candidate;
import com.example.hr.repository.CandidateRepository;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender; // <--- UNCOMMENT/ADD THIS IMPORT
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CandidateService {

    private final CandidateRepository candidateRepository;
    private final JavaMailSender mailSender; // <--- UNCOMMENT/ADD THIS LINE

    // <--- ADJUST CONSTRUCTOR HERE TO INJECT JavaMailSender
    public CandidateService(CandidateRepository candidateRepository, JavaMailSender mailSender) {
        this.candidateRepository = candidateRepository;
        this.mailSender = mailSender; // <--- UNCOMMENT THIS LINE
    }

    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }

    public Optional<Candidate> getCandidateById(Long id) {
        return candidateRepository.findById(id);
    }

    public Candidate createCandidate(Candidate candidate) {
        if (candidate.getStatus() == null || candidate.getStatus().isEmpty()) {
            candidate.setStatus("Applied");
        }
        return candidateRepository.save(candidate);
    }

    public Candidate updateCandidate(Long id, Candidate candidateDetails) {
        Optional<Candidate> candidateOptional = candidateRepository.findById(id);
        if (candidateOptional.isPresent()) {
            Candidate candidate = candidateOptional.get();
            candidate.setFirstName(candidateDetails.getFirstName());
            candidate.setLastName(candidateDetails.getLastName());
            candidate.setEmail(candidateDetails.getEmail());
            candidate.setPhone(candidateDetails.getPhone());
            candidate.setStatus(candidateDetails.getStatus());
            return candidateRepository.save(candidate);
        }
        return null;
    }

    public void deleteCandidate(Long id) {
        candidateRepository.deleteById(id);
    }

    public List<Candidate> getCandidatesByStatus(String status) {
        return candidateRepository.findByStatus(status);
    }

    public String sendInterviewInvitation(Long candidateId) {
        Optional<Candidate> candidateOptional = candidateRepository.findById(candidateId);

        if (candidateOptional.isEmpty()) {
            return "Candidate not found!";
        }

        Candidate candidate = candidateOptional.get();
        String recipientEmail = candidate.getEmail();
        String candidateName = candidate.getFirstName() + " " + candidate.getLastName();
        String subject = "Interview Invitation - " + candidateName;
        String messageBody = "Dear " + candidateName + ",\n\n"
                           + "You have been selected for an interview for the position you applied for. "
                           + "We will contact you shortly to schedule a suitable time.\n\n"
                           + "Best regards,\n"
                           + "The HR Team";

        // --- Simulated Email Sending (keep this for console log too, or remove if only real emails) ---
        System.out.println("--- SIMULATING EMAIL SEND ---");
        System.out.println("To: " + recipientEmail);
        System.out.println("Subject: " + subject);
        System.out.println("Body:\n" + messageBody);
        System.out.println("-----------------------------");

        // --- For Real Email Sending (now uncommented and working with injected mailSender) ---
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientEmail);
        email.setSubject(subject);
        email.setText(messageBody);
        mailSender.send(email); // <--- THIS WILL NOW WORK

        candidate.setStatus("Interview Scheduled");
        candidateRepository.save(candidate);

        return "Interview invitation sent to " + candidate.getEmail() + " and status updated.";
    }
}
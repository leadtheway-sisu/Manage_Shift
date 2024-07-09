package eight.pj.trial.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eight.pj.trial.repository.CompleteShiftRepository;
import jakarta.servlet.http.HttpServletRequest;
import eight.pj.trial.entity.Shift;

@RestController
@RequestMapping("/api")
public class CompleteShiftController {  // admin.js&htmlで完成版シフトを保存する

    private final CompleteShiftRepository compShiftRepo;

    public CompleteShiftController(CompleteShiftRepository compShiftRepo) {
        this.compShiftRepo = compShiftRepo;
    }

    @PostMapping("/save-shifts")
    public ResponseEntity<String> saveShifts(@RequestBody List<Shift> shifts) {
        try {
            compShiftRepo.saveAll(shifts);
            return ResponseEntity.ok("Shifts saved successfully!"); // return new ResponseEntity<>("Shifts saved successfully!", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving shifts");
        }
    }

    
    @GetMapping("/csrf-token-endpoint")
    public Map<String, String> getCsrfToken(HttpServletRequest request, CsrfToken token) {
        Map<String, String> csrfToken = new HashMap<>();
        csrfToken.put("csrfToken", token.getToken());
        return csrfToken;
    }
}

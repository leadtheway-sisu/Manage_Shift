package eight.pj.trial.controller;

import eight.pj.trial.entity.Shift;
import eight.pj.trial.repository.CompleteShiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ShowShiftController {  //index.js&htmlにシフト表を表示させる

    private final CompleteShiftRepository completeShiftRepo;

    @Autowired
    public ShowShiftController(CompleteShiftRepository completeShiftRepo) {
        this.completeShiftRepo = completeShiftRepo;
    }

    @GetMapping("/all-shifts")
    public List<Shift> getAllShifts() {
        return completeShiftRepo.findAll();
    }
}

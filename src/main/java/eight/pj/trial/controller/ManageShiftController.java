package eight.pj.trial.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eight.pj.trial.repository.HolidayRequestRepository;
import eight.pj.trial.entity.HolidayRequest;

@RestController
@RequestMapping("/api/holiday-requests")
public class ManageShiftController { //admin.jsで、DBのデータを取得（＆表示）するためのクラス
	
    @Autowired
    private HolidayRequestRepository HolidayRequestRepo;

    @GetMapping("/all")
    public List<HolidayRequest> getAllHolidayRequests() {
        return HolidayRequestRepo.findAll();
        
    }
}

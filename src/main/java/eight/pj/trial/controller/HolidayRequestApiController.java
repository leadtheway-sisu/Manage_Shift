package eight.pj.trial.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eight.pj.trial.repository.HolidayRequestRepository;
import eight.pj.trial.entity.HolidayRequest;

@RestController
@RequestMapping("/api")
public class HolidayRequestApiController { //user.jsで、DBから休日リクエストのデータを取得（＆表示）するためのクラス
	
    @Autowired
    private HolidayRequestRepository HolidayRequestRepo;

    @GetMapping("/saved-dates")
    public ResponseEntity<List<HolidayRequest>> getSavedDates(@AuthenticationPrincipal UserDetails userDetails) {
        // ログインユーザー名を取得
        String username = userDetails.getUsername();

        // ログインユーザーに紐づく休日リクエストを取得
        List<HolidayRequest> holidays = HolidayRequestRepo.findByUsername(username);

        System.out.println("Retrieved holiday requests for user: " + username);
        for (HolidayRequest holiday : holidays) {
            System.out.println("Date: " + holiday.getSelectedDates() + ", Username: " + holiday.getUsername());
        }

        return ResponseEntity.ok().body(holidays);
    }
}
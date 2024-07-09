package eight.pj.trial.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import eight.pj.trial.entity.HolidayRequest;
import eight.pj.trial.repository.HolidayRequestRepository;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class HolidayRequestController {

    @Autowired
    private HolidayRequestRepository holidayRequestRepository;
    
    @GetMapping("/submitSuccess")
    public String SubmitSuccess() {
    	return "/submitSuccess";
    }
    
    @PostMapping("/submitSuccess")
    public ResponseEntity<String> saveShift(@RequestBody String shiftDataJson) {
        try {
            // JSON文字列からオブジェクトへの変換
            ObjectMapper mapper = new ObjectMapper();
            ShiftData shiftData = mapper.readValue(shiftDataJson, ShiftData.class);

            // 既存の日付データを取得
            List<LocalDate> existingDates = holidayRequestRepository.findSelectedDatesByUsername(shiftData.getUsername());

            // 新しい日付データの準備
            List<LocalDate> newDates = shiftData.getSelectedDates().stream()
                    .map(LocalDate::parse)
                    .collect(Collectors.toList());

            // 新しい日付データの保存
            for (LocalDate date : newDates) {
                if (!existingDates.contains(date)) { // 既存の日付データと重複しない場合のみ保存
                    HolidayRequest holidayRequest = new HolidayRequest();
                    holidayRequest.setSelectedDates(date);
                    holidayRequest.setUsername(shiftData.getUsername());
                    holidayRequestRepository.save(holidayRequest);
                }
            }

            // クライアントに成功レスポンスを返す
            return ResponseEntity.ok("シフトが保存されました");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("シフトの保存中にエラーが発生しました");
        }
    }


    @RestController
    public class CsrfTokenController {
        
    @GetMapping("/csrf-token-endpoint")
        public Map<String, String> getCsrfToken(HttpServletRequest request, CsrfToken token) {
            Map<String, String> csrfToken = new HashMap<>();
            csrfToken.put("csrfToken", token.getToken());
            return csrfToken;
        }
    }
       
    // フロントエンドから送信されるJSONデータの構造を定義するクラス
    static class ShiftData {
        private String username;
        private List<String> selectedDates;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public List<String> getSelectedDates() {
            return selectedDates;
        }

        public void setSelectedDates(List<String> selectedDates) {
            this.selectedDates = selectedDates;
        }
    }
}

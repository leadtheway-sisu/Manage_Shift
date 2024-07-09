package eight.pj.trial.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="holiday_request")
public class HolidayRequest{  //休み希望を提出するための情報を保持

	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
	@Column(name = "date")
	private LocalDate selectedDates;

	@Column(name = "username")
    private String username;

    public LocalDate getSelectedDates() {
        return selectedDates;
    }

    public void setSelectedDates(LocalDate selectedDates) {
        this.selectedDates = selectedDates;
    }    
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

	public void setUpdatedDate(LocalDate now) {
		// TODO Auto-generated method stub
		
	}

}

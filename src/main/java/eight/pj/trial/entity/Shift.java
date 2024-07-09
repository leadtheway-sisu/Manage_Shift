package eight.pj.trial.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="complete_shift")
public class Shift {

		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    
		@Column(name = "username")
		String username;

		@Column(name = "selectedDate") 
		LocalDate selectedDate;

		@Column(name = "shiftType")
		String shiftType;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public LocalDate getSelectedDate() {
			return selectedDate;
		}

		public void setSelectedDate(LocalDate selectedDate) {
			this.selectedDate = selectedDate;
		}

		public String getShiftType() {
			return shiftType;
		}

		public void setShiftType(String shiftType) {
			this.shiftType = shiftType;
		}

}

package eight.pj.trial.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import eight.pj.trial.entity.HolidayRequest;
import jakarta.transaction.Transactional;

@Repository   
public interface HolidayRequestRepository extends JpaRepository<HolidayRequest, Long> {

    @Query("SELECT hr.selectedDates FROM HolidayRequest hr WHERE hr.username = :username")
    List<LocalDate> findSelectedDatesByUsername(@Param("username") String username);

	List<HolidayRequest> findByUsername(String username);

	List<HolidayRequest> findByUsernameAndSelectedDatesIn(String username, List<LocalDate> selectedDates);

    @Modifying
    @Transactional
    @Query("DELETE FROM HolidayRequest hr WHERE hr.username = :username")
    void deleteByUsername(@Param("username") String username);

    @Modifying
    @Transactional
    @Query("DELETE FROM HolidayRequest hr WHERE hr.username = :username AND hr.selectedDates = :date")
    void deleteByUsernameAndSelectedDates(@Param("username") String username, @Param("date") LocalDate date);

}
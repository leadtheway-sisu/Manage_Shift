package eight.pj.trial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eight.pj.trial.entity.Shift;

@Repository 
public interface CompleteShiftRepository extends JpaRepository<Shift, Long> {
    
}
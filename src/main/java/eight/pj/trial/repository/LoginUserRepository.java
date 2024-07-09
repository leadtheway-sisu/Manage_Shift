package eight.pj.trial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eight.pj.trial.entity.LoginUser;

@Repository
public interface LoginUserRepository extends JpaRepository<LoginUser, Long> {

	LoginUser findByName(String name);
                                                                    
}

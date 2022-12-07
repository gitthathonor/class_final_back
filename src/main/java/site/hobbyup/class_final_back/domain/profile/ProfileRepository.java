package site.hobbyup.class_final_back.domain.profile;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    @Query("select p from Profile p join fetch p.user u where p.user.id = :userId")
    Profile findByUserId(@Param("userId") Long userId);
}

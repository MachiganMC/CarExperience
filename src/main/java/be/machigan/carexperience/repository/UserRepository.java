package be.machigan.carexperience.repository;

import be.machigan.carexperience.entity.User;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> getByUsername(String username);

    @Query(value = """
        SELECT (COUNT(u) > 0) FROM User u
    """)
    boolean existsOne();

    @SQLRestriction("")
    boolean existsByUsername(String username);
}

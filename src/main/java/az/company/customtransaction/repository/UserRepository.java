package az.company.customtransaction.repository;

import az.company.customtransaction.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

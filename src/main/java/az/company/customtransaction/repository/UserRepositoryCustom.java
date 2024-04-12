package az.company.customtransaction.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositoryCustom {

    void saveUser(String username, String email);
    void updateUser(String username, String email, Long id);
}

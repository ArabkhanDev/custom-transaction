package az.company.customtransaction.repository.impl;

import az.company.customtransaction.repository.UserRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepositoryCustom {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void saveUser(String username, String email) {
        String sql = "INSERT INTO user (username, email) VALUES (?, ?)";
        jdbcTemplate.update(sql, username, email);

    }

    @Override
    public void updateUser(String username, String email, Long id) {
        String sql = "UPDATE user SET username = ?, email = ? WHERE id = ?";
        jdbcTemplate.update(sql, username, email, id);
    }
}

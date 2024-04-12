package az.company.customtransaction.service;

import az.company.customtransaction.annotation.MyTransactional;
import az.company.customtransaction.entity.User;
import az.company.customtransaction.repository.UserRepository;
import az.company.customtransaction.repository.UserRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;

@Service
public class UserService {


    private final UserRepositoryCustom userRepositoryCustom;
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepositoryCustom userRepositoryCustom, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.userRepositoryCustom = userRepositoryCustom;
    }

    @MyTransactional
    public User createUser(String username, String email) throws Exception{
        userRepositoryCustom.saveUser(username, email);
        throw new Exception();  // save user
//        throw new RuntimeException();  does not save user

    }


    @MyTransactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW)
    public User updateUser(Long id, String username, String email) throws Exception{
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found " + id));
        user.setUsername(username);
        user.setEmail(email);
        userRepositoryCustom.updateUser(user.getUsername(), user.getEmail(), id);
        //throw new Exception();  update user
        throw new RuntimeException(); // does not update the user
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found " + userId));
    }
}

package Google.service;

import Google.dao.UserRepository;
import Google.entity.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private static Map<String, User> tokenStore = new HashMap<>();

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<Boolean> checkUserExists(String email) {
        return userRepository.findByEmail(email).isPresent() ? Optional.of(true) : Optional.empty();
    }
    public Optional<Boolean> userAuth(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent() && user.get().getPassword().equals(password) ? Optional.of(true) : Optional.empty();
    }
}

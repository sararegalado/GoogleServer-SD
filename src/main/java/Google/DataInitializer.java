package Google;

import Google.dao.UserRepository;
import Google.entity.User;
import jakarta.transaction.Transactional;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;

import java.util.List;


@Configuration
public class DataInitializer {
    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    @Bean
    @Transactional
    CommandLineRunner innitData(UserRepository userRepository) {
        return args -> {
            // Database is already initialized
            if (userRepository.count() > 0) {
                return;
            }
            // Create some users
            User u1 = new User("user1@opendeusto.es", "user1");
            User u2 = new User("user2@google.es", "user2");
            User u3 = new User("user3@google.es", "user3");
            User u4 = new User("user4@opendeusto.es", "user4");

            // Save users
            userRepository.save(u1);
            userRepository.save(u2);
            userRepository.save(u3);
            userRepository.save(u4);
            logger.info("Users saved");
        };
    }
}

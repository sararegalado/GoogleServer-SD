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
            User u2 = new User("user2@opendeusto.es", "user2");
            User u3 = new User("user3@opendeusto.es", "user3");

            // Save users
            userRepository.saveAll(List.of(u1, u2, u3));
            logger.info("Users saved");
        };
    }
}

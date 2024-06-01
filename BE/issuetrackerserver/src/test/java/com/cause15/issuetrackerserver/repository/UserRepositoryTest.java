package com.cause15.issuetrackerserver.repository;

import com.cause15.issuetrackerserver.model.User;
import com.cause15.issuetrackerserver.model.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user1;
    private User user2;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();

        user1 = new User();
        user1.setId(UUID.randomUUID());
        user1.setName("minseok");
        user1.setType(UserType.ADMIN);

        user2 = new User();
        user2.setId(UUID.randomUUID());
        user2.setName("eunki");
        user2.setType(UserType.DEVELOPER);

        userRepository.save(user1);
        userRepository.save(user2);
    }

    @Test
    public void testGetByName() {
        User user1 = userRepository.getByName("minseok");
        assertThat(user1.getName()).isEqualTo("minseok");
    }

    @Test
    public void testGetAllByType() {
        List<User> admins = userRepository.getAllByType(UserType.ADMIN);
        List<User> devs = userRepository.getAllByType(UserType.DEVELOPER);

        assertThat(admins).hasSize(1);
        assertThat(devs).hasSize(1);
    }
}

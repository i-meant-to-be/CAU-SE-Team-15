package com.cause15.issuetrackerserver.repository;

import com.cause15.issuetrackerserver.model.User;
import com.cause15.issuetrackerserver.model.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.TestPropertySource;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user1;
    private User user2;


    // 각 테스트 시작 전, Setup. 디비에 2명의 Dev를 생성
    @BeforeEach
    public void setUp() {
        // 중복 저장을 방지하기 위해 이미 존재하는지 확인
        //CREATE
        if (userRepository.getByName("eunki") == null) {
            user1 = new User();
            user1.setId(UUID.randomUUID());
            user1.setPassword("123");
            user1.setName("eunki");
            user1.setType(UserType.DEVELOPER);
            user1.setTags(new HashSet<>());
            userRepository.save(user1);
        } else {
            user1 = userRepository.getByName("eunki");
        }

        if (userRepository.getByName("jihwan") == null) {
            user2 = new User();
            user2.setId(UUID.randomUUID());
            user2.setName("jihwan");
            user2.setPassword("123");
            user2.setTags(new HashSet<>());
            user2.setType(UserType.DEVELOPER);
            userRepository.save(user2);
        } else {
            user2 = userRepository.getByName("jihwan");
        }
    }

    //isEqual 메소드는 오버라이드됨
    //name으로 DB에서 찾아온 user가 user1과 동일한지 체크
    //READ
    @Test
    public void testGetByName() {
        User user = userRepository.getByName("eunki");
        assertThat(user).isNotNull();
        assertThat(user).isEqualTo(user1);
    }

    //이름으로 user를 찾아오고, password를 업데이트
    //업데이트한 user와 동일한지 체크
    //UPDATE
    @Test
    public void testUpdateUser() {
        User user = userRepository.getByName("jihwan");
        assertThat(user).isNotNull();

        user.setPassword("987");
        userRepository.save(user);

        User updatedUser = userRepository.getByName("jihwan");
        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.getPassword()).isEqualTo("987");
    }
    //이름으로 읽어온 user가 있는지 우선 확인하고 있다면 삭제합니다.
    //삭제하고 다시 읽어왔을때 NULL인지 체크합니다.
    @Test
    public void testDeleteUser() {
        User user = userRepository.getByName("eunki");
        assertThat(user).isNotNull();

        userRepository.delete(user);

        User deletedUser = userRepository.getByName("eunki");
        assertThat(deletedUser).isNull();

        User user2 = userRepository.getByName("jihwan");
        assertThat(user).isNotNull();

        userRepository.delete(user2);

        User deletedUser2 = userRepository.getByName("jihwan");
        assertThat(deletedUser2).isNull();
    }

    //현재 DB에 저장된 ADMIN은 2명입니다.
    //따라서 user의 Type에 따라 검사하는 메소드를 테스트합니다.
    //getAllType 메소드로 ADMIN을 찾고, 그 인원이 두명인지 확인합니다.
    @Test
    public void testGetAllByType() {
        List<User> devs = userRepository.getAllByType(UserType.ADMIN);
        assertThat(devs).hasSize(2);
    }
}

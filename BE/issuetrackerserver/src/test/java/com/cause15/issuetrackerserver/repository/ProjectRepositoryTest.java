package com.cause15.issuetrackerserver.repository;


import com.cause15.issuetrackerserver.model.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
@DataMongoTest
@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")
public class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    private Project project1;

    //각 테스트 시작 전, setup함수
    //title이 testProject인 project를 하나 생성합니다.
    //이미 있는지 체크해 project1을 저장합니다.
    @BeforeEach
    public void setUp() {
        // CREATE , READ
        if (projectRepository.getByTitle("testProject1")==null) {
            project1 = new Project();
            project1.setId(UUID.randomUUID());
            project1.setTitle("testProject1");
            project1.setDescription("Description TP1");
            projectRepository.save(project1);
        } else {
            project1 = projectRepository.getByTitle("testProject1");
        }
    }
    //title이름으로 project를 찾아오고, description을 업데이트함
    //업데이트한 project와 동일한지 체크
    //READ, UPDATE
    @Test
    public void testUpdateProject() {
        Project project = projectRepository.getByTitle("testProject1");
        assertThat(project).isNotNull();
        project.setTitle("testProject2");
        project.setDescription("Updated Description");
        projectRepository.save(project);

        Project updatedProject = projectRepository.getByTitle("testProject2");
        assertThat(updatedProject).isNotNull();
        assertThat(updatedProject.getDescription()).isEqualTo("Updated Description");
        projectRepository.delete(updatedProject);
    }

    //Title로 읽어온 Project가 있는지 우선 확인하고 있다면 삭제합니다.
    //삭제하고 다시 읽어왔을때 NULL인지 체크합니다.
    @Test
    public void testDeleteUser(){
        Project project=projectRepository.getByTitle("testProject1");
        assertThat(project).isNotNull();

        projectRepository.delete(project);

        Project deletedProject=projectRepository.getByTitle("testProject1");
        assertThat(deletedProject).isNull();
    }
}

package com.zyfera.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.zyfera.config.NoJpaAuditingConfig;
import com.zyfera.dto.incoming.StudentCreateDto;
import com.zyfera.dto.outgoing.StudentDto;
import com.zyfera.mapper.MapperUtil;
import com.zyfera.service.StudentService;
import com.zyfera.service.impl.TestDocumentInitializer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

// @ContextConfiguration(classes = {StudentController.class, StudentService.class})
// @RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
@Import(NoJpaAuditingConfig.class)
@WebMvcTest(StudentController.class)
@ExtendWith(MockitoExtension.class)
public class StudentControllerTest {

  @InjectMocks private StudentController studentController;
  @Autowired private MockMvc mvc;
  @MockBean private StudentService studentService;
  @Spy private MapperUtil mapperUtil = new MapperUtil(new ModelMapper());

  private static String toJsonString(final Object obj) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
      objectMapper.registerModule(new JavaTimeModule());
      return objectMapper.writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void testCreateStudent() throws Exception {

    // Retrieve the mock StudentCreateDto from TestDocumentInitializer
    StudentCreateDto studentCreateDto = TestDocumentInitializer.getStudent();

    StudentDto student = mapperUtil.convertToType(studentCreateDto, new StudentDto());

    // Mock the service layer
    when(studentService.save(any(StudentCreateDto.class))).thenReturn(student);

    // Call the controller method

    mvc.perform(
            MockMvcRequestBuilders.post("/api/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(studentCreateDto)))
        .andExpect(status().isOk())
        .andExpect(
            MockMvcResultMatchers.jsonPath("message").value("student is successfully created"));

    // Verify the interaction with the service
    Mockito.verify(studentService, Mockito.times(1)).save(any(StudentCreateDto.class));
  }
}

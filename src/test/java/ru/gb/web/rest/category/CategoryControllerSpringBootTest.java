package ru.gb.web.rest.category;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.bytebuddy.utility.RandomString;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.gb.web.dto.CategoryDto;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CategoryControllerSpringBootTest {
    private static String CATEGORY_NAME = "Electronic";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @Order(1)
    public void saveCategoryTest() throws Exception {
        mockMvc.perform(post("/api/v1/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper
                                .writeValueAsString(CategoryDto.builder()
                                        .title(CATEGORY_NAME)
                                        .build()
                                )
                        ))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(2)
    public void findAllTest() throws Exception {
        mockMvc.perform(get("/api/v1/category"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$.[0].id").value("1"))
                .andExpect(jsonPath("$.[0].title").value(CATEGORY_NAME));
    }

    @Test
    @Order(3)
    public void editCategoryTest() throws Exception {
        CATEGORY_NAME = CATEGORY_NAME + UUID.randomUUID();
        mockMvc.perform(put("/api/v1/category/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper
                                .writeValueAsString(CategoryDto.builder()
                                        .title(CATEGORY_NAME)
                                        .build()
                                )
                        ))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/v1/category"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$.[0].id").value("1"))
                .andExpect(jsonPath("$.[0].title").value(CATEGORY_NAME));
    }

    @Test
    @Order(4)
    public void deleteCategoryTest() throws Exception {
        mockMvc.perform(delete("/api/v1/category/1"))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/v1/category"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    public void createCategoryWithNullTitleExpectBadRequest() throws Exception {
        mockMvc.perform(post("/api/v1/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(CategoryDto.builder()
                        .title(null)
                        .build())
                ))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createCategoryWithEmptyTitleExpectBadRequest() throws Exception {
        mockMvc.perform(post("/api/v1/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CategoryDto.builder()
                                .title("")
                                .build())
                        ))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createCategoryWithMaxTitleExpectBadRequest() throws Exception {
        mockMvc.perform(post("/api/v1/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CategoryDto.builder()
                                .title(RandomString.make(256))
                                .build())
                        ))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createCategoryWithMaxTitleExpectCreate() throws Exception {
        mockMvc.perform(post("/api/v1/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CategoryDto.builder()
                                .title(RandomString.make(255))
                                .build())
                        ))
                .andExpect(status().isCreated());
    }
}
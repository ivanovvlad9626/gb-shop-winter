package ru.gb.web.rest.manufacturer;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.gb.service.ManufacturerService;
import ru.gb.web.dto.ManufacturerDto;
import ru.gb.web.rest.ManufacturerController;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ManufacturerController.class)
class ManufacturerControllerMockMvcTest {

    @MockBean
    ManufacturerService manufacturerService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void findAllTest() throws Exception {
        ArrayList<ManufacturerDto> manufacturerDtoList = new ArrayList<>() {{
            add(new ManufacturerDto(1L, "Apple"));
            add(new ManufacturerDto(2L, "Microsoft"));
        }};

        Mockito.when(manufacturerService.findAll()).thenReturn(manufacturerDtoList);

        mockMvc.perform(get("/api/v1/manufacturer"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)));
    }

}
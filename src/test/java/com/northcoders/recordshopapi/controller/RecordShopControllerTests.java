package com.northcoders.recordshopapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.northcoders.recordshopapi.model.Album;
import com.northcoders.recordshopapi.model.Genre;
import com.northcoders.recordshopapi.repository.RecordShopRepository;
import com.northcoders.recordshopapi.service.RecordShopService;
import com.northcoders.recordshopapi.service.RecordShopServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class RecordShopControllerTests {

    @Mock
    private RecordShopService mockRecordShopService;

    @InjectMocks
    private RecordShopController recordShopController;

    @Autowired
    private MockMvc mockMvcController;

    private ObjectMapper mapper;

    @BeforeEach
    public void setup() {
        mockMvcController = MockMvcBuilders.standaloneSetup(recordShopController).build();
        mapper = new ObjectMapper();

    }

    @Test
    void getAllAlbums() throws Exception {

        List<Album> testAlbumList = new ArrayList<>();

        testAlbumList.add(new Album(1L, "Abbey Road", "The Beatles", Genre.POP, "1969", 20));
        testAlbumList.add(new Album(2L, "Led Zeppelin II", "Led Zeppelin", Genre.ROCK, "1969", 10));
        testAlbumList.add(new Album(3L, "The Dark Side of the Moon", "Pink Floyd", Genre.ROCK, "1969", 5));

        when(mockRecordShopService.getAllAlbums()).thenReturn(testAlbumList);
        this.mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/v1/record-shop"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].albumName").value("Abbey Road"))
                .andReturn();
    }



    @Test
    void getAlbumById() throws Exception {

        List<Album> testAlbumList = new ArrayList<>();

        testAlbumList.add(new Album(1L, "Abbey Road", "The Beatles", Genre.POP, "1969", 20));
        testAlbumList.add(new Album(2L, "Led Zeppelin II", "Led Zeppelin", Genre.ROCK, "1969", 10));
        testAlbumList.add(new Album(3L, "The Dark Side of the Moon", "Pink Floyd", Genre.ROCK, "1969", 5));

        when (mockRecordShopService.getAlbumById(1L)).thenReturn(Optional.ofNullable(testAlbumList.getFirst()));
                this.mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/v1/record-shop/1"))
                        .andExpect(status().isFound())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.albumName").value("Abbey Road"))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.artistName").value("The Beatles"))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.genre").value(String.valueOf(Genre.POP)))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.yearReleased").value("1969"))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.stock").value(20))
                        .andReturn();
    }


    @Test
    void addAlbum() throws Exception {

        Album testAlbum = new Album(1L, "Abbey Road", "The Beatles", Genre.POP, "1969", 20);

        when (mockRecordShopService.addAlbum(testAlbum)).thenReturn(testAlbum);

        String testAlbumAsString = mapper.writeValueAsString(testAlbum);

        this.mockMvcController.perform(
                MockMvcRequestBuilders.post("/api/v1/record-shop")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(testAlbumAsString))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.albumName").value("Abbey Road"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.artistName").value("The Beatles"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre").value(String.valueOf(Genre.POP)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.yearReleased").value("1969"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stock").value(20))
                .andReturn();

        }


    @Test
    void updateAlbumById() throws Exception {

        Album testAlbum = new Album(1L, "Abbey Road", "The Beatles", Genre.POP, "1969", 20);

        Album testUpdatedAlbum = new Album(1L, "Abbey Road", "The Beatles", Genre.ROCK, "1973", 5);

        when (mockRecordShopService.updateAlbumById(1L, testAlbum)).thenReturn(testUpdatedAlbum);

        this.mockMvcController.perform(
                MockMvcRequestBuilders.put("/api/v1/record-shop/{id}", testAlbum.getId().toString()))
                .andExpect(status().isAccepted())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.albumName").value("Abbey Road"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.artistName").value("The Beatles"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre").value(String.valueOf(Genre.ROCK)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.yearReleased").value("1973"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stock").value(5))
                .andReturn();
//Likely problem with URI causing 400 error
    }


    @Test
    void deleteAlbumById() throws Exception {

        Album testAlbum = new Album(1L, "Abbey Road", "The Beatles", Genre.POP, "1969", 20);

        String deletedMessage = "The following Album Has Been Removed: " + testAlbum.getAlbumName();

        when(mockRecordShopService.deleteAlbumById(1L)).thenReturn(deletedMessage);

        this.mockMvcController.perform(
                MockMvcRequestBuilders.delete("/api/v1/record-shop/1"))
                .andExpect(status().isAccepted())
                .andReturn();

}
}

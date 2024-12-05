package com.northcoders.recordshopapi.service;

import com.northcoders.recordshopapi.model.Album;
import com.northcoders.recordshopapi.model.Genre;
import com.northcoders.recordshopapi.repository.RecordShopRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DataJpaTest
class RecordShopServiceImplTest {

    @Mock
    private RecordShopRepository mockRecordShopRepository;

    @InjectMocks
    private RecordShopServiceImpl recordShopServiceImpl;

    @Test
    @DisplayName("Test to retrieve all input items")
    public void testGetAllAlbums() {

        List<Album> testAlbumList = new ArrayList<>();

        testAlbumList.add(new Album(1L, "Abbey Road", "The Beatles", Genre.POP, "1969", 20));
        testAlbumList.add(new Album(2L, "Led Zeppelin II", "Led Zeppelin", Genre.ROCK, "1969", 10));
        testAlbumList.add(new Album(3L, "The Dark Side of the Moon", "Pink Floyd", Genre.ROCK, "1969", 5));

        when (mockRecordShopRepository.findAll()).thenReturn(testAlbumList);

        List<Album> testResult = recordShopServiceImpl.getAllAlbums();

        assertThat(testResult).hasSize(3);
        assertThat(testResult).isEqualTo(testAlbumList);





    }

    @Test
    void getAlbumById() {
    }

    @Test
    void addAlbum() {
    }

    @Test
    void updateAlbumById() {
    }

    @Test
    void deleteRecordById() {
    }
}
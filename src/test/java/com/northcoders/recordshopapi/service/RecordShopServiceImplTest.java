package com.northcoders.recordshopapi.service;

import com.northcoders.recordshopapi.model.Album;
import com.northcoders.recordshopapi.model.Genre;
import com.northcoders.recordshopapi.repository.RecordShopRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@DataJpaTest
class RecordShopServiceImplTest {

    @Mock
    private RecordShopRepository mockRecordShopRepository;

    @InjectMocks
    private RecordShopServiceImpl recordShopServiceImpl;

    @Test
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

        List<Album> testAlbumList = new ArrayList<>();

        testAlbumList.add(new Album(1L, "Abbey Road", "The Beatles", Genre.POP, "1969", 20));
        testAlbumList.add(new Album(2L, "Led Zeppelin II", "Led Zeppelin", Genre.ROCK, "1969", 10));
        testAlbumList.add(new Album(3L, "The Dark Side of the Moon", "Pink Floyd", Genre.ROCK, "1969", 5));

        when (mockRecordShopRepository.findById(1L)).thenReturn(Optional.ofNullable(testAlbumList.getFirst()));

        Optional<Album> testResult = recordShopServiceImpl.getAlbumById(1L);

        assertThat(testResult).contains(testAlbumList.getFirst());
    }

    @Test
    void addAlbum() {

        var testAlbum = new Album(1L, "Abbey Road", "The Beatles", Genre.POP, "1969", 20);

        when (mockRecordShopRepository.save(testAlbum)).thenReturn(testAlbum);

        Album testResult = recordShopServiceImpl.addAlbum(testAlbum);

        assertThat(testResult).isEqualTo(testAlbum);
    }

    @Test
    void updateAlbumById() {

        Album testAlbum = new Album(1L, "Abbey Road", "The Beatles", Genre.POP, "1969", 20);
        mockRecordShopRepository.save(testAlbum);

        Album replacementAlbum = new Album(1L, "Abbey Road", "The Beatles", Genre.POP, "1973", 5);

        when (mockRecordShopRepository.findById(1L)).thenReturn(Optional.of(replacementAlbum));

        Album testResult = recordShopServiceImpl.updateAlbumById(1L, replacementAlbum);

        assertThat(testResult).isEqualTo(replacementAlbum);


    }

    @Test
    void deleteAlbumById() {

        Album testAlbum = new Album(1L, "Abbey Road", "The Beatles", Genre.POP, "1969", 20);
        mockRecordShopRepository.save(testAlbum);

        when (mockRecordShopRepository.findById(1L)).thenReturn(Optional.of(testAlbum));
        recordShopServiceImpl.deleteAlbumById(1L);

       assertThat(testAlbum).isNotIn(mockRecordShopRepository);

    }
}
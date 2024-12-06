package com.northcoders.recordshopapi.service;

import com.northcoders.recordshopapi.model.Album;
import com.northcoders.recordshopapi.repository.RecordShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RecordShopServiceImpl implements RecordShopService {

    @Autowired
    RecordShopRepository recordShopRepository;

    @Override
    public List<Album> getAllAlbums() {
        List<Album> albumList = new ArrayList<>();
        recordShopRepository.findAll().forEach(albumList::add);
        return albumList;
    }

    @Override
    public Optional<Album> getAlbumById(Long id) {
        return recordShopRepository.findById(id);
    }

    @Override
    public Album addAlbum(Album album) {
        return recordShopRepository.save(album);
    }

    @Override
    public Album updateAlbumById(Long id, Album album) {
        Album currentAlbum = recordShopRepository.findById(id).get();
        currentAlbum.setAlbumName(album.getAlbumName());
        currentAlbum.setArtistName(album.getArtistName());
        currentAlbum.setGenre(album.getGenre());
        currentAlbum.setYearReleased(album.getYearReleased());
        currentAlbum.setStock(album.getStock());
        recordShopRepository.save(currentAlbum);
        return currentAlbum;
    }

    @Override
    public String deleteAlbumById(Long id) {
        return "";
    }
}


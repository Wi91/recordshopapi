package com.northcoders.recordshopapi.controller;

import com.northcoders.recordshopapi.model.Album;
import com.northcoders.recordshopapi.service.RecordShopService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/record-shop")
public class RecordShopController {

    @Autowired
    RecordShopService recordShopService;

    @GetMapping
    public ResponseEntity<List<Album>> getAllAlbums() {
        List<Album> albumList = recordShopService.getAllAlbums();
        return new ResponseEntity<>(albumList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Album> addAlbum(@RequestBody Album album) {
        Album newAlbum = recordShopService.addAlbum(album);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Album", "/api/v1/record-shop/" + newAlbum.getId().toString());
        return new ResponseEntity<>(newAlbum, httpHeaders, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Album>> getAlbumById(@PathVariable("id") Long id) {
        if (recordShopService.getAlbumById(id).isPresent()) {
            return new ResponseEntity<Optional<Album>>(Optional.of(recordShopService.getAlbumById(id).get()), HttpStatus.FOUND);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Album> updateAlbumById(@PathVariable("id") Long id, @RequestBody Album album) {
        return new ResponseEntity<>(recordShopService.updateAlbumById(id, album), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAlbumByID (@PathVariable("id") Long id) {
        return new ResponseEntity<>(recordShopService.deleteAlbumById(id), HttpStatus.ACCEPTED);
    }

}

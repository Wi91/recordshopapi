package com.northcoders.recordshopapi.service;

import java.util.List;
import java.util.Optional;

public interface RecordShopService {

List<Record> getAllAlbums();

Optional<Record> getAlbumById(Long id);

Record addAlbum(Record record);

Record updateAlbumById(Long id, Record record);

String deleteRecordById(Long id);
}

package com.nftapp.nftapp.Service;

import com.nftapp.nftapp.DTO.CollectionDto;
import com.nftapp.nftapp.DTO.CollectionStatisticDto;
import com.nftapp.nftapp.DTO.ItemDto;
import com.nftapp.nftapp.Model.Collection;
import com.nftapp.nftapp.Model.Item;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CollectionService {
    CollectionDto insertCollection(CollectionDto collectionDto, MultipartFile image) throws Exception;
    void saveCollection(Collection collectionDto, String image);
    boolean deleteCollection(Long collectionId);
    List<CollectionDto> searchByTitle (String title);
    List<Collection> getAllCollections();
    CollectionDto getCollectionById (Long id);
    Collection find(Long id);
}

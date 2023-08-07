package com.fixbugnft.fixbugnft.Service;

import com.fixbugnft.fixbugnft.DTO.CollectionDto;
import com.fixbugnft.fixbugnft.DTO.CollectionStatisticDto;
import com.fixbugnft.fixbugnft.DTO.ItemDto;
import com.fixbugnft.fixbugnft.Model.Collection;
import com.fixbugnft.fixbugnft.Model.Item;
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

    Collection save(CollectionDto collectionDto);
}

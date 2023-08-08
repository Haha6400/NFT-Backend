package com.fixbugnft.fixbugnft.Service;

import com.fixbugnft.fixbugnft.DTO.CollectionStatisticDto;
import com.fixbugnft.fixbugnft.DTO.ItemDto;
import com.fixbugnft.fixbugnft.Model.Item;
import com.fixbugnft.fixbugnft.Model.Offer;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ItemService {
    ItemDto insertItem(ItemDto itemDto, MultipartFile image) throws Exception;
    void saveImage(ItemDto itemDto, String image);
    void saveItem(Item item, String image);
    boolean deletePost(Long itemId);
    List<Item> searchByTitle (String title);
    List<Item> getAllItems();
    List<Item> getAllItemsByCategory(String category);
    ItemDto getItemById (Long id);
    Item find(Long id);
    Item save(ItemDto itemDto);

    Item getAllItemsByOfferListId (Long offerListId);
}

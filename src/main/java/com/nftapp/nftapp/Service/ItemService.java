package com.nftapp.nftapp.Service;

import com.nftapp.nftapp.DTO.CollectionStatisticDto;
import com.nftapp.nftapp.DTO.ItemDto;
import com.nftapp.nftapp.Model.Item;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ItemService {
    ItemDto insertItem(ItemDto itemDto, MultipartFile image) throws Exception;
    void saveImage(ItemDto itemDto, String image);
    void saveItem(Item item, String image);
    boolean deletePost(Long itemId);
    List<ItemDto> searchByTitle (String title);
    List<Item> getAllItems();
    List<ItemDto> getAllItemsByCategory(String category);
    ItemDto getItemById (Long id);
    Item find(Long id);

    Item save(ItemDto itemDto);
}

package com.nftapp.nftapp.Service.Impl;

import com.nftapp.nftapp.DTO.ItemDto;
import com.nftapp.nftapp.Model.Collection;
import com.nftapp.nftapp.Model.Item;
import com.nftapp.nftapp.Repository.ItemRepo;
import com.nftapp.nftapp.Repository.UserRepo;
import com.nftapp.nftapp.Service.ItemService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemRepo itemRepo;
    public ModelMapper modelMapper;

    @Autowired
    public UserRepo userRepository;

    @Autowired
    public FileServiceImpl fileService;
    @Value("${project.image}")
    private String path;
    @Override
    public ItemDto insertItem(ItemDto itemDto, MultipartFile image) throws Exception {
        Item item = this.modelMapper.map(itemDto, Item.class);
        String fileName = fileService.updateFile(path, image);
        item.setPictureLink(fileName);
        item.setCreatedDate(new Date());
        Item newItem = itemRepo.save(item);
        return this.modelMapper.map(newItem, ItemDto.class);
    }

    @Override
    public void saveItem(Item item, String image) {
//        item.setPictureLink(image);
//        item.setCreatedDate(new Date());
        itemRepo.save(item);
    }

    @Override
    public void saveImage(ItemDto itemDto, String image) {
        Item item = this.modelMapper.map(itemDto, Item.class);
        item.setPictureLink(image);
        item.setCreatedDate(new Date());
        itemRepo.save(item);
    }

    @Override
    public boolean deletePost(Long itemId) {
        Item item = itemRepo.findAllById(itemId);
        if(item == null) {
            return false;
        }
        itemRepo.deleteById(itemId);
        return true;
    }

    @Override
    public List<ItemDto> searchByTitle(String title) {
        List<Item> items = itemRepo.searchByTitle("%" + title + "%");
        return items.stream().map((post) ->
                        this.modelMapper.map(post, ItemDto.class)).
                collect(Collectors.toList());
    }

    @Override
    public List<Item> getAllItems() {
        return itemRepo.findAll();
    }

    @Override
    public ItemDto getItemById(Long id) {
        Item item = itemRepo.findAllById(id);
        return this.modelMapper.map(item, ItemDto.class);
    }

    @Override
    public Item find(Long id) {
        return itemRepo.findAllById(id);
    }
}

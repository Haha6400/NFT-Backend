package com.fixbugnft.fixbugnft.Service.Impl;

import com.fixbugnft.fixbugnft.DTO.ItemDto;
import com.fixbugnft.fixbugnft.Model.Category;
import com.fixbugnft.fixbugnft.Model.Item;
import com.fixbugnft.fixbugnft.Repository.CategoryRepo;
import com.fixbugnft.fixbugnft.Repository.ItemRepo;
import com.fixbugnft.fixbugnft.Repository.UserRepo;
import com.fixbugnft.fixbugnft.Service.ItemService;
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
    public CategoryRepo categoryRepository;

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
    public Item save(ItemDto itemDto){
        Category category = categoryRepository.findByName(itemDto.getCategory());
        if (category == null) {
            category = new Category();
            category.setName(itemDto.getCategory());
            categoryRepository.save(category);
        }
        Item item = itemRepo.findAllById(itemDto.getId());
        if (item == null) {
            item = new Item();
        }
            item.setDescription(itemDto.getDescription());
            item.setName(itemDto.getName());
            item.setCreatedDate(new Date());
            item.setCategory(itemDto.getCategory());
            item.setPrice(itemDto.getPrice());
            item.setStatus(itemDto.getStatus());
            item.setOwnerId(itemDto.getOwner().getId());
            item.setPictureLink(item.getPictureLink());
//            Collection collection = itemDto.getOwner().getCollection();
//            collection.getItemList().add(item);
            itemRepo.save(item);
        return item;
    }


    @Override
    public List<Item> getAllItemsByCategory(String category){
        return itemRepo.getAllItemsByCategory(category);

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
    public List<Item> searchByTitle(String title) {
        return  itemRepo.searchByTitle("%" + title + "%");

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

    @Override
    public Item getAllItemsByOfferListId (Long offerListId){
        return itemRepo.getAllItemsByOfferListId(offerListId);
    }

}

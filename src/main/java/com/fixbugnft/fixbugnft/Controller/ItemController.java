package com.fixbugnft.fixbugnft.Controller;

import com.fixbugnft.fixbugnft.DTO.ItemDto;
import com.fixbugnft.fixbugnft.Model.Item;
import com.fixbugnft.fixbugnft.Model.User;
import com.fixbugnft.fixbugnft.Service.FileService;
import com.fixbugnft.fixbugnft.Service.ItemService;
import com.fixbugnft.fixbugnft.Service.UserService;
import com.fixbugnft.fixbugnft.Model.Status;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/openeye/items")
@Validated
public class ItemController {
    @Autowired
    UserService userService;

    @Autowired
    ItemService itemService;

    @Autowired
    private FileService fileService;
    @Value("${project.image}")
    private String path;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllItem(){
        List<Item> itemList = itemService.getAllItems();
        Map<Object, Object> map = Map.of("total", itemList.size(), "items", itemList);
        return ResponseEntity.ok(map);
    }

    @GetMapping("/{id}")
    public ItemDto getItemById(@PathVariable("id") Long id) {
        return itemService.getItemById(id);
    }

    @GetMapping("/{category}")
    public ResponseEntity<?> listItemByCategory(@PathVariable("category") String category){
        List<ItemDto> itemDtoList = itemService.getAllItemsByCategory(category);
        Map<Object, Object> map = Map.of("total", itemDtoList.size(), "itemList", itemDtoList);
        return ResponseEntity.ok(map);
    }
    @PostMapping("/save")
    public Item createItem(@RequestPart String name,
                                         @RequestPart String description,
                                         @RequestPart Double price,
                                         @RequestPart String category,
                                         @RequestPart MultipartFile fileImage,
                           @RequestPart String ownerName) throws Exception {
        User owner = userService.getUserByUsername(ownerName);
        ItemDto itemDto = new ItemDto(name, description, price, category, new Date(), Status.NEW, owner);
        saveItem(fileImage, itemDto);
        return itemService.save(itemDto);
    }

    private void saveItem(MultipartFile fileImage,
                          ItemDto itemDto) throws Exception {
        String fileName = fileService.updateFile(path, fileImage);
        itemDto.setPictureLink(fileName);
    }

    @PutMapping("/update/{id}")
    public Item updateItem(@RequestPart String name,
                              @RequestPart String description,
                              @RequestPart Double price,
                              @RequestPart String category,
                              @RequestPart MultipartFile fileImage,
                              @PathVariable("id") long id) throws Exception {
        ItemDto itemDto = itemService.getItemById(id);
        if(itemDto == null) return null;
        itemDto.setDescription(description);
        itemDto.setName(name);
        itemDto.setCategory(category);
        itemDto.setPrice(price);
        itemDto.setPictureLink(fileService.updateFile(path, fileImage));
        return itemService.save(itemDto);
    }


    @DeleteMapping("/delete/{id}")
    public void deleteItemById(@PathVariable("id") Long id){
        itemService.deletePost(id);
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> searchItemByName(@PathVariable("name") String name){
        List<ItemDto> itemDtoList = itemService.searchByTitle(name);
        Map<Object, Object> map = Map.of("total", itemDtoList.size(), "items", itemDtoList);
        return ResponseEntity.ok(map);
    }

    //Image upload
//    @PostMapping("/file/upload/{id}")
//    public ResponseEntity<CollectionDto> uploadFile(@RequestParam("file") MultipartFile file,
//                                              @PathVariable Long id) throws Exception {
//        CollectionDto collectionDto = collectionService.getCollectionById(id);
//        String fileName = fileService.updateFile(path, file);
//        collectionDto.setPictureLink(fileName);
//        CollectionDto updatePost = collectionService.updatePost(collectionDto, id);
//        return new ResponseEntity<>(updatePost, HttpStatus.OK);
//    }


    @GetMapping(value = "/file/{fileName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadFile(@PathVariable("fileName") String fileName,
                             HttpServletResponse response) throws Exception {
        InputStream resource = this.fileService.getResource(path, fileName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }
}

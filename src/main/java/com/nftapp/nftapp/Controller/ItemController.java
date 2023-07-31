package com.nftapp.nftapp.Controller;

import com.nftapp.nftapp.DTO.CollectionDto;
import com.nftapp.nftapp.DTO.ItemDto;
import com.nftapp.nftapp.Model.Item;
import com.nftapp.nftapp.Repository.CollectionRepo;
import com.nftapp.nftapp.Service.FileService;
import com.nftapp.nftapp.Service.ItemService;
import com.nftapp.nftapp.Service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/items")
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

    CollectionRepo collectionRepo;

    @PostMapping("/save")
    public ResponseEntity<Item> saveItem(@Valid @RequestBody Item item, UriComponentsBuilder uriComponentsBuilder) {
        this.itemService.saveItem(item, item.getPictureLink());
        UriComponents uriComponents = uriComponentsBuilder.path("/api/v1/items/{id}").buildAndExpand(item.getId());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .location(uriComponents.toUri())
                .body(item);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteItemById(@PathVariable("id") Long id){
        this.itemService.deletePost(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ItemDto> insertItem(@RequestBody ItemDto itemDto, @RequestPart MultipartFile image) throws Exception {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(this.itemService.insertItem(itemDto, image));
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Item>> getAllItem(){
        return ResponseEntity.ok(this.itemService.getAllItems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDto> getItemById(@PathVariable("id") Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(this.itemService.getItemById(id));
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

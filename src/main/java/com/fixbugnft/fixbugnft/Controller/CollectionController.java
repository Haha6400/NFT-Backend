package com.fixbugnft.fixbugnft.Controller;

import com.fixbugnft.fixbugnft.DTO.CollectionDto;
import com.fixbugnft.fixbugnft.Model.Collection;
import com.fixbugnft.fixbugnft.Repository.CollectionRepo;
import com.fixbugnft.fixbugnft.Service.CollectionService;
import com.fixbugnft.fixbugnft.Service.FileService;
import com.fixbugnft.fixbugnft.Service.UserService;
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

/*
CRUD COLLECTION
 */
@RestController
@RequestMapping("/openeye/collection")
@Validated
public class CollectionController {
    @Autowired
    UserService userService;

    CollectionService collectionService;

    @Autowired
    private FileService fileService;
    @Value("${project.image}")
    private String path;

    CollectionRepo collectionRepo;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllCollection(){
        List<Collection> collectionList = collectionService.getAllCollections();
        Map<Object, Object> map = Map.of("total", collectionList.size(), "collections", collectionList);
        return ResponseEntity.ok(map);
    }

    @GetMapping("/{id}")
    public CollectionDto getCollectionById(@PathVariable("id") Long id) {
        return collectionService.getCollectionById(id);
    }


    @PostMapping("/save/{username}")
    public Collection createCollection(@RequestPart String name,
                           @RequestPart String description,
                           @RequestPart Double price,
                           @RequestPart MultipartFile fileImage,
                                       @PathVariable String username) throws Exception {
        CollectionDto collectionDto = new CollectionDto(name, description, new Date(), 0.0, userService.getUserByUsername(username));
        saveCollection(fileImage, collectionDto);
        return collectionService.save(collectionDto);
    }

    private void saveCollection(MultipartFile fileImage,
                          CollectionDto collectionDto) throws Exception {
        String fileName = fileService.updateFile(path, fileImage);
        collectionDto.setPictureLink(fileName);
    }

    @PutMapping("/update/{id}")
    public Collection updateCollection(@RequestPart String name,
                           @RequestPart String description,
                           @RequestPart Double volume,
                           @RequestPart MultipartFile fileImage,
                           @PathVariable("id") long id) throws Exception {
        CollectionDto collectionDto = collectionService.getCollectionById(id);
        if(collectionDto == null) return null;
        collectionDto.setDescription(description);
        collectionDto.setName(name);
        collectionDto.setVolume(volume);
        collectionDto.setPictureLink(fileService.updateFile(path, fileImage));
        return collectionService.save(collectionDto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCollectionById(@PathVariable("id") Long id){
        this.collectionService.deleteCollection(id);
    }
    @GetMapping("/{name}")
    public ResponseEntity<?> searchCollectionByName(@PathVariable("name") String name){
        List<CollectionDto> collectionDtoList = collectionService.searchByTitle(name);
        Map<Object, Object> map = Map.of("total", collectionDtoList.size(), "collections", collectionDtoList);
        return ResponseEntity.ok(map);
    }



    @GetMapping(value = "/file/{fileName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadFile(@PathVariable("fileName") String fileName,
                             HttpServletResponse response) throws Exception {
        InputStream resource = this.fileService.getResource(path, fileName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }
}

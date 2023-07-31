package com.nftapp.nftapp.Controller;

import com.nftapp.nftapp.DTO.CollectionDto;
import com.nftapp.nftapp.DTO.CollectionStatisticDto;
import com.nftapp.nftapp.Model.Collection;
import com.nftapp.nftapp.Param.CollectionQueryCondParam;
import com.nftapp.nftapp.Repository.CollectionRepo;
import com.nftapp.nftapp.Service.CollectionService;
import com.nftapp.nftapp.Service.FileService;
import com.nftapp.nftapp.Service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.aspectj.apache.bcel.classfile.Constant;
import org.aspectj.weaver.patterns.ReferencePointcut;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/*
CRUD COLLECTION
 */
@RestController
@RequestMapping("/collection")
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

    @PostMapping("/save")
    public ResponseEntity<Collection> saveCollection(@Valid @RequestBody Collection collection, UriComponentsBuilder uriComponentsBuilder) {
        this.collectionService.saveCollection(collection, collection.getPictureLink());
        UriComponents uriComponents = uriComponentsBuilder.path("/api/v1/collections/{id}").buildAndExpand(collection.getId());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .location(uriComponents.toUri())
                .body(collection);
    }

//    @PutMapping("/update/{id}")
//    public ResponseEntity<CollectionDto> updateCollection(@RequestPart String name,
//                                              @RequestPart MultipartFile image,
//                                              @RequestPart String description,
//                                              @PathVariable Long id) throws Exception {
//        CollectionDto postDto = new CollectionDto(id, name, description);
//        String fileName = fileService.updateFile(path, image);
//        postDto.setPictureLink(fileName);
//        CollectionDto updatePost = collectionService.updatePost(postDto, postId);
//        return new ResponseEntity<>(updatePost, HttpStatus.OK);
//    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCollectionById(@PathVariable("id") Long id){
        this.collectionService.deleteCollection(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CollectionDto> insertCollection(@RequestBody CollectionDto collectionDto,  @RequestPart MultipartFile image) throws Exception {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(this.collectionService.insertCollection(collectionDto, image));
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Collection>> getAllCollection(){
        return ResponseEntity.ok(this.collectionService.getAllCollections());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CollectionDto> getCollectionById(@PathVariable("id") Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(this.collectionService.getCollectionById(id));
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> searchCollectionByName(@PathVariable("name") String name){
        List<CollectionDto> collectionDtoList = collectionService.searchByTitle(name);
        Map<Object, Object> map = Map.of("total", collectionDtoList.size(), "collections", collectionDtoList);
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

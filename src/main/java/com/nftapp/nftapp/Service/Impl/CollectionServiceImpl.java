package com.nftapp.nftapp.Service.Impl;

//import org.redisson.api.RedissonClient;
import com.nftapp.nftapp.DTO.CollectionDto;
import com.nftapp.nftapp.Param.CollectionQueryCondParam;
import com.nftapp.nftapp.Utils.ThreadPoolUtils;
import com.nftapp.nftapp.DTO.CollectionStatisticDto;
import com.nftapp.nftapp.Model.Collection;
import com.nftapp.nftapp.Repository.CollectionRepo;
import com.nftapp.nftapp.Repository.UserRepo;
import com.nftapp.nftapp.Service.CollectionService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class CollectionServiceImpl implements CollectionService {
    private CollectionRepo collectionRepo;

    public ModelMapper modelMapper;

    public UserRepo userRepo;

    public FileServiceImpl fileService;
    @Value("${project.image}")
    private String path;

    @Transactional
    @Override
    public CollectionDto insertCollection(CollectionDto collectionDto, MultipartFile image) throws Exception {
        Collection collection = this.modelMapper.map(collectionDto, Collection.class);
        String fileName = fileService.updateFile(path, image);
        BeanUtils.copyProperties(this, collection);
        collection.setPictureLink(fileName);
        collection.setCreatedDate(new Date());
        Collection newCollection = collectionRepo.save(collection);
        return this.modelMapper.map(newCollection, CollectionDto.class);
    }

    @Override
    public void saveCollection(Collection collectionDto, String image) {
        Collection collection = this.modelMapper.map(collectionDto, Collection.class);
        collection.setPictureLink(image);
        collection.setCreatedDate(new Date());
        collectionRepo.save(collection);
    }

    @Override
    public boolean deleteCollection(Long collectionId) {
        return false;
    }

    @Override
    public List<CollectionDto> searchByTitle(String title) {
        List<Collection> collections = collectionRepo.searchByName("%" + title + "%");
        return collections.stream().map((post) ->
                        this.modelMapper.map(post, CollectionDto.class)).
                collect(Collectors.toList());
    }

    @Override
    public List<Collection> getAllCollections() {
        CollectionQueryCondParam param = new CollectionQueryCondParam();
        return collectionRepo.findAll(param.buildSpecification(),
                Sort.by(Sort.Order.desc("createTime")));
    }

    @Override
    public CollectionDto getCollectionById(Long id) {
        Collection collection = collectionRepo.findAllById(id);
        return this.modelMapper.map(collection, CollectionDto.class);
    }

    @Override
    public Collection find(Long id) {
        return collectionRepo.findAllById(id);
    }



}

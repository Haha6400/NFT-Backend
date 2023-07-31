package com.nftapp.nftapp.Repository;

import com.nftapp.nftapp.Model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepo extends JpaRepository<Item, Long>{

//    List<Item> findByCollectionIdOrderByOrderNo(String collectionId);
    Item getItemById(Long Id);
    @Query("select i from Item i where i.name like :key")
    List<Item> searchByTitle(@Param("key") String title);

    Item findAllById(Long id);
}
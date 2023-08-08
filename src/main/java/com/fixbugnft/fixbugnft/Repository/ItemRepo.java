package com.fixbugnft.fixbugnft.Repository;

//import com.nftapp.nftapp.Model.Item;
import com.fixbugnft.fixbugnft.Model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepo extends JpaRepository<Item, Long>{

//    List<Item> findByCollectionIdOrderByOrderNo(String collectionId);
    Item getItemById(Long Id);
    @Query("select i from Item i where i.name like :key")
    List<Item> searchByTitle(@Param("key") String title);

    Item findAllById(Long id);

    List<Item> getAllItemsByCategory(String category);

    Item getAllItemsByOfferListId (Long OfferListId);

}
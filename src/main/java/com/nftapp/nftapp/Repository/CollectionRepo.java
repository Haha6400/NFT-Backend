package com.nftapp.nftapp.Repository;

import com.nftapp.nftapp.Model.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface CollectionRepo extends JpaRepository<Collection, String>, JpaSpecificationExecutor<Collection> {

//    List<Collection> findByDeletedFlagFalseOrderByCreateTimeDesc();

    Collection findAllById(Long Id);

    List<Collection> searchByName(String s);
}
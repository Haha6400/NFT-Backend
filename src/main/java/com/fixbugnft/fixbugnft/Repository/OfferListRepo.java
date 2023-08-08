package com.fixbugnft.fixbugnft.Repository;

import com.fixbugnft.fixbugnft.Model.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OfferListRepo extends JpaRepository<Collection, String>, JpaSpecificationExecutor<Collection>  {
}

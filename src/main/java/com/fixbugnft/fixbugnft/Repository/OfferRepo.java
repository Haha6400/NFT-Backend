package com.fixbugnft.fixbugnft.Repository;

//import com.nftapp.nftapp.Model.Offer;
import com.fixbugnft.fixbugnft.Model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfferRepo extends JpaRepository<Offer, Long> {
//    List<Offer> getAll();

    Offer getOfferById(Long id);
}

package com.fixbugnft.fixbugnft.Service;

import com.fixbugnft.fixbugnft.DTO.OfferDto;
import com.fixbugnft.fixbugnft.Model.Offer;

import java.util.List;

public interface OfferService {
//    List<Offer> getAllOffer();

    Offer findById(Long id);

    boolean deleteOffer(Long id);

}

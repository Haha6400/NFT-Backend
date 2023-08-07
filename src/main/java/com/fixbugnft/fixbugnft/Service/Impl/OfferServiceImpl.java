package com.fixbugnft.fixbugnft.Service.Impl;

import com.fixbugnft.fixbugnft.Model.Offer;
import com.fixbugnft.fixbugnft.Repository.ItemRepo;
import com.fixbugnft.fixbugnft.Repository.OfferRepo;
import com.fixbugnft.fixbugnft.Service.OfferService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfferServiceImpl implements OfferService {

    private OfferRepo offerRepository;

    private ItemRepo itemRepository;
//    @Override
//    public List<Offer> getAllOffer() {
//        return offerRepository.getAll();
//    }

    @Override
    public Offer findById(Long id) {
        return offerRepository.getOfferById(id);
    }

    @Override
    public boolean deleteOffer(Long id) {
        Offer offer = offerRepository.getOfferById(id);
        if(offer == null) return false;
        offerRepository.deleteById(id);
        return true;
    }


}

package com.fixbugnft.fixbugnft.Service.Impl;

import com.fixbugnft.fixbugnft.Repository.AuctionListRepository;
import com.fixbugnft.fixbugnft.Service.AuctionListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuctionListServiceImpl implements AuctionListService {
    @Autowired
    AuctionListRepository auctionListRepository;

    public int auctionListService(Long priceAuction,
                                  Long idUser,
                                  Long idAuction) {
        if (auctionListRepository.userAuction(idUser, idAuction) != null) {
            return auctionListRepository.updatePriceUser(priceAuction,
                    idUser, idAuction);
        } else {
            return auctionListRepository.auctionListRepository(priceAuction,
                    idUser, idAuction);
        }
    }

    public Long priceAuctionMaxService(Long idAuction) {
        return auctionListRepository.
                priceAuctionMaxRepository(idAuction);
    }

    public Long emailAuctionMaxService(Long idAuction) {
        return auctionListRepository.
                emailAuctionMaxRepository(idAuction);
    }
}

package com.fixbugnft.fixbugnft.Service.Impl;


import com.fixbugnft.fixbugnft.Model.Auction;
import com.fixbugnft.fixbugnft.Model.User;
import com.fixbugnft.fixbugnft.Repository.AuctionListRepository;
import com.fixbugnft.fixbugnft.Repository.AuctionRepository;
import com.fixbugnft.fixbugnft.Repository.UserRepo;
import com.fixbugnft.fixbugnft.Service.AuctionService;
import com.fixbugnft.fixbugnft.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class AuctionServiceImpl implements AuctionService {
    @Autowired
    AuctionRepository auctionRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    AuctionListRepository auctionListRepository;

    @Autowired
    UserRepo userRepository;


    public int newAuctionService(Long seller, Long item, Long price,
                                 ZonedDateTime startAuctionSession,
                                 ZonedDateTime endAuctionSession) {
        return auctionRepository.newAuctionRepository(seller, item,
                price, startAuctionSession, endAuctionSession);
    }

    public User getUserService(Long idUser) {
        return auctionRepository.getUserRepository(idUser);
    }

    public Auction getAuctionService(Long idAuction) {
        return auctionRepository.getAuctionRepository(idAuction);
    }

    public boolean checkTime(Long idAuction, ZonedDateTime now) {
        Auction auction = auctionRepository.getAuctionRepository(idAuction);
        return now.isAfter(auction.getStartAuctionSession())
                && auction.getEndAuctionSession().isAfter(now);
    }

    @Scheduled(fixedRate = 1000)
    public void sendEmailForUser() {

    }

    @Scheduled(fixedRate = 999)
    public void endAuction() {
        ZonedDateTime now = ZonedDateTime.now().withZoneSameInstant(ZoneOffset.UTC);
        List<Auction> auctions = auctionRepository.endAuctionRepository(now, now.minusSeconds(1));
        for (Auction auction : auctions) {
            if (null != auctionListRepository.priceAuctionMaxRepository(auction.getId())) {
                emailService.sendSeller(userRepository.getEmail(auction.getSeller()));
                emailService.sendBuyer(userRepository.getEmail(
                        auctionListRepository.emailAuctionMaxRepository(auction.getId())));

                // Xử lý ở đây.
            } else {
                emailService.sendSellerNo(userRepository.getEmail(auction.getSeller()));

            }
        }
    }
}

package com.fixbugnft.fixbugnft.Service;

import com.fixbugnft.fixbugnft.Model.Auction;
import com.fixbugnft.fixbugnft.Model.User;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.ZonedDateTime;
import java.util.List;

public interface AuctionService {
    public int newAuctionService(Long seller, Long item, Long price,
                                 ZonedDateTime startAuctionSession,
                                 ZonedDateTime endAuctionSession);

    public User getUserService(Long idUser);

    public Auction getAuctionService(Long idAuction);

    public boolean checkTime(Long idAuction, ZonedDateTime now);

    @Scheduled(fixedRate = 1000)
    public void sendEmailForUser();

    @Scheduled(fixedRate = 999)
    public void endAuction();
}

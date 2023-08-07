package com.fixbugnft.fixbugnft.Repository;


import com.fixbugnft.fixbugnft.Model.Auction;
import com.fixbugnft.fixbugnft.Model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface AuctionRepository extends CrudRepository<Auction, Long> {

    @Transactional
    @Modifying
    @Query(value =
            "INSERT INTO AUCTIONS(seller, item, price, start_auction_session, end_auction_session) VALUES (:seller, :item, :price, :startAuctionSession, :endAuctionSession)",
            nativeQuery = true)
    int newAuctionRepository(@Param("seller") Long seller,
                             @Param("item") Long item,
                             @Param("price") Long price,
                             @Param("startAuctionSession") ZonedDateTime startAuctionSession,
                             @Param("endAuctionSession") ZonedDateTime endAuctionSession);


    @Query(value =
            "SELECT *FROM USERS WHERE id = :id",
            nativeQuery = true)
    User getUserRepository(@Param("id") Long id);

    @Query(value =
            "SELECT *FROM AUCTIONS WHERE id = :id",
            nativeQuery = true)
    Auction getAuctionRepository(@Param("id") Long id);

    @Query(value =
            "SELECT *FROM AUCTIONS WHERE end_auction_session <= :now AND end_auction_session > :nowMinus",
            nativeQuery = true)
    List<Auction> endAuctionRepository(@Param("now") ZonedDateTime now,
                                       @Param("nowMinus") ZonedDateTime nowMinus);



}

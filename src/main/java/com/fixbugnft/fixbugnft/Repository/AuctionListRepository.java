package com.fixbugnft.fixbugnft.Repository;


import com.fixbugnft.fixbugnft.Model.AuctionList;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AuctionListRepository extends CrudRepository<AuctionList, Long> {

    @Transactional
    @Modifying
    @Query(value =
            "INSERT INTO LISTS(price_auction, id_user, auction_id) VALUES (:price, :idUser, :auction)",
            nativeQuery = true)
    int auctionListRepository(@Param("price") Long price,
                              @Param("idUser") Long idUser,
                              @Param("auction") Long auction);

    @Query(value =
            "SELECT * FROM LISTS WHERE id_user = :idUser AND auction_id = :idAuction",
            nativeQuery = true)
    AuctionList userAuction(@Param("idUser") Long idUser,
                            @Param("idAuction") Long idAuction);

    @Transactional
    @Modifying
    @Query(value =
            "UPDATE LISTS SET price_auction = :price WHERE id_user = :idUser AND auction_id = :idAuction",
            nativeQuery = true)
    int updatePriceUser(@Param("price") Long price,
                        @Param("idUser") Long idUser,
                        @Param("idAuction") Long idAuction);

    @Query(value =
            "SELECT * FROM LISTS WHERE auction_id = :idAuction",
            nativeQuery = true)
    List<AuctionList> getUserAuction(@Param("idAuction") Long idAuction);

    @Query(value =
            "SELECT MAX(price_auction) FROM LISTS WHERE auction_id = :idAuction",
            nativeQuery = true)
    Long priceAuctionMaxRepository(@Param("idAuction") Long idAuction);

    @Query(value =
            "SELECT id_user FROM LISTS WHERE auction_id = :idAuction AND price_auction = (SELECT MAX(price_auction) FROM LISTS WHERE auction_id = :idAuction)",
            nativeQuery = true)
    Long emailAuctionMaxRepository(@Param("idAuction") Long idAuction);

}

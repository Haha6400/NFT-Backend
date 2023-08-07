package com.fixbugnft.fixbugnft.Service;

public interface AuctionListService {
    public int auctionListService(Long priceAuction,
                                  Long idUser,
                                  Long idAuction);

    public Long priceAuctionMaxService(Long idAuction);

    public Long emailAuctionMaxService(Long idAuction);

}

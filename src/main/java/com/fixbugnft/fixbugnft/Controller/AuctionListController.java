package com.fixbugnft.fixbugnft.Controller;

import com.fixbugnft.fixbugnft.Service.AuctionListService;
import com.fixbugnft.fixbugnft.Service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;


@RestController
@CrossOrigin
@RequestMapping("/openeye/auctionList")
public class AuctionListController {

    @Autowired
    AuctionListService auctionListService;

    @Autowired
    AuctionService auctionService;

    @PostMapping("/auction")
    public ResponseEntity joinTheAuction(@RequestParam("priceAuction") Long priceAuction,
                                         @RequestParam("idUser") Long idUser,
                                         @RequestParam("idAuction") Long idAuction) {
        if (priceAuction <= auctionService.getAuctionService(idAuction).getPrice()) {
            return new ResponseEntity<>("T.T", HttpStatus.BAD_GATEWAY);
        }
        if (null != auctionListService.priceAuctionMaxService(idAuction)
                && priceAuction <= auctionListService.priceAuctionMaxService(idAuction)) {
            return new ResponseEntity<>("T.T", HttpStatus.BAD_GATEWAY);
        }
        //Tiền phải lớn hơn max.
        if (!auctionService.checkTime(idAuction, ZonedDateTime.now().withZoneSameInstant(ZoneOffset.UTC))) {
            return new ResponseEntity<>("T.T", HttpStatus.BAD_GATEWAY);
        }
        //Trong khoảng thời gian cho phép.
        int ok = auctionListService.auctionListService(priceAuction,
                idUser, idAuction);
        return new ResponseEntity<>("Ok", HttpStatus.OK);
    }


}
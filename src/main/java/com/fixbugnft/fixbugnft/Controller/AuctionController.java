package com.fixbugnft.fixbugnft.Controller;

//import com.example.StringBoot.Model.Auction;
//import com.example.StringBoot.Service.AuctionService;
import com.fixbugnft.fixbugnft.Service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@RestController
@CrossOrigin
@RequestMapping("/openeye/auction")
public class AuctionController {

    @Autowired
    AuctionService auctionService;

    @PostMapping("/new")
    public ResponseEntity auctionController(@RequestParam("seller") Long seller,
                                            @RequestParam("item") Long item,
                                            @RequestParam("price") Long price,
                                            @RequestParam("startAuctionSession") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime startAuctionSession,
                                            @RequestParam("endAuctionSession") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime endAuctionSession) {
        if (startAuctionSession.isAfter(endAuctionSession)
                && ZonedDateTime.now().isAfter(startAuctionSession)
                && price < 0) {
            return new ResponseEntity<>("T.T", HttpStatus.BAD_GATEWAY);
        }
        ZonedDateTime uctStartAuctionSession = startAuctionSession.withZoneSameInstant(ZoneOffset.UTC);
        ZonedDateTime uctEndAuctionSession = endAuctionSession.withZoneSameInstant(ZoneOffset.UTC);
        auctionService.newAuctionService(seller, item, price,
                uctStartAuctionSession, uctEndAuctionSession);
        return new ResponseEntity<>("Ok", HttpStatus.OK);
    }
}
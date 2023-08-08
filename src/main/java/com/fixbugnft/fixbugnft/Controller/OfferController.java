package com.fixbugnft.fixbugnft.Controller;

import com.fixbugnft.fixbugnft.DTO.OfferDto;
import com.fixbugnft.fixbugnft.Model.Item;
import com.fixbugnft.fixbugnft.Model.Offer;
import com.fixbugnft.fixbugnft.Model.Status;
import com.fixbugnft.fixbugnft.Service.ItemService;
import com.fixbugnft.fixbugnft.Service.OfferService;
import com.fixbugnft.fixbugnft.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;

@RestController
@RequestMapping("/openeye/offer")
@Validated
public class OfferController {
    @Autowired
    UserService userService;

    @Autowired
    ItemService itemService;

    @Autowired
    OfferService offerService;

    private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/save/{idItem}")
        public ResponseEntity<?> createOffer(@PathVariable("idItem") Long idItem,
                                             @RequestPart String usernameCustomer,
                                             @RequestPart Double price){
            Item item = itemService.find(idItem);
            if(item.getStatus() == Status.SOLD) return ResponseEntity.badRequest().body("Invalid item in the offer");
            OfferDto offerDto = new OfferDto(new Date(), Status.NEW, item, userService.findById(item.getOwnerId()), userService.getUserByUsername(usernameCustomer));
            Offer offer = new Offer(new Date(), BigDecimal.valueOf(price), Status.NEW, item.getId(), item.getOwnerId(), userService.getUserByUsername(usernameCustomer).getId());
//            Offer offer = new Offer(new Date(), Status.NEW, item, userService.findById(item.getOwnerId()), userService.getUserByUsername(usernameCustomer));
//            item.getOfferList().add(offer);
            item.setStatus(Status.HAS_OFFERS);
            return  ResponseEntity.ok(offer);
    }
//
//    @PostMapping("/{idOffer}/accept")
//    public ResponseEntity<String> acceptOffer(@PathVariable Long idOffer){
//        Offer offer = offerService.findById(idOffer);
//        Item item = offer.getItem();
//        offer.setStatus(Status.DONE);
//        item.getOwner().getCollection().getItemList().remove(item);
//        User oldOwner = userService.addBalance(item.getOwner(), item.getPrice());
//
//        item.setStatus(Status.SOLD);
//        item.setOwner(offer.getCustomer());
//        item.getOwner().getCollection().getItemList().add(item);
//        itemService.saveItem(item, item.getPictureLink());
//
//        User newOwner = userService.deductBalance(item.getOwner(), item.getPrice());
//
//        for (Offer offerInItem : item.getOfferList()){
//            offerInItem.setStatus(Status.CANCEL);
//        }
//        return ResponseEntity.ok("Offer accepted successfully!");
//    }
}

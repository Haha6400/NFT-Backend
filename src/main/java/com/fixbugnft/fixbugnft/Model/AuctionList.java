package com.fixbugnft.fixbugnft.Model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "lists")
@Getter @Setter
public class AuctionList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long priceAuction;

    private Long idUser;

    private Long auctionId;
}
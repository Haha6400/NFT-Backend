package com.fixbugnft.fixbugnft.Model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "auctions")
@Setter @Getter
public class Auction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long seller;

    private Long item;

    private Long price;

    private ZonedDateTime startAuctionSession;

    private ZonedDateTime endAuctionSession;
}
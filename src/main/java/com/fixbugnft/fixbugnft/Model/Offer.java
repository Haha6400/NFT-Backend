package com.fixbugnft.fixbugnft.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter @Setter
//@NoArgsConstructor @AllArgsConstructor
@RequiredArgsConstructor
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date createdDate;

    private BigDecimal price;

    private Status status;

    private Long itemId;

    private Long customerId;

    private Long sellerId;

    public Offer(Date createdDate, BigDecimal price, Status status, Long itemId, Long customerId, Long sellerId) {
        this.createdDate = createdDate;
        this.price = price;
        this.status = status;
        this.itemId = itemId;
        this.customerId = customerId;
        this.sellerId = sellerId;
    }



}

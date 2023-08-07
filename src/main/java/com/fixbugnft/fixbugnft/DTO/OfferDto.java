package com.fixbugnft.fixbugnft.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fixbugnft.fixbugnft.Model.Item;
import com.fixbugnft.fixbugnft.Model.User;
import com.fixbugnft.fixbugnft.Model.Status;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfferDto {
    private Long id;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date createdDate;

    private Status status;

    private Item item;

    private User seller;

    private User customer;

    public OfferDto(Date createdDate, Status status, Item item) {
        this.createdDate = createdDate;
        this.status = status;
        this.item = item;
    }

    public OfferDto(Date createdDate, Status status, Item item, User seller, User customer) {
        this.createdDate = createdDate;
        this.status = status;
        this.item = item;
        this.customer = customer;
        this.seller = seller;
    }
}

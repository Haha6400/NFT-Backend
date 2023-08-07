package com.fixbugnft.fixbugnft.DTO;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fixbugnft.fixbugnft.Model.User;
import com.fixbugnft.fixbugnft.Model.Status;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {
    private Long id;
    private String name;

    private String description;

    private Double price;

    private String category;
    private Date createdDate;

    private Date syncChainTime;

    private Status status;

    private String pictureLink;
    private User owner;

    public ItemDto(String name, String description, Double price, String category, Date createdDate, Status status, User user) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.createdDate = createdDate;
        this.owner = user;
    }
}


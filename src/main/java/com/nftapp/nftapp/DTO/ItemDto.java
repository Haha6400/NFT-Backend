package com.nftapp.nftapp.DTO;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.nftapp.nftapp.Model.User;
import com.nftapp.nftapp.Status;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ItemDto {
    private Long id;
    private String name;

    private String description;

    private Double price;

    private Date createdDate;

    private Date syncChainTime;

    private Status status;

    private String pictureLink;
    private User owner;
}


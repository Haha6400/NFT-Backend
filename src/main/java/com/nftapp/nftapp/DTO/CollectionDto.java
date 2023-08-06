package com.nftapp.nftapp.DTO;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.nftapp.nftapp.Model.Item;
import com.nftapp.nftapp.Model.User;
import com.nftapp.nftapp.Model.Collection;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Data
public class CollectionDto {
    private Long id;
    private String name;

    private String description;

//    private String category;

    private Date createdDate;

    private String slug;

    private String pictureLink;

    private Double volume;

    private User creator;
    private String creatorId;

    private Set<Item> ItemList;

    public CollectionDto(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}

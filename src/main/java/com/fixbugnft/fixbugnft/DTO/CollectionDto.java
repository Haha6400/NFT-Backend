package com.fixbugnft.fixbugnft.DTO;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fixbugnft.fixbugnft.Model.Item;
import com.fixbugnft.fixbugnft.Model.User;
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

    private Date createdDate;

    private String slug;

    private String pictureLink;

    private Double volume;

    private User creator;
    private String creatorId;

    private Set<Item> ItemList;

    public CollectionDto(String name, String description, Date createdDate, Double volume, User creator) {
        this.name = name;
        this.description = description;
        this.createdDate = createdDate;
        this.volume = volume;
        this.creator = creator;
    }

    public CollectionDto(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}

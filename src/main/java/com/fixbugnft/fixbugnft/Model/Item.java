package com.fixbugnft.fixbugnft.Model;


import com.fasterxml.jackson.annotation.JsonIdentityReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false, exclude = "owner")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String name;

    private String category;

    private String description;

    private Double price;

    private Date createdDate;

    private Date syncChainTime;

    private Status status;

    private String pictureLink;

    private Long ownerId;

//    @OneToMany
//    @JoinColumn(name = "offerList_id")
//    private Set<Offer> offerList = new HashSet<>();


}

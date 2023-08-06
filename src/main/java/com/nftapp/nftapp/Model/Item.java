package com.nftapp.nftapp.Model;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.nftapp.nftapp.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Date;

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

    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    private User owner;

    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    private Collection collection;

}

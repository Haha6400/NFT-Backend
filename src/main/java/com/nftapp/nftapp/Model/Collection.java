package com.nftapp.nftapp.Model;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.nftapp.nftapp.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@DynamicInsert(true)
@DynamicUpdate(value = true)
public class Collection implements Serializable {

    @Id
    @Column(length = 32)
    private Long id;

    @NotBlank
    private String name;

    private String description;

    private String category;

    private Date createdDate;

    private Date syncChainTime;

    private String pictureLink;

    private Double volume;

    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    @JoinColumn(name = "creator_id", updatable = false, insertable = false, foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private User creator;

    @Column(name = "creator_id", length = 32)
    private String creatorId;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "collection_id", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private Set<Item> ItemList;
}

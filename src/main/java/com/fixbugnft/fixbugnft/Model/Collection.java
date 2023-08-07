package com.fixbugnft.fixbugnft.Model;


import com.fasterxml.jackson.annotation.JsonIdentityReference;
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

    private Date createdDate;

    private String pictureLink;

    private Double volume;

    private String slug;

    private String creatorName;

    @Column(name = "creator_id", length = 32)
    private String creatorId;

}

package com.nftapp.nftapp.Model;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignRequest {
    private String signature;
    private String address;
}

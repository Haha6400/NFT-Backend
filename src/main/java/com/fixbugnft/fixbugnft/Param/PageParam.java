package com.fixbugnft.fixbugnft.Param;

import jakarta.validation.constraints.DecimalMin;
import lombok.Data;

@Data
public class PageParam {
    @DecimalMin(value = "1", inclusive = true)
    private Integer pageNum;

    @DecimalMin(value = "1", inclusive = true)
    private Integer pageSize;

    private String propertie;

    private String direction;
}

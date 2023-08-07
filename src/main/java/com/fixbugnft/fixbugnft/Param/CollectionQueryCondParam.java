package com.fixbugnft.fixbugnft.Param;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.fixbugnft.fixbugnft.Model.Collection;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class CollectionQueryCondParam extends PageParam {

    private String commodityType;

    private String name;

    private String creatorId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date saleTimeStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date saleTimeEnd;

    public Specification<com.fixbugnft.fixbugnft.Model.Collection> buildSpecification() {
        CollectionQueryCondParam param = this;
        Specification<com.fixbugnft.fixbugnft.Model.Collection> specification = new Specification<Collection>() {

            private static final long serialVersionUID = 1L;

            public Predicate toPredicate(Root<Collection> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                predicates.add(builder.equal(root.get("deletedFlag"), false));
                if (StrUtil.isNotEmpty(param.getCommodityType())) {
                    predicates.add(builder.equal(root.get("commodityType"), param.getCommodityType()));
                }
                if (StrUtil.isNotEmpty(param.getName())) {
                    predicates.add(builder.equal(root.get("name"), param.getName()));
                }
                if (StrUtil.isNotEmpty(param.getCreatorId())) {
                    predicates.add(builder.equal(root.get("creatorId"), param.getCreatorId()));
                }
                if (param.getSaleTimeStart() != null) {
                    predicates.add(builder.greaterThanOrEqualTo(root.get("saleTime").as(Date.class),
                            DateUtil.beginOfDay(param.getSaleTimeStart())));
                }
                if (param.getSaleTimeEnd() != null) {
                    predicates.add(builder.lessThanOrEqualTo(root.get("saleTime").as(Date.class),
                            DateUtil.endOfDay(param.getSaleTimeEnd())));
                }
                return predicates.size() > 0 ? builder.and(predicates.toArray(new Predicate[predicates.size()])) : null;
            }
        };
        return specification;
    }

}

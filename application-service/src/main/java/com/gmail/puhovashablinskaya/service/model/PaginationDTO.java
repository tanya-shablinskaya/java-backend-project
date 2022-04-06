package com.gmail.puhovashablinskaya.service.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder(toBuilder = true)
@ToString
@Getter
@EqualsAndHashCode
public class PaginationDTO {

    private PaginationEnum pagination;

    private Integer customizedPage;

    private Integer page;
}

package com.gmail.puhovashablinskaya.service.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
@ToString
@Getter
@EqualsAndHashCode
public class SearchLegalDTO {
    private String name;

    private String unp;

    private String iban;
}

package com.gmail.puhovashablinskaya.service.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CurrencyEnumDTO {
    BYN(933),
    USD(840),
    EUR(978),
    RUB(643);

    Integer currencyCode;
}

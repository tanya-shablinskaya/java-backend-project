package com.gmail.puhovashablinskaya.repository.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CurrencyEnum {
    BYN(933),
    USD(840),
    EUR(978),
    RUB(643);

    Integer currencyCode;
}

package com.gmail.puhovashablinskaya.service;

import com.gmail.puhovashablinskaya.service.model.LegalAddDTO;
import com.gmail.puhovashablinskaya.service.model.LegalDTO;

public interface GetLegalByNotUniqueFields {
    LegalDTO getLegalsByNotUniqueFields(LegalAddDTO legalName);
}

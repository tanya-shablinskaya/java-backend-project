package com.gmail.puhovashablinskaya.service.converters;

import com.gmail.puhovashablinskaya.repository.model.Legal;
import com.gmail.puhovashablinskaya.service.model.LegalAddDTO;
import com.gmail.puhovashablinskaya.service.model.LegalDTO;

public interface LegalConverter {
    LegalDTO convertModelToDTO(Legal legal);

    Legal convertDtoToModel(LegalAddDTO legalDTO);
}

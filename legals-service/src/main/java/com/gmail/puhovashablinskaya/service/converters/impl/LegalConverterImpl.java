package com.gmail.puhovashablinskaya.service.converters.impl;

import com.gmail.puhovashablinskaya.repository.model.Legal;
import com.gmail.puhovashablinskaya.service.converters.LegalConverter;
import com.gmail.puhovashablinskaya.service.model.LegalAddDTO;
import com.gmail.puhovashablinskaya.service.model.LegalDTO;
import com.gmail.puhovashablinskaya.service.model.ResidenceEnum;
import org.springframework.stereotype.Service;

@Service
public class LegalConverterImpl implements LegalConverter {

    @Override
    public LegalDTO convertModelToDTO(Legal legal) {
        LegalDTO.LegalDTOBuilder legalDTOBuilder = LegalDTO.builder()
                .id(legal.getId())
                .name(legal.getName())
                .iban(legal.getIban())
                .unp(Integer.valueOf(legal.getUnp()))
                .countOfEmployees(legal.getEmployeeCount());


        if (legal.getResidence()) {
            legalDTOBuilder.residence(ResidenceEnum.RESIDENT);
        } else {
            legalDTOBuilder.residence(ResidenceEnum.NORESIDENT);
        }

        return legalDTOBuilder.build();
    }

    @Override
    public Legal convertDtoToModel(LegalAddDTO legalDTO) {
        Legal legal = new Legal();
        legal.setName(legalDTO.getName());
        legal.setUnp(String.valueOf(legalDTO.getUnp()));
        legal.setIban(legalDTO.getIban());
        legal.setEmployeeCount(legalDTO.getCountOfEmployees());

        if (legalDTO.getResidence().equals(ResidenceEnum.RESIDENT)) {
            legal.setResidence(true);
        } else {
            legal.setResidence(false);
        }
        return legal;
    }
}

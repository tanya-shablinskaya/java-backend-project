package com.gmail.puhovashablinskaya.service.impl;

import com.gmail.puhovashablinskaya.repository.LegalRepository;
import com.gmail.puhovashablinskaya.repository.model.Legal;
import com.gmail.puhovashablinskaya.service.AddLegalService;
import com.gmail.puhovashablinskaya.service.GetLegalByNotUniqueFields;
import com.gmail.puhovashablinskaya.service.config.MessageConstants;
import com.gmail.puhovashablinskaya.service.converters.LegalConverter;
import com.gmail.puhovashablinskaya.service.exceptions.LegalNotUniqueException;
import com.gmail.puhovashablinskaya.service.model.LegalAddDTO;
import com.gmail.puhovashablinskaya.service.model.LegalDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class AddLegalServiceImpl implements AddLegalService {
    private final LegalRepository legalRepository;
    private final LegalConverter legalConverter;
    private final GetLegalByNotUniqueFields getLegalByName;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public LegalDTO addLegal(LegalAddDTO legalDTO) {
        isLegalUnique(legalDTO);
        Legal legal = legalConverter.convertDtoToModel(legalDTO);
        legal.setDateOfCreate(LocalDate.now());
        legalRepository.add(legal);
        return legalConverter.convertModelToDTO(legal);
    }

    private Boolean isLegalUnique(LegalAddDTO legalDTO) {
        LegalDTO legalByFields = getLegalByName.getLegalsByNotUniqueFields(legalDTO);
        if (legalByFields != null) {
            String message = String.format(MessageConstants.LEGAL_IS_EXIST_MESSAGE,
                    legalByFields.getName(),
                    legalByFields.getUnp(),
                    legalByFields.getIban());
            throw new LegalNotUniqueException(message);
        }
        return true;
    }

}

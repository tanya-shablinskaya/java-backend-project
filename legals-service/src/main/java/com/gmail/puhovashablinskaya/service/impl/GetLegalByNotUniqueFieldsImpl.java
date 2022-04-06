package com.gmail.puhovashablinskaya.service.impl;

import com.gmail.puhovashablinskaya.repository.LegalRepository;
import com.gmail.puhovashablinskaya.repository.model.Legal;
import com.gmail.puhovashablinskaya.service.GetLegalByNotUniqueFields;
import com.gmail.puhovashablinskaya.service.converters.LegalConverter;
import com.gmail.puhovashablinskaya.service.model.LegalAddDTO;
import com.gmail.puhovashablinskaya.service.model.LegalDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GetLegalByNotUniqueFieldsImpl implements GetLegalByNotUniqueFields {
    private final LegalRepository legalRepository;
    private final LegalConverter legalConverter;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public LegalDTO getLegalsByNotUniqueFields(LegalAddDTO legalAddDTO) {
        Legal legal = legalConverter.convertDtoToModel(legalAddDTO);
        Optional<Legal> resultLegal = legalRepository.getByNameUnpIban(legal);
        return resultLegal.map(legalConverter::convertModelToDTO)
                .orElse(null);
    }
}

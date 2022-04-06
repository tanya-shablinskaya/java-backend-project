package com.gmail.puhovashablinskaya.service.impl;

import com.gmail.puhovashablinskaya.repository.LegalRepository;
import com.gmail.puhovashablinskaya.repository.model.Legal;
import com.gmail.puhovashablinskaya.service.GetLegalsByIdService;
import com.gmail.puhovashablinskaya.service.config.MessageConstants;
import com.gmail.puhovashablinskaya.service.converters.LegalConverter;
import com.gmail.puhovashablinskaya.service.exceptions.LegalException;
import com.gmail.puhovashablinskaya.service.model.LegalDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class GetLegalsByIdServiceImpl implements GetLegalsByIdService {
    private final LegalRepository legalRepository;
    private final LegalConverter converter;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public LegalDTO getLegalById(Long id) {
        Legal legal = legalRepository.findById(id);
        if (legal == null) {
            throw new LegalException(MessageConstants.LEGAL_NOT_FOUND_MESSAGE);
        }
        return converter.convertModelToDTO(legal);
    }
}
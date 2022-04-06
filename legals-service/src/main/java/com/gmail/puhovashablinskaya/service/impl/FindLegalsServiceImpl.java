package com.gmail.puhovashablinskaya.service.impl;

import com.gmail.puhovashablinskaya.repository.LegalRepository;
import com.gmail.puhovashablinskaya.repository.model.Legal;
import com.gmail.puhovashablinskaya.service.FindLegalsService;
import com.gmail.puhovashablinskaya.service.config.MessageConstants;
import com.gmail.puhovashablinskaya.service.converters.LegalConverter;
import com.gmail.puhovashablinskaya.service.exceptions.LegalException;
import com.gmail.puhovashablinskaya.service.model.LegalDTO;
import com.gmail.puhovashablinskaya.service.model.SearchLegalDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FindLegalsServiceImpl implements FindLegalsService {
    private final LegalConverter legalConverter;
    private final LegalRepository legalRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public List<LegalDTO> findLegals(SearchLegalDTO legalFindDTO) {
        String name = legalFindDTO.getName();
        String unp = legalFindDTO.getUnp();
        String iban = legalFindDTO.getIban();

        List<Legal> legals = legalRepository.findByLegalInfo(name, iban, unp);

        if (legals.isEmpty()) {
            throw new LegalException(MessageConstants.LEGAL_SEARCH_ERROR);
        }

        return legals.stream()
                .map(legalConverter::convertModelToDTO)
                .collect(Collectors.toList());
    }
}

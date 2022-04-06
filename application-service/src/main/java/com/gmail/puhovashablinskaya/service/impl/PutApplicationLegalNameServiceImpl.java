package com.gmail.puhovashablinskaya.service.impl;

import com.gmail.puhovashablinskaya.repository.ApplicationRepository;
import com.gmail.puhovashablinskaya.repository.feign.request.FindLegalByNameService;
import com.gmail.puhovashablinskaya.repository.model.Application;
import com.gmail.puhovashablinskaya.service.PutApplicationLegalNameService;
import com.gmail.puhovashablinskaya.service.exceptions.LegalException;
import com.gmail.puhovashablinskaya.service.model.ApplicationChangeResultDTO;
import com.gmail.puhovashablinskaya.service.model.LegalDTO;
import com.gmail.puhovashablinskaya.service.util.MessageConstants;
import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class PutApplicationLegalNameServiceImpl implements PutApplicationLegalNameService {
    private final ApplicationRepository applicationRepository;
    private final FindLegalByNameService findLegalByNameService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public ApplicationChangeResultDTO putApplicationLegalNameById(Long id, String name) {
        List<LegalDTO> legals;
        try {
            legals = findLegalByNameService.getLegalsByInfo(name);
        } catch (FeignException ex) {
            throw new LegalException(MessageConstants.LEGAL_NOT_FOUND_EXCEPTION);
        }

        Optional<LegalDTO> legal = legals.stream()
                .filter(legalDTO -> legalDTO.getName().equals(name))
                .findFirst();
        if (legal.isPresent()) {
            Long legalId = legal.get().getId();
            Optional<Application> applicationByIdAndLegalId = applicationRepository.findApplicationByIdAndLegalId(id, legalId);
            if (applicationByIdAndLegalId.isPresent()) {
                String message = String.format(MessageConstants.APPLICATION_BINDING, id, name);
                return ApplicationChangeResultDTO.builder()
                        .legalId(legalId)
                        .message(message)
                        .build();
            } else {
                LocalDateTime updateDate = LocalDateTime.now();
                applicationRepository.updateApplicationSetLegalIdAndUpdateDateById(id, legalId, updateDate);
                String message = String.format(MessageConstants.APPLICATION_REBINDING, id, name);
                return ApplicationChangeResultDTO.builder()
                        .legalId(legalId)
                        .message(message)
                        .build();
            }
        } else {
            throw new LegalException(MessageConstants.LEGAL_NOT_FOUND_EXCEPTION);
        }
    }
}

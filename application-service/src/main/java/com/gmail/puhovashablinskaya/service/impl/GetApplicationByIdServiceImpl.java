package com.gmail.puhovashablinskaya.service.impl;

import com.gmail.puhovashablinskaya.repository.ApplicationRepository;
import com.gmail.puhovashablinskaya.repository.model.Application;
import com.gmail.puhovashablinskaya.service.GetApplicationByIdService;
import com.gmail.puhovashablinskaya.service.converter.ApplicationConverter;
import com.gmail.puhovashablinskaya.service.exceptions.ApplicationException;
import com.gmail.puhovashablinskaya.service.model.ApplicationDTO;
import com.gmail.puhovashablinskaya.service.util.MessageConstants;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class GetApplicationByIdServiceImpl implements GetApplicationByIdService {
    private final ApplicationRepository applicationRepository;
    private final ApplicationConverter applicationConverter;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public ApplicationDTO getApplicationById(Long id) {
        Application applicationById = applicationRepository.getById(id);
        if (applicationById == null) {
            throw new ApplicationException(MessageConstants.APPLICATION_IS_NOT_FOUND);
        }
        return applicationConverter.convertModelToDTO(applicationById);
    }
}

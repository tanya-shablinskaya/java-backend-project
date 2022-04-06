package com.gmail.puhovashablinskaya.service.impl;

import com.gmail.puhovashablinskaya.repository.ApplicationLogRepository;
import com.gmail.puhovashablinskaya.repository.ApplicationRepository;
import com.gmail.puhovashablinskaya.repository.StatusAppRepository;
import com.gmail.puhovashablinskaya.repository.model.Application;
import com.gmail.puhovashablinskaya.repository.model.ApplicationLog;
import com.gmail.puhovashablinskaya.repository.model.StatusApplication;
import com.gmail.puhovashablinskaya.repository.model.StatusApplicationEnum;
import com.gmail.puhovashablinskaya.security.util.ConfigTokenConstants;
import com.gmail.puhovashablinskaya.security.util.JwtUtils;
import com.gmail.puhovashablinskaya.service.PutApplicationStatusService;
import com.gmail.puhovashablinskaya.service.exceptions.StatusChangeException;
import com.gmail.puhovashablinskaya.service.model.MessageDTO;
import com.gmail.puhovashablinskaya.service.model.StatusApplicationEnumDTO;
import com.gmail.puhovashablinskaya.service.util.MessageConstants;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PutApplicationStatusServiceImpl implements PutApplicationStatusService {
    private final ApplicationRepository applicationRepository;
    private final StatusAppRepository statusAppRepository;
    private final ApplicationLogRepository applicationLogRepository;
    private final JwtUtils jwtUtils;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public MessageDTO putApplicationStatusById(Long id, StatusApplicationEnumDTO statusEnumDTO, String jwtRequest) {
        Application application = applicationRepository.getById(id);
        StatusApplication statusFrom = application.getStatus();
        StatusApplicationEnum statusEnum = StatusApplicationEnum.valueOf(statusEnumDTO.name());

        Optional<StatusApplication> statusToOptional = statusAppRepository.getStatusApplicationByName(statusEnum);
        StatusApplication statusTo = new StatusApplication();
        if (statusToOptional.isPresent()) {
            statusTo = statusToOptional.get();
        } else {
            throw new StatusChangeException(MessageConstants.STATUS_EXCEPTION);
        }


        switch (statusEnumDTO) {
            case NEW:
                throw new StatusChangeException(MessageConstants.APPLICATION_STATUS_NOT_CHANGED);
            case IN_PROGRESS:
                if (statusFrom.getName().equals(StatusApplicationEnum.NEW)) {
                    applicationRepository.updateApplicationStatusById(id, statusFrom);
                    addStatusLog(jwtRequest, application, statusFrom, statusTo);
                    return MessageDTO.builder().message(MessageConstants.APPLICATION_STATUS_CHANGED).build();
                }
                break;
            case REJECTED:
                if (statusFrom.getName().equals(StatusApplicationEnum.NEW)) {
                    addStatusLog(jwtRequest, application, statusFrom, statusTo);
                    return MessageDTO.builder().message(MessageConstants.APPLICATION_STATUS_CHANGED).build();
                }
                break;
            case DONE:
                if (statusFrom.getName().equals(StatusApplicationEnum.IN_PROGRESS)) {
                    addStatusLog(jwtRequest, application, statusFrom, statusTo);
                    return MessageDTO.builder().message(MessageConstants.APPLICATION_STATUS_CHANGED).build();
                }
                break;
            default:
                throw new StatusChangeException(MessageConstants.APPLICATION_STATUS_NOT_FOUND);
        }
        throw new StatusChangeException(MessageConstants.APPLICATION_STATUS_NOT_CHANGED);
    }

    private void addStatusLog(String jwtRequest, Application application, StatusApplication statusFrom, StatusApplication statusTo) {
        ApplicationLog applicationLog = new ApplicationLog();
        applicationLog.setStatusFrom(statusFrom);
        applicationLog.setStatusTo(statusTo);
        applicationLog.setApplication(application);
        applicationLog.setCreateDate(LocalDateTime.now());

        String token = parseJwt(jwtRequest);
        Long userIdFromJwtToken = jwtUtils.getUserIdFromJwtToken(token);
        applicationLog.setUserId(userIdFromJwtToken);
        applicationLogRepository.save(applicationLog);
    }

    private String parseJwt(String jwtRequest) {
        if (StringUtils.hasText(jwtRequest)
                && jwtRequest.startsWith(ConfigTokenConstants.TOKEN_TYPE)) {
            return jwtRequest.substring(ConfigTokenConstants.TOKEN_TYPE.length());
        }
        return null;
    }

}

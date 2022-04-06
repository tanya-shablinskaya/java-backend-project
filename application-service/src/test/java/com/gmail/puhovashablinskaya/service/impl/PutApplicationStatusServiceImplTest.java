package com.gmail.puhovashablinskaya.service.impl;

import com.gmail.puhovashablinskaya.repository.ApplicationLogRepository;
import com.gmail.puhovashablinskaya.repository.ApplicationRepository;
import com.gmail.puhovashablinskaya.repository.StatusAppRepository;
import com.gmail.puhovashablinskaya.repository.feign.request.FindLegalByNameService;
import com.gmail.puhovashablinskaya.repository.model.Application;
import com.gmail.puhovashablinskaya.repository.model.StatusApplication;
import com.gmail.puhovashablinskaya.repository.model.StatusApplicationEnum;
import com.gmail.puhovashablinskaya.security.util.JwtUtils;
import com.gmail.puhovashablinskaya.service.exceptions.StatusChangeException;
import com.gmail.puhovashablinskaya.service.model.MessageDTO;
import com.gmail.puhovashablinskaya.service.model.StatusApplicationEnumDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PutApplicationStatusServiceImplTest {
    @InjectMocks
    private PutApplicationStatusServiceImpl putApplicationStatusService;
    @Mock
    private FindLegalByNameService findLegalByNameService;
    @Mock
    private ApplicationRepository applicationRepository;
    @Mock
    private StatusAppRepository statusAppRepository;
    @Mock
    private ApplicationLogRepository applicationLogRepository;
    @Mock
    private JwtUtils jwtUtils;

    @Test
    void shouldReturnValidErrorMessageIfStatusToIsNew() {
        Application application = new Application();
        application.setId(1L);
        application.setLegalId(1L);

        StatusApplication statusApplication = new StatusApplication();
        statusApplication.setId(1L);
        statusApplication.setName(StatusApplicationEnum.NEW);

        when(statusAppRepository.getStatusApplicationByName(StatusApplicationEnum.NEW)).thenReturn(Optional.of(statusApplication));
        when(applicationRepository.getById(1L)).thenReturn(application);

        String jwt = "eyJzdWIiOiJ1c2VybmFtZSIsInVzZXJJZCI6MTIsImNoZWNrVXNlcklkIjpmYWxzZSwiaWF0IjoxNjQ3NzA5MDcyLCJleHAiOjE2NzkyNDUwNzJ9";
        StatusChangeException exception = Assertions.assertThrows(StatusChangeException.class, () -> {
            putApplicationStatusService.putApplicationStatusById(1L, StatusApplicationEnumDTO.NEW, jwt);
        });
        Assertions.assertEquals("Статус не может быть изменен", exception.getMessage());
    }

    @Test
    void shouldReturnValidMessageIfStatusFromIsNewStatusToIsRejected() {
        StatusApplication statusApplication = new StatusApplication();
        statusApplication.setId(1L);
        statusApplication.setName(StatusApplicationEnum.NEW);

        Application application = new Application();
        application.setId(1L);
        application.setLegalId(1L);
        application.setStatus(statusApplication);

        StatusApplication statusApplicationTo = new StatusApplication();
        statusApplicationTo.setId(2L);
        statusApplicationTo.setName(StatusApplicationEnum.REJECTED);

        when(applicationRepository.getById(1L)).thenReturn(application);
        when(statusAppRepository.getStatusApplicationByName(StatusApplicationEnum.REJECTED)).thenReturn(Optional.of(statusApplicationTo));


        String jwt = "eyJzdWIiOiJ1c2VybmFtZSIsInVzZXJJZCI6MTIsImNoZWNrVXNlcklkIjpmYWxzZSwiaWF0IjoxNjQ3NzA5MDcyLCJleHAiOjE2NzkyNDUwNzJ9";

        MessageDTO messageDTO = putApplicationStatusService.putApplicationStatusById(1L, StatusApplicationEnumDTO.REJECTED, jwt);

        Assertions.assertEquals("Статус изменен", messageDTO.getMessage());
    }


    @Test
    void shouldReturnValidMessageIfStatusFromIsNewStatusToIsInProgress() {
        StatusApplication statusApplication = new StatusApplication();
        statusApplication.setId(1L);
        statusApplication.setName(StatusApplicationEnum.NEW);

        Application application = new Application();
        application.setId(1L);
        application.setLegalId(1L);
        application.setStatus(statusApplication);

        StatusApplication statusApplicationTo = new StatusApplication();
        statusApplicationTo.setId(2L);
        statusApplicationTo.setName(StatusApplicationEnum.IN_PROGRESS);

        when(applicationRepository.getById(1L)).thenReturn(application);
        when(statusAppRepository.getStatusApplicationByName(StatusApplicationEnum.IN_PROGRESS)).thenReturn(Optional.of(statusApplicationTo));


        String jwt = "eyJzdWIiOiJ1c2VybmFtZSIsInVzZXJJZCI6MTIsImNoZWNrVXNlcklkIjpmYWxzZSwiaWF0IjoxNjQ3NzA5MDcyLCJleHAiOjE2NzkyNDUwNzJ9";

        MessageDTO messageDTO = putApplicationStatusService.putApplicationStatusById(1L, StatusApplicationEnumDTO.IN_PROGRESS, jwt);

        Assertions.assertEquals("Статус изменен", messageDTO.getMessage());
    }

    @Test
    void shouldReturnValidMessageIfStatusFromIsInProgressStatusToIsDone() {
        StatusApplication statusApplication = new StatusApplication();
        statusApplication.setId(1L);
        statusApplication.setName(StatusApplicationEnum.IN_PROGRESS);

        Application application = new Application();
        application.setId(1L);
        application.setLegalId(1L);
        application.setStatus(statusApplication);

        StatusApplication statusApplicationTo = new StatusApplication();
        statusApplicationTo.setId(2L);
        statusApplicationTo.setName(StatusApplicationEnum.DONE);

        when(applicationRepository.getById(1L)).thenReturn(application);
        when(statusAppRepository.getStatusApplicationByName(StatusApplicationEnum.DONE)).thenReturn(Optional.of(statusApplicationTo));


        String jwt = "eyJzdWIiOiJ1c2VybmFtZSIsInVzZXJJZCI6MTIsImNoZWNrVXNlcklkIjpmYWxzZSwiaWF0IjoxNjQ3NzA5MDcyLCJleHAiOjE2NzkyNDUwNzJ9";

        MessageDTO messageDTO = putApplicationStatusService.putApplicationStatusById(1L, StatusApplicationEnumDTO.DONE, jwt);

        Assertions.assertEquals("Статус изменен", messageDTO.getMessage());
    }

    @Test
    void shouldReturnErrorValidMessageIfStatusFromIsNewStatusToIsDone() {
        StatusApplication statusApplication = new StatusApplication();
        statusApplication.setId(1L);
        statusApplication.setName(StatusApplicationEnum.NEW);

        Application application = new Application();
        application.setId(1L);
        application.setLegalId(1L);
        application.setStatus(statusApplication);

        StatusApplication statusApplicationTo = new StatusApplication();
        statusApplicationTo.setId(2L);
        statusApplicationTo.setName(StatusApplicationEnum.DONE);

        when(applicationRepository.getById(1L)).thenReturn(application);
        when(statusAppRepository.getStatusApplicationByName(StatusApplicationEnum.DONE)).thenReturn(Optional.of(statusApplicationTo));


        String jwt = "eyJzdWIiOiJ1c2VybmFtZSIsInVzZXJJZCI6MTIsImNoZWNrVXNlcklkIjpmYWxzZSwiaWF0IjoxNjQ3NzA5MDcyLCJleHAiOjE2NzkyNDUwNzJ9";

        StatusChangeException exception = Assertions.assertThrows(StatusChangeException.class, () -> {
            putApplicationStatusService.putApplicationStatusById(1L, StatusApplicationEnumDTO.DONE, jwt);
        });
        Assertions.assertEquals("Статус не может быть изменен", exception.getMessage());
    }

    @Test
    void shouldReturnErrorValidMessageIfStatusFromIsRegectedStatusToIsDone() {
        StatusApplication statusApplication = new StatusApplication();
        statusApplication.setId(1L);
        statusApplication.setName(StatusApplicationEnum.REJECTED);

        Application application = new Application();
        application.setId(1L);
        application.setLegalId(1L);
        application.setStatus(statusApplication);

        StatusApplication statusApplicationTo = new StatusApplication();
        statusApplicationTo.setId(2L);
        statusApplicationTo.setName(StatusApplicationEnum.DONE);

        when(applicationRepository.getById(1L)).thenReturn(application);
        when(statusAppRepository.getStatusApplicationByName(StatusApplicationEnum.DONE)).thenReturn(Optional.of(statusApplicationTo));


        String jwt = "eyJzdWIiOiJ1c2VybmFtZSIsInVzZXJJZCI6MTIsImNoZWNrVXNlcklkIjpmYWxzZSwiaWF0IjoxNjQ3NzA5MDcyLCJleHAiOjE2NzkyNDUwNzJ9";

        StatusChangeException exception = Assertions.assertThrows(StatusChangeException.class, () -> {
            putApplicationStatusService.putApplicationStatusById(1L, StatusApplicationEnumDTO.DONE, jwt);
        });
        Assertions.assertEquals("Статус не может быть изменен", exception.getMessage());
    }

}
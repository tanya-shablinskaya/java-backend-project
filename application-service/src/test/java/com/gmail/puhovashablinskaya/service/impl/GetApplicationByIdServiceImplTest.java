package com.gmail.puhovashablinskaya.service.impl;

import com.gmail.puhovashablinskaya.repository.ApplicationRepository;
import com.gmail.puhovashablinskaya.repository.model.Application;
import com.gmail.puhovashablinskaya.service.converter.ApplicationConverter;
import com.gmail.puhovashablinskaya.service.model.ApplicationDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetApplicationByIdServiceImplTest {
    @InjectMocks
    private GetApplicationByIdServiceImpl getApplicationByIdService;
    @Mock
    private ApplicationRepository applicationRepository;
    @Mock
    private ApplicationConverter applicationConverter;

    @Test
    void shouldReturnValidApplication() {
        Application application = new Application();
        application.setId(1L);
        application.setApplicationId("34729964-aec8-11ec-b909-0242ac120002");

        ApplicationDTO applicationDTO = ApplicationDTO.builder()
                .applicationId("34729964-aec8-11ec-b909-0242ac120002")
                .build();
        when(applicationRepository.getById(1L)).thenReturn(application);
        when(applicationConverter.convertModelToDTO(application)).thenReturn(applicationDTO);
        ApplicationDTO applicationById = getApplicationByIdService.getApplicationById(1L);

        Assertions.assertEquals(applicationById, applicationDTO);
    }

    /*@Test
    void shouldThrowExceptionIfApplicationIsNotExist() {
        Application application = new Application();
        application.setId(1L);
        application.setApplicationId("34729964-aec8-11ec-b909-0242ac120002");

        ApplicationDTO applicationDTO = ApplicationDTO.builder()
                .applicationId("34729964-aec8-11ec-b909-0242ac120002")
                .build();
        when(applicationRepository.getById(1L)).thenReturn(null);
        //when(applicationConverter.convertModelToDTO(application)).thenReturn(applicationDTO);
        //ApplicationDTO applicationById = getApplicationByIdService.getApplicationById(1L);

        ApplicationException exception = Assertions.assertThrows(ApplicationException.class, () -> {
            applicationRepository.getById(1L);
        });
        Assertions.assertEquals("some message", exception.getMessage());
    }*/

}
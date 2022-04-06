package com.gmail.puhovashablinskaya.service.impl;

import com.gmail.puhovashablinskaya.repository.ApplicationRepository;
import com.gmail.puhovashablinskaya.repository.feign.request.FindLegalByNameService;
import com.gmail.puhovashablinskaya.repository.model.Application;
import com.gmail.puhovashablinskaya.service.model.ApplicationChangeResultDTO;
import com.gmail.puhovashablinskaya.service.model.LegalDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PutApplicationLegalNameServiceImplTest {
    @InjectMocks
    private PutApplicationLegalNameServiceImpl putApplicationLegalNameService;
    @Mock
    private FindLegalByNameService findLegalByNameService;
    @Mock
    private ApplicationRepository applicationRepository;

    @Test
    void shouldReturnValidMessage() {
        LegalDTO legal = LegalDTO.builder().name("Company").id(1L).build();
        List<LegalDTO> legals = List.of(legal);
        Application application = new Application();
        application.setId(1L);
        application.setLegalId(1L);

        when(findLegalByNameService.getLegalsByInfo("Company")).thenReturn(legals);
        when(applicationRepository.findApplicationByIdAndLegalId(1L, 1L)).thenReturn(Optional.of(application));

        ApplicationChangeResultDTO company = putApplicationLegalNameService.putApplicationLegalNameById(1L, "Company");
        Assertions.assertEquals("Заявка на конверсию 1 привязана к Company", company.getMessage());
    }

    @Test
    void shouldReturnSuccessWhenLegalIsRewrite() {
        LegalDTO legal = LegalDTO.builder().name("Company").id(1L).build();
        List<LegalDTO> legals = List.of(legal);

        when(findLegalByNameService.getLegalsByInfo("Company")).thenReturn(legals);
        when(applicationRepository.findApplicationByIdAndLegalId(1L, 1L)).thenReturn(Optional.empty());

        ApplicationChangeResultDTO company = putApplicationLegalNameService.putApplicationLegalNameById(1L, "Company");
        Assertions.assertEquals("Заявка на конверсию 1 перепривязана к Company", company.getMessage());
    }
}
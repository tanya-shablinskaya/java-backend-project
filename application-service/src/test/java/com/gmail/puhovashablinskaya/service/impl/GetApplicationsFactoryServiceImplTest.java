package com.gmail.puhovashablinskaya.service.impl;

import com.gmail.puhovashablinskaya.service.GetAppService;
import com.gmail.puhovashablinskaya.service.model.ApplicationDTO;
import com.gmail.puhovashablinskaya.service.model.PaginationDTO;
import com.gmail.puhovashablinskaya.service.model.PaginationEnum;
import com.gmail.puhovashablinskaya.service.util.ApplicationConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetApplicationsFactoryServiceImplTest {
    @InjectMocks
    private GetApplicationsFactoryServiceImpl getApplicationsFactoryService;
    @Mock
    private GetAppService getAppService;
    @Mock
    private ApplicationConfig valuesConfig;

    @Test
    void shouldReturnListOfApplicationsWhenPaginationIsDefault() {
        PaginationDTO paginationDTO = PaginationDTO.builder()
                .pagination(PaginationEnum.DEFAULT)
                .page(1)
                .build();

        when(valuesConfig.getDefaultCountOfElementsPage()).thenReturn(10);
        when(getAppService.getApplicationsList(10, 1)).thenReturn(List.of(ApplicationDTO.builder().build()));

        List<ApplicationDTO> applications = getApplicationsFactoryService.getApplications(paginationDTO);

        Assertions.assertFalse(applications.isEmpty());
    }

    @Test
    void shouldReturnListOfApplicationsWhenPaginationIsCustomized() {
        PaginationDTO paginationDTO = PaginationDTO.builder()
                .pagination(PaginationEnum.CUSTOMIZED)
                .page(1)
                .customizedPage(20)
                .build();

        when(getAppService.getApplicationsList(20, 1)).thenReturn(List.of(ApplicationDTO.builder().build()));

        List<ApplicationDTO> applications = getApplicationsFactoryService.getApplications(paginationDTO);

        Assertions.assertFalse(applications.isEmpty());
    }


}
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAppServiceImplTest {
    @InjectMocks
    private GetAppServiceImpl getAppService;
    @Mock
    private ApplicationRepository repository;
    @Mock
    private ApplicationConverter converter;

    @Test
    void shouldReturnEmptyListWhenApplicationsIsNotExist() {
        PageRequest pageable = PageRequest.of(0, 10);
        Page<Application> page = Page.empty();
        when(repository.findAll(pageable)).thenReturn(page);
        List<ApplicationDTO> applicationsList = getAppService.getApplicationsList(10, 1);
        Assertions.assertTrue(applicationsList.isEmpty());
    }

}
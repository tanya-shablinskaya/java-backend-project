package com.gmail.puhovashablinskaya.service.impl;

import com.gmail.puhovashablinskaya.repository.LegalRepository;
import com.gmail.puhovashablinskaya.repository.model.Legal;
import com.gmail.puhovashablinskaya.service.converters.LegalConverter;
import com.gmail.puhovashablinskaya.service.model.LegalDTO;
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
class GetLegalsServiceImplTest {
    @InjectMocks
    private GetLegalsServiceImpl getLegalsService;
    @Mock
    private LegalRepository legalRepository;
    @Mock
    private LegalConverter converter;

    @Test
    void shouldReturnEmptyListWhenNoLegals() {
        PageRequest pageable = PageRequest.of(0, 10);
        Page<Legal> page = Page.empty();
        when(legalRepository.findAll(pageable)).thenReturn(page);
        List<LegalDTO> applicationsList = getLegalsService.getLegalsList(10, 1);
        Assertions.assertTrue(applicationsList.isEmpty());
    }

}
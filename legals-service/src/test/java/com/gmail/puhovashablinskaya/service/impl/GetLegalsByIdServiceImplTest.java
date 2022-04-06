package com.gmail.puhovashablinskaya.service.impl;

import com.gmail.puhovashablinskaya.repository.LegalRepository;
import com.gmail.puhovashablinskaya.repository.model.Legal;
import com.gmail.puhovashablinskaya.service.config.MessageConstants;
import com.gmail.puhovashablinskaya.service.converters.LegalConverter;
import com.gmail.puhovashablinskaya.service.exceptions.LegalException;
import com.gmail.puhovashablinskaya.service.model.LegalDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetLegalsByIdServiceImplTest {
    @InjectMocks
    private GetLegalsByIdServiceImpl getLegalsByIdService;
    @Mock
    private LegalRepository legalRepository;
    @Mock
    private LegalConverter converter;

    @Test
    void shouldReturnLegalById() {
        Legal legal = new Legal();
        legal.setName("One");
        LegalDTO legalDTO = LegalDTO.builder()
                .name("One")
                .build();
        when(legalRepository.findById(1L)).thenReturn(legal);
        when(converter.convertModelToDTO(legal)).thenReturn(legalDTO);

        LegalDTO legalById = getLegalsByIdService.getLegalById(1L);
        Assertions.assertEquals(legalDTO, legalById);
    }

    @Test
    void shouldThrowExceptionWhenLegalByIdNotFound() {
        Legal legal = new Legal();
        legal.setName("One");
        LegalDTO legalDTO = LegalDTO.builder()
                .name("One")
                .build();
        when(legalRepository.findById(1L)).thenReturn(null);

        LegalException exception = Assertions.assertThrows(LegalException.class, () -> {
            getLegalsByIdService.getLegalById(1L);
        });
        Assertions.assertEquals(MessageConstants.LEGAL_NOT_FOUND_MESSAGE, exception.getMessage());
    }
}
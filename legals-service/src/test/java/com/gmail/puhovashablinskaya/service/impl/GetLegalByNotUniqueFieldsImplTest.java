package com.gmail.puhovashablinskaya.service.impl;

import com.gmail.puhovashablinskaya.repository.LegalRepository;
import com.gmail.puhovashablinskaya.repository.model.Legal;
import com.gmail.puhovashablinskaya.service.converters.LegalConverter;
import com.gmail.puhovashablinskaya.service.model.LegalAddDTO;
import com.gmail.puhovashablinskaya.service.model.LegalDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetLegalByNotUniqueFieldsImplTest {
    @InjectMocks
    private GetLegalByNotUniqueFieldsImpl getLegalByNotUniqueFields;
    @Mock
    private LegalRepository legalRepository;
    @Mock
    private LegalConverter legalConverter;

    @Test
    void shouldReturnLegalWhenGet() {
        LegalAddDTO legalDTO = LegalAddDTO.builder()
                .name("Name")
                .unp(11111111)
                .iban("BY12UNBS0000000000000000000000")
                .build();
        LegalDTO legalResultDTO = LegalDTO.builder()
                .name("Name")
                .unp(11111111)
                .iban("BY12UNBS0000000000000000000000")
                .build();

        Legal legal = new Legal();
        legal.setName("Name");
        legal.setUnp("11111111");
        legal.setIban("BY12UNBS0000000000000000000000");

        when(legalConverter.convertDtoToModel(legalDTO)).thenReturn(legal);
        when(legalConverter.convertModelToDTO(legal)).thenReturn(legalResultDTO);
        when(legalRepository.getByNameUnpIban(legal)).thenReturn(Optional.of(legal));
        LegalDTO legalsByNotUniqueFields = getLegalByNotUniqueFields.getLegalsByNotUniqueFields(legalDTO);

        Assertions.assertTrue(legalsByNotUniqueFields != null);
    }
}
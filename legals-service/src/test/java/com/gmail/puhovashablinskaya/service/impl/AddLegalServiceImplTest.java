package com.gmail.puhovashablinskaya.service.impl;

import com.gmail.puhovashablinskaya.repository.LegalRepository;
import com.gmail.puhovashablinskaya.repository.model.Legal;
import com.gmail.puhovashablinskaya.service.GetLegalByNotUniqueFields;
import com.gmail.puhovashablinskaya.service.converters.LegalConverter;
import com.gmail.puhovashablinskaya.service.exceptions.LegalNotUniqueException;
import com.gmail.puhovashablinskaya.service.model.LegalAddDTO;
import com.gmail.puhovashablinskaya.service.model.LegalDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddLegalServiceImplTest {
    @InjectMocks
    private AddLegalServiceImpl addLegalService;
    @Mock
    private LegalRepository legalRepository;
    @Mock
    private LegalConverter legalConverter;
    @Mock
    private GetLegalByNotUniqueFields getLegalByName;

    @Test
    void shouldReturnValidLegalWhenAdd() {
        LegalAddDTO legalDTO = LegalAddDTO.builder()
                .build();
        Legal legal = new Legal();
        LegalDTO result = LegalDTO.builder().build();

        when(getLegalByName.getLegalsByNotUniqueFields(legalDTO)).thenReturn(null);
        when(legalConverter.convertDtoToModel(legalDTO)).thenReturn(legal);
        when(legalConverter.convertModelToDTO(legal)).thenReturn(result);

        LegalDTO resultLegal = addLegalService.addLegal(legalDTO);

        Assertions.assertEquals(result, resultLegal);
    }

    @Test
    void shouldThrowErrorWhenLegalIsNotUnique() {
        LegalAddDTO legalAddDTO = LegalAddDTO.builder()
                .build();
        LegalDTO legalDTO = LegalDTO.builder().id(1L)
                .name("Company")
                .unp(123456789)
                .iban("By12UNBS000000000000000000000000")
                .build();

        when(getLegalByName.getLegalsByNotUniqueFields(legalAddDTO)).thenReturn(legalDTO);


        LegalNotUniqueException exception = Assertions.assertThrows(LegalNotUniqueException.class, () -> {
            addLegalService.addLegal(legalAddDTO);
        });
        Assertions.assertEquals("Компания существует с параметрами Company, 123456789, By12UNBS000000000000000000000000", exception.getMessage());
    }

}
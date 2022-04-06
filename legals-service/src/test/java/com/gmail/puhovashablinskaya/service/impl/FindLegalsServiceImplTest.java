package com.gmail.puhovashablinskaya.service.impl;

import com.gmail.puhovashablinskaya.repository.LegalRepository;
import com.gmail.puhovashablinskaya.repository.model.Legal;
import com.gmail.puhovashablinskaya.service.converters.LegalConverter;
import com.gmail.puhovashablinskaya.service.exceptions.LegalException;
import com.gmail.puhovashablinskaya.service.model.LegalDTO;
import com.gmail.puhovashablinskaya.service.model.SearchLegalDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindLegalsServiceImplTest {
    @InjectMocks
    private FindLegalsServiceImpl findLegalsService;
    @Mock
    private LegalConverter legalConverter;
    @Mock
    private LegalRepository legalRepository;

    @Test
    void shouldReturnListOfLegals() {
        SearchLegalDTO legalFindDTO = SearchLegalDTO.builder()
                .name("One")
                .unp("1111111")
                .iban("By33")
                .build();
        Legal legal = new Legal();
        legal.setName("One");
        List<Legal> legals = List.of(legal);

        when(legalRepository.findByLegalInfo(
                "One",
                "By33",
                "1111111")
        ).thenReturn(legals);

        List<LegalDTO> result = findLegalsService.findLegals(legalFindDTO);

        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    void shouldThrowExceptionWhenLegalsNotFound() {
        SearchLegalDTO legalFindDTO = SearchLegalDTO.builder()
                .name("One")
                .unp("1111111")
                .iban("By33")
                .build();
        Legal legal = new Legal();
        legal.setName("One");

        when(legalRepository.findByLegalInfo(
                "One",
                "By33",
                "1111111")
        ).thenReturn(List.of());


        LegalException exception = Assertions.assertThrows(LegalException.class, () -> {
            findLegalsService.findLegals(legalFindDTO);
        });
        Assertions.assertEquals("Компания не найдена, измените параметры поиска", exception.getMessage());

    }

}
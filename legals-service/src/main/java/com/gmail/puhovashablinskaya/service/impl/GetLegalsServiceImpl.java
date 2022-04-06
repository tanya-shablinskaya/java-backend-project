package com.gmail.puhovashablinskaya.service.impl;

import com.gmail.puhovashablinskaya.repository.LegalRepository;
import com.gmail.puhovashablinskaya.repository.model.Legal;
import com.gmail.puhovashablinskaya.service.GetLegalsService;
import com.gmail.puhovashablinskaya.service.converters.LegalConverter;
import com.gmail.puhovashablinskaya.service.model.LegalDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class GetLegalsServiceImpl implements GetLegalsService {
    private final LegalRepository legalRepository;
    private final LegalConverter converter;
    private static final Integer PAGE_SHIFT = 1;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public List<LegalDTO> getLegalsList(Integer elementCount, Integer page) {
        page = page - PAGE_SHIFT;
        PageRequest pageable = PageRequest.of(page, elementCount);
        Page<Legal> legals = legalRepository.findAll(pageable);
        return legals.stream()
                .map(converter::convertModelToDTO)
                .collect(Collectors.toList());
    }
}

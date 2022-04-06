package com.gmail.puhovashablinskaya.service.impl;

import com.gmail.puhovashablinskaya.repository.ApplicationRepository;
import com.gmail.puhovashablinskaya.repository.model.Application;
import com.gmail.puhovashablinskaya.service.GetAppService;
import com.gmail.puhovashablinskaya.service.converter.ApplicationConverter;
import com.gmail.puhovashablinskaya.service.model.ApplicationDTO;
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
public class GetAppServiceImpl implements GetAppService {
    private final ApplicationRepository repository;
    private final ApplicationConverter converter;
    private static final Integer PAGE_SHIFT = 1;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public List<ApplicationDTO> getApplicationsList(Integer countOfElementsPage, Integer page) {
        page = page - PAGE_SHIFT;
        PageRequest pageable = PageRequest.of(page, countOfElementsPage);
        Page<Application> applications = repository.findAll(pageable);
        return applications.stream()
                .map(converter::convertModelToDTO)
                .collect(Collectors.toList());
    }
}

package com.gmail.puhovashablinskaya.service.impl;

import com.gmail.puhovashablinskaya.service.GetAppService;
import com.gmail.puhovashablinskaya.service.GetApplicationsFactoryService;
import com.gmail.puhovashablinskaya.service.exceptions.PaginationException;
import com.gmail.puhovashablinskaya.service.model.ApplicationDTO;
import com.gmail.puhovashablinskaya.service.model.PaginationDTO;
import com.gmail.puhovashablinskaya.service.util.ApplicationConfig;
import com.gmail.puhovashablinskaya.service.util.MessageConstants;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class GetApplicationsFactoryServiceImpl implements GetApplicationsFactoryService {
    private final GetAppService getAppService;
    private final ApplicationConfig valuesConfig;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public List<ApplicationDTO> getApplications(PaginationDTO paginationDTO) {
        List<ApplicationDTO> resultList = new ArrayList<>();
        if (paginationDTO.getPagination() != null) {
            resultList = findApplications(paginationDTO);
        }
        return resultList;
    }

    private List<ApplicationDTO> findApplications(PaginationDTO pagination) {
        switch (pagination.getPagination()) {
            case DEFAULT:
                return getAppService.getApplicationsList(valuesConfig.getDefaultCountOfElementsPage(), pagination.getPage());
            case CUSTOMIZED:
                return getAppService.getApplicationsList(pagination.getCustomizedPage(), pagination.getPage());
            default:
                throw new PaginationException(MessageConstants.PAGINATION_ERROR_MESSAGE);
        }
    }
}

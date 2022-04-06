package com.gmail.puhovashablinskaya.service.impl;

import com.gmail.puhovashablinskaya.controllers.config.ControllerValuesConfig;
import com.gmail.puhovashablinskaya.service.FindLegalsService;
import com.gmail.puhovashablinskaya.service.GetLegalsFactory;
import com.gmail.puhovashablinskaya.service.GetLegalsService;
import com.gmail.puhovashablinskaya.service.config.MessageConstants;
import com.gmail.puhovashablinskaya.service.exceptions.LegalException;
import com.gmail.puhovashablinskaya.service.exceptions.PaginationException;
import com.gmail.puhovashablinskaya.service.exceptions.SearchInputException;
import com.gmail.puhovashablinskaya.service.model.LegalDTO;
import com.gmail.puhovashablinskaya.service.model.PaginationDTO;
import com.gmail.puhovashablinskaya.service.model.SearchLegalDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class GetLegalsFactoryImpl implements GetLegalsFactory {
    private final GetLegalsService getLegalsService;
    private final ControllerValuesConfig valueConfig;
    private final FindLegalsService findLegalsService;

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public List<LegalDTO> getLegals(PaginationDTO paginationDTO, SearchLegalDTO searchLegalDTO) {
        List<LegalDTO> resultList = new ArrayList<>();
        if (paginationDTO.getPagination() != null) {
            resultList = getLegals(paginationDTO);
        } else {
            if (validateSearch(searchLegalDTO)) {
                resultList = findLegalsService.findLegals(searchLegalDTO);
            }
        }
        if (resultList.isEmpty()) {
            throw new LegalException(MessageConstants.LEGALS_LIST_NOT_FOUND);
        }
        return resultList;
    }

    private boolean validateSearch(SearchLegalDTO searchLegalDTO) {
        String name = searchLegalDTO.getName();
        if (name != null && name.length() < valueConfig.getMinNameSearchLength()) {
            throw new SearchInputException(MessageConstants.SEARCH_INPUT_INVALID);
        }
        String unp = searchLegalDTO.getUnp();
        if (unp != null && unp.length() < valueConfig.getMinUnpSearchLength()) {
            throw new SearchInputException(MessageConstants.SEARCH_INPUT_INVALID);
        }
        String iban = searchLegalDTO.getIban();
        if (iban != null && iban.length() < valueConfig.getMinIbanSearchLength()) {
            throw new SearchInputException(MessageConstants.SEARCH_INPUT_INVALID);
        }
        return true;
    }

    private List<LegalDTO> getLegals(PaginationDTO paginationDTO) {
        switch (paginationDTO.getPagination()) {
            case DEFAULT:
                return getLegalsService.getLegalsList(valueConfig.getDefaultCountOfElements(), paginationDTO.getPage());
            case CUSTOMIZED:
                return getLegalsService.getLegalsList(paginationDTO.getCustomizedPage(), paginationDTO.getPage());
            default:
                throw new PaginationException(MessageConstants.PAGINATION_ERROR_MESSAGE);
        }
    }
}

package com.gmail.puhovashablinskaya.service.impl;

import com.gmail.puhovashablinskaya.controllers.util.config.ValuesConfig;
import com.gmail.puhovashablinskaya.service.FindEmployeesService;
import com.gmail.puhovashablinskaya.service.GetEmployeeDivideService;
import com.gmail.puhovashablinskaya.service.GetEmployeeService;
import com.gmail.puhovashablinskaya.service.config.MessageConstants;
import com.gmail.puhovashablinskaya.service.exception.EmployeeException;
import com.gmail.puhovashablinskaya.service.exception.PaginationException;
import com.gmail.puhovashablinskaya.service.exception.SearchInputException;
import com.gmail.puhovashablinskaya.service.model.EmployeeDTO;
import com.gmail.puhovashablinskaya.service.model.PaginationDTO;
import com.gmail.puhovashablinskaya.service.model.SearchEmployeeDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class GetEmployeeDivideServiceImpl implements GetEmployeeDivideService {
    private final GetEmployeeService getEmployeeService;
    private final ValuesConfig valuesConfig;
    private final FindEmployeesService findEmployeesService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public List<EmployeeDTO> getEmployees(PaginationDTO paginationDTO, SearchEmployeeDTO searchEmployeeDTO) {
        List<EmployeeDTO> resultList = new ArrayList<>();
        if (paginationDTO.getPagination() != null) {
            resultList = getEmployees(paginationDTO);
        } else {
            if (validateSearch(searchEmployeeDTO)) {
                resultList = findEmployeesService.findEmployees(searchEmployeeDTO);
            }
        }
        if (resultList.isEmpty()) {
            throw new EmployeeException(MessageConstants.EMPLOYEE_SEARCH_ERROR);
        }
        return resultList;
    }

    private boolean validateSearch(SearchEmployeeDTO searchEmployeeDTO) {
        String employeeName = searchEmployeeDTO.getEmployeeName();
        if (employeeName != null
                && employeeName.length() < valuesConfig.getMinLengthSearch()
                || employeeName.length() > valuesConfig.getMaxLengthEmployeeName()) {
            throw new SearchInputException(MessageConstants.SEARCH_INPUT_INVALID);
        }
        String legalName = searchEmployeeDTO.getLegalName();
        if (legalName != null
                && legalName.length() < valuesConfig.getMinLengthSearch()
                || legalName.length() > valuesConfig.getMaxLengthLegalName()) {
            throw new SearchInputException(MessageConstants.SEARCH_INPUT_INVALID);
        }
        String unp = searchEmployeeDTO.getUnp();
        if (unp != null
                && unp.length() < valuesConfig.getMinLengthSearch()
                || unp.length() > valuesConfig.getMaxLengthUnp()) {
            throw new SearchInputException(MessageConstants.SEARCH_INPUT_INVALID);
        }
        return true;
    }

    private List<EmployeeDTO> getEmployees(PaginationDTO paginationDTO) {
        switch (paginationDTO.getPagination()) {
            case DEFAULT:
                return getEmployeeService.getEmployeesList(valuesConfig.getDefaultCountOfElementsPage(), paginationDTO.getPage());
            case CUSTOMIZED:
                return getEmployeeService.getEmployeesList(paginationDTO.getCustomizedPage(), paginationDTO.getPage());
            default:
                throw new PaginationException(MessageConstants.PAGINATION_ERROR_MESSAGE);
        }
    }
}

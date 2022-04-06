package com.gmail.puhovashablinskaya.controllers.validators;

import com.gmail.puhovashablinskaya.repository.feign.request.FindEmployeeInfoService;
import com.gmail.puhovashablinskaya.service.model.ApplicationFromFile;
import com.gmail.puhovashablinskaya.service.model.EmployeeDTO;
import com.gmail.puhovashablinskaya.service.util.ApplicationConfig;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Slf4j
@Service
public class ApplicationFromFileValidatorImpl implements ApplicationFromFileValidator {
    private final ApplicationConfig appConfig;
    private final FindEmployeeInfoService findEmployees;

    @Override
    public boolean isValid(ApplicationFromFile applicationFromFile) {
        String applicationId = applicationFromFile.getApplicationId();
        if (applicationId == null || applicationId.isBlank()) {
            return false;
        }
        String legalName = applicationFromFile.getLegalName();
        if (legalName == null || legalName.isBlank()
                || legalName.length() > appConfig.getMaxLengthNameLegal()) {
            return false;
        }
        Long employeeId = applicationFromFile.getEmployeeId();
        if (employeeId == null || employeeId > appConfig.getMaxLengthEmployeeId()) {
            return false;
        }

        EmployeeDTO employeeFromId = findEmployees.getEmployeeById(employeeId);
        String legalFromId = employeeFromId.getLegalName();
        String legalFromFile = applicationFromFile.getLegalName();
        if (legalFromId != null) {
            boolean isWorkEmployeesInCompany = isWorkEmployeesInCompany(legalFromId, legalFromFile);
            if (!isWorkEmployeesInCompany) {
                return false;
            }
            return false;
        }
        String currencyFrom = applicationFromFile.getCurrencyFrom().name();
        if (currencyFrom.isBlank() || currencyFrom.length() != appConfig.getCountOfCharValueCurrency()
                || !currencyFrom.matches(appConfig.getRegexCurrency())) {
            return false;
        }
        String currencyTo = applicationFromFile.getCurrencyTo().name();
        return !currencyTo.isBlank() && currencyTo.length() == appConfig.getCountOfCharValueCurrency()
                && currencyTo.matches(appConfig.getRegexCurrency());
    }

    private boolean isWorkEmployeesInCompany(String legalFromId, String legalFromFile) {
        return legalFromId.equals(legalFromFile);
    }
}

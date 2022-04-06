package com.gmail.puhovashablinskaya.service.converter.impl;

import com.gmail.puhovashablinskaya.repository.CurrencyRepository;
import com.gmail.puhovashablinskaya.repository.StatusAppRepository;
import com.gmail.puhovashablinskaya.repository.feign.request.FindEmployeeInfoService;
import com.gmail.puhovashablinskaya.repository.feign.request.FindLegalByIdService;
import com.gmail.puhovashablinskaya.repository.feign.request.FindLegalByNameService;
import com.gmail.puhovashablinskaya.repository.model.*;
import com.gmail.puhovashablinskaya.service.converter.ApplicationConverter;
import com.gmail.puhovashablinskaya.service.exceptions.ApplicationException;
import com.gmail.puhovashablinskaya.service.exceptions.LegalException;
import com.gmail.puhovashablinskaya.service.model.*;
import com.gmail.puhovashablinskaya.service.util.MessageConstants;
import com.gmail.puhovashablinskaya.util.DateTimeService;
import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class ApplicationConverterImpl implements ApplicationConverter {
    private final DateTimeService dataTimeService;
    private final FindLegalByNameService findLegalService;
    private final FindLegalByIdService findLegalByIdService;
    private final FindEmployeeInfoService employeeService;

    private final CurrencyRepository currencyRepository;
    private final StatusAppRepository statusRepository;

    @Override
    public ApplicationDTO convertModelToDTO(Application application) {
        StatusApplication status = application.getStatus();
        Currency currencyFrom = application.getCurrencyFrom();
        Currency currencyTo = application.getCurrencyTo();
        Long legalId = application.getLegalId();

        return ApplicationDTO.builder()
                .applicationId(application.getApplicationId())
                .status(findStatus(status))
                .employeeId(application.getEmployeeId())
                .percent(application.getPercent())
                .currencyFrom(findCurrency(currencyFrom))
                .currencyTo(findCurrency(currencyTo))
                .legalName(findLegalName(legalId))
                .employeeName(findNameEmployeeById(application.getEmployeeId()))
                .build();
    }

    private String findNameEmployeeById(Long employeeId) {
        EmployeeDTO employeeById = employeeService.getEmployeeById(employeeId);
        return employeeById.getName();
    }

    private String findLegalName(Long legalId) {
        LegalDTO legalById = findLegalByIdService.getLegalById(legalId);
        return legalById.getName();
    }

    @Override
    public Application convertAppFromFileToModel(ApplicationFromFile applicationDTO) {
        Application application = new Application();
        application.setApplicationId(applicationDTO.getApplicationId());
        application.setEmployeeId(applicationDTO.getEmployeeId());
        application.setPercent(applicationDTO.getPercent());
        application.setNote(applicationDTO.getNote());
        Optional<Currency> currencyFrom = generateCurrency(applicationDTO.getCurrencyFrom());
        currencyFrom.ifPresent(application::setCurrencyFrom);
        Optional<Currency> currencyTo = generateCurrency(applicationDTO.getCurrencyTo());
        currencyTo.ifPresent(application::setCurrencyTo);

        application.setStatus(createStatusNew());
        application.setCreateDate(dataTimeService.currentTimeDate());

        String legalName = applicationDTO.getLegalName();
        List<LegalDTO> legals;
        try {
            legals = findLegalService.getLegalsByInfo(legalName);
        } catch (FeignException ex) {
            throw new LegalException(MessageConstants.LEGAL_NOT_FOUND_EXCEPTION);
        }

        legals.stream()
                .filter(legalDTO -> legalDTO.getName().equals(legalName))
                .findFirst()
                .ifPresent(legalDTO -> application.setLegalId(legalDTO.getId()));

        return application;
    }

    private StatusApplication createStatusNew() {
        StatusApplicationEnum statusNew = StatusApplicationEnum.NEW;
        return generateStatusApp(statusNew);
    }

    private StatusApplication generateStatusApp(StatusApplicationEnum statusEnum) {
        Optional<StatusApplication> statusApp = statusRepository.getStatusApplicationByName(statusEnum);
        return statusApp.orElseThrow(() -> new ApplicationException(MessageConstants.STATUS_EXCEPTION));
    }

    private Optional<Currency> generateCurrency(CurrencyEnumDTO currency) {
        String stringCurrency = currency.name();
        CurrencyEnum currencyEnum = CurrencyEnum.valueOf(stringCurrency);
        return currencyRepository.findCurrencyByName(currencyEnum);
    }

    private StatusApplicationEnumDTO findStatus(StatusApplication status) {
        String nameString = status.getName().name();
        return StatusApplicationEnumDTO.valueOf(nameString);
    }

    private CurrencyEnumDTO findCurrency(Currency currency) {
        String nameCurrency = currency.getName().name();
        return CurrencyEnumDTO.valueOf(nameCurrency);
    }
}
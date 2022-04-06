package com.gmail.puhovashablinskaya.service.impl;

import com.gmail.puhovashablinskaya.controllers.validators.ApplicationFromFileValidator;
import com.gmail.puhovashablinskaya.repository.ApplicationRepository;
import com.gmail.puhovashablinskaya.repository.model.Application;
import com.gmail.puhovashablinskaya.service.FileService;
import com.gmail.puhovashablinskaya.service.converter.ApplicationConverter;
import com.gmail.puhovashablinskaya.service.exceptions.AppDuplicateException;
import com.gmail.puhovashablinskaya.service.exceptions.ApplicationException;
import com.gmail.puhovashablinskaya.service.model.ApplicationFromFile;
import com.gmail.puhovashablinskaya.service.model.CurrencyEnumDTO;
import com.gmail.puhovashablinskaya.service.util.ApplicationConfig;
import com.gmail.puhovashablinskaya.service.util.MessageConstants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class FileServiceImpl implements FileService {
    private final ApplicationRepository applicationRepository;
    private final ApplicationConverter converter;
    private final ApplicationFromFileValidator validator;
    private final ApplicationConfig config;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public ResponseEntity<String> process(MultipartFile file) {
        List<ApplicationFromFile> fileApplication = parseFileApplicationCsv(file);
        List<Boolean> collect = fileApplication.stream()
                .map(validator::isValid)
                .filter(item -> !item)
                .collect(Collectors.toList());
        if (collect.isEmpty()) {
            return ResponseEntity.badRequest().body(MessageConstants.INVALID_APPLICATION_DATA_MESSAGE);
        }
        Set<String> duplicateApplicationId = findDuplicateApplicationId(fileApplication);
        if (duplicateApplicationId.isEmpty()) {
            List<Application> applications = fileApplication.stream()
                    .map(converter::convertAppFromFileToModel)
                    .collect(Collectors.toList());
            applicationRepository.saveAll(applications);
            return ResponseEntity.ok(MessageConstants.SUCCESS_ADD_APPLICATIONS);
        }
        throw new AppDuplicateException(MessageConstants.ERROR_FILE_DUPLICATES + duplicateApplicationId);
    }


    private List<ApplicationFromFile> parseFileApplicationCsv(MultipartFile file) {
        byte[] fileBytes;
        try {
            fileBytes = file.getBytes();
        } catch (IOException e) {
            throw new ApplicationException(MessageConstants.INVALID_FILE_MESSAGE);
        }
        String fileString = new String(fileBytes);
        List<String> splitLines = List.of(fileString.split(config.getRegexNewLine()));
        try {
            return splitLines.stream()
                    .map(line -> line.split(config.getRegexSplited()))
                    .map(split -> new ArrayList<>(List.of(split)))
                    .map(columnListFile -> ApplicationFromFile.builder()
                            .applicationId(columnListFile.get(0))
                            .currencyFrom(CurrencyEnumDTO.valueOf(columnListFile.get(1)))
                            .currencyTo(CurrencyEnumDTO.valueOf(columnListFile.get(2)))
                            .employeeId(Long.parseLong(columnListFile.get(3)))
                            .percent(Float.parseFloat(columnListFile.get(4)))
                            .legalName(columnListFile.get(5))
                            .note(columnListFile.get(6))
                            .build())
                    .collect(Collectors.toList());
        } catch (NumberFormatException e) {
            throw new AppDuplicateException(MessageConstants.FILE_DATA_EXCEPTION);
        }
    }

    private Set<String> findDuplicateApplicationId(List<ApplicationFromFile> applications) {
        List<String> applicationsId = applications.stream()
                .map(ApplicationFromFile::getApplicationId)
                .collect(Collectors.toList());

        Set<String> duplicateApplicationsId = new HashSet<>();
        return applicationsId.stream()
                .filter(applicationId -> !duplicateApplicationsId.add(applicationId))
                .collect(Collectors.toSet());
    }
}
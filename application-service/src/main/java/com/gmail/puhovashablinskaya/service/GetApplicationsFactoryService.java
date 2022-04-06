package com.gmail.puhovashablinskaya.service;

import com.gmail.puhovashablinskaya.service.model.ApplicationDTO;
import com.gmail.puhovashablinskaya.service.model.PaginationDTO;

import java.util.List;

public interface GetApplicationsFactoryService {
    List<ApplicationDTO> getApplications(PaginationDTO paginationDTO);
}

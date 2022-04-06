package com.gmail.puhovashablinskaya.service;

import com.gmail.puhovashablinskaya.service.model.ApplicationDTO;

import java.util.List;

public interface GetAppService {
    List<ApplicationDTO> getApplicationsList(Integer countOfElementsPage, Integer page);
}

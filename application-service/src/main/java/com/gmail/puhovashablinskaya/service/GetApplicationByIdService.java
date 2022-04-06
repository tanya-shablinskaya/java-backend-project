package com.gmail.puhovashablinskaya.service;

import com.gmail.puhovashablinskaya.service.model.ApplicationDTO;

public interface GetApplicationByIdService {
    ApplicationDTO getApplicationById(Long id);
}

package com.gmail.puhovashablinskaya.service;

import com.gmail.puhovashablinskaya.service.model.ApplicationChangeResultDTO;

public interface PutApplicationLegalNameService {
    ApplicationChangeResultDTO putApplicationLegalNameById(Long id, String name);
}

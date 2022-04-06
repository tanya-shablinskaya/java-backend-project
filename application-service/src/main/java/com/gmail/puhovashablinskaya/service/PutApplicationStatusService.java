package com.gmail.puhovashablinskaya.service;

import com.gmail.puhovashablinskaya.service.model.MessageDTO;
import com.gmail.puhovashablinskaya.service.model.StatusApplicationEnumDTO;

public interface PutApplicationStatusService {
    MessageDTO putApplicationStatusById(Long id, StatusApplicationEnumDTO statusApplicationEnumDTO, String jwt);
}

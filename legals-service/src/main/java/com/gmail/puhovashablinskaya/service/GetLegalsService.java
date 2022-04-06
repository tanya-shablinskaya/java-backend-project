package com.gmail.puhovashablinskaya.service;

import com.gmail.puhovashablinskaya.service.model.LegalDTO;

import java.util.List;

public interface GetLegalsService {
    List<LegalDTO> getLegalsList(Integer elementCount, Integer page);
}

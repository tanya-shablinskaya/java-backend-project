package com.gmail.puhovashablinskaya.service;

import com.gmail.puhovashablinskaya.service.model.LegalDTO;
import com.gmail.puhovashablinskaya.service.model.SearchLegalDTO;

import java.util.List;

public interface FindLegalsService {
    List<LegalDTO> findLegals(SearchLegalDTO legalFindDTO);
}

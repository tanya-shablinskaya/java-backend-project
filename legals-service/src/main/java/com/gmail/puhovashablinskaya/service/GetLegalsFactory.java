package com.gmail.puhovashablinskaya.service;

import com.gmail.puhovashablinskaya.service.model.LegalDTO;
import com.gmail.puhovashablinskaya.service.model.PaginationDTO;
import com.gmail.puhovashablinskaya.service.model.SearchLegalDTO;

import java.util.List;

public interface GetLegalsFactory {
    List<LegalDTO> getLegals(PaginationDTO paginationDTO, SearchLegalDTO searchLegalDTO);
}

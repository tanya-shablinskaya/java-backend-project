package com.gmail.puhovashablinskaya.service.converter;

import com.gmail.puhovashablinskaya.repository.model.Application;
import com.gmail.puhovashablinskaya.service.model.ApplicationDTO;
import com.gmail.puhovashablinskaya.service.model.ApplicationFromFile;

public interface ApplicationConverter {
    ApplicationDTO convertModelToDTO(Application application);

    Application convertAppFromFileToModel(ApplicationFromFile applicationDTO);

}

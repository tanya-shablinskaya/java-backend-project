package com.gmail.puhovashablinskaya.service.convert;

import com.gmail.puhovashablinskaya.repository.model.Session;
import com.gmail.puhovashablinskaya.service.model.SessionDTO;

public interface ConvertSession {

    Session convertToSession(SessionDTO sessionDTO);

    SessionDTO convertToDTO(Session session);

}

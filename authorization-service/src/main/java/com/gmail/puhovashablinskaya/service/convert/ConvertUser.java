package com.gmail.puhovashablinskaya.service.convert;

import com.gmail.puhovashablinskaya.repository.model.Employee;
import com.gmail.puhovashablinskaya.service.model.UserAddDTO;
import com.gmail.puhovashablinskaya.service.model.UserDTO;
import com.gmail.puhovashablinskaya.service.model.UserResponseDTO;

public interface ConvertUser {

    Employee convertToUser(UserAddDTO userAddDTO);

    UserDTO convertToDTO(Employee user);

    UserResponseDTO convertToResponseDTO(Employee user);

    Employee convertToUser(UserDTO userDTO);
}

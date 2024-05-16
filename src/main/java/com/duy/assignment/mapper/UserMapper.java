package com.duy.assignment.mapper;

import com.duy.assignment.dto.UserDTO;
import com.duy.assignment.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    UserDTO toDTO(User entity);

    User toEntity(UserDTO dto);

    List<UserDTO> toDTOs(List<User> users);

    List<User> toEntities(List<UserDTO> userDTOs);
}

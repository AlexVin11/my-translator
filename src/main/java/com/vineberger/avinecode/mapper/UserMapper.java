package com.vineberger.avinecode.mapper;

import com.vineberger.avinecode.dto.UserDTO.UserCreateDTO;
import com.vineberger.avinecode.dto.UserDTO.UserDTO;
import com.vineberger.avinecode.dto.UserDTO.UserUpdateDTO;
import com.vineberger.avinecode.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        uses = {JsonNullableMapper.class, ReferenceMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class UserMapper {
    public abstract User map(UserCreateDTO dto);
    public abstract UserDTO map(User model);
    public abstract User map(UserDTO dto);
    public abstract void update(UserUpdateDTO dto, @MappingTarget User model);
}

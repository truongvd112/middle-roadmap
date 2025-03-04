package com.example.middle_roadmap.utils.converters.mapper;
import com.example.middle_roadmap.dto.user.UserDto;
import com.example.middle_roadmap.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<User, UserDto> {
}

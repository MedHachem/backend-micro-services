package mapper;

import dto.userDTO;
import entity.user;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface userMapper {
    userMapper INSTANCE = Mappers.getMapper(userMapper.class);

    userDTO toDTO(user user);

    user toEntity(userDTO userDTO);
}

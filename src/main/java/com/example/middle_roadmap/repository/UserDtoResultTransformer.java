package com.example.middle_roadmap.repository;

import com.example.middle_roadmap.dto.device.DeviceDto;
import com.example.middle_roadmap.dto.role.RoleDto;
import com.example.middle_roadmap.dto.user.UserDto;
import org.hibernate.transform.ResultTransformer;

import java.util.*;

public class UserDtoResultTransformer implements ResultTransformer {
    private final Map<Long, UserDto> userDTOMap = new LinkedHashMap<>();
    @Override
    public UserDto transformTuple(Object[] tuple, String[] aliases) {
        Map<String, Integer> aliasToIndexMap = aliasToIndexMap(aliases);

        Long userId = Long.parseLong(String.valueOf(tuple[aliasToIndexMap.get("id")]));

        if(userDTOMap.containsKey(userId)) {
            UserDto userDTO = userDTOMap.get(userId);
            addDevice(userDTO, tuple, aliasToIndexMap);
            return userDTO;
        } else {
            return init(tuple, aliasToIndexMap);
        }
    }

    @Override
    public List<UserDto> transformList(List resultList) {
//        return ResultTransformer.super.transformList(resultList);
        return new ArrayList<>(userDTOMap.values());
    }

    private Map<String, Integer> aliasToIndexMap(String[] aliases) {
        Map<String, Integer> aliasToIndexMap = new LinkedHashMap<>();

        for (int i = 0; i < aliases.length; i++) {
            aliasToIndexMap.put(
                    aliases[i].toLowerCase(Locale.ROOT),
                    i
            );
        }

        return aliasToIndexMap;
    }

    public UserDto init(Object[] tuple, Map<String, Integer> aliasToIndexMap) {
        UserDto userDto = new UserDto();
        userDto.setId((Long) tuple[aliasToIndexMap.get("id")]);
        userDto.setUsername((String) tuple[aliasToIndexMap.get("username")]);
        userDto.setPassword((String) tuple[aliasToIndexMap.get("password")]);
        userDto.setName((String) tuple[aliasToIndexMap.get("name")]);
        userDto.setPhoneNumber((String) tuple[aliasToIndexMap.get("phone_number")]);
        userDto.setEmail((String) tuple[aliasToIndexMap.get("email")]);
        addDevice(userDto, tuple, aliasToIndexMap);
        RoleDto roleDto = new RoleDto();
        roleDto.setId((Long) tuple[aliasToIndexMap.get("role_id")]);
        roleDto.setName((String) tuple[aliasToIndexMap.get("role_name")]);
        userDto.setRole(roleDto);
        userDTOMap.put(userDto.getId(), userDto);
        return userDto;
    }

    void addDevice(UserDto userDto, Object[] tuple, Map<String, Integer> aliasToIndexMap) {
        DeviceDto deviceDto = new DeviceDto();
        deviceDto.setId((Long) tuple[aliasToIndexMap.get("device_id")]);
        deviceDto.setType((String) tuple[aliasToIndexMap.get("device_type")]);
        deviceDto.setName((String) tuple[aliasToIndexMap.get("device_name")]);
        deviceDto.setQuantity((Integer) tuple[aliasToIndexMap.get("device_quantity")]);
        deviceDto.setPrice((Long) tuple[aliasToIndexMap.get("device_price")]);
        if (userDto.getDevices() == null) {
            userDto.setDevices(new ArrayList<>());
        }
        userDto.getDevices().add(deviceDto);
    }
}

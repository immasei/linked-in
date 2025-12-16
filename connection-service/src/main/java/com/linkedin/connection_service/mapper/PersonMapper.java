package com.linkedin.connection_service.mapper;

import com.linkedin.connection_service.dto.PersonDTO;
import com.linkedin.connection_service.entity.Person;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PersonMapper {

    private final ModelMapper modelMapper;

    public PersonDTO toDTO(Person post) {
        return modelMapper.map(post, PersonDTO.class);
    }

    public List<PersonDTO> toDTOs(List<Person> posts) {
        return posts.stream().map(this::toDTO).collect(Collectors.toList());
    }

}

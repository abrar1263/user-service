package com.perfectsquare.common.mapper;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class MapperUtil {


    ModelMapper modelMapper;

    public <S,T> T mapSourceToTargetClass(S source, Class<T> targetClass){
        return modelMapper.map(source,targetClass);
    }

    public <S,T> List<T> mapSourceToTargetClass(List<S> source, Class<T> targetClass){
        return source
                .stream()
                .map(element -> modelMapper.map(element,targetClass))
                .collect(Collectors.toList());
    }

    public <S,T> Set<T> mapSourceToTargetClass(Set<S> source, Class<T> targetClass){
        return source
                .stream()
                .map(element -> modelMapper.map(element,targetClass))
                .collect(Collectors.toSet());
    }


}

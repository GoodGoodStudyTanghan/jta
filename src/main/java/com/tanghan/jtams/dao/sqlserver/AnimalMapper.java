package com.tanghan.jtams.dao.sqlserver;

import com.tanghan.jtams.pojo.Animal;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AnimalMapper {

    List<Animal> queryAnimalList();

    void addAnimal(Animal animal);
}

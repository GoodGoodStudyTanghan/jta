package com.tanghan.jtams.service;

import com.tanghan.jtams.dao.sqlserver.AnimalMapper;
import com.tanghan.jtams.pojo.Animal;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AnimalService {
    @Resource
    private AnimalMapper animalMapper;

    public List<Animal> getAnimalList() {
        return animalMapper.queryAnimalList();
    }

    public void addAnimal(Animal animal) {
        animalMapper.addAnimal(animal);
    }
}

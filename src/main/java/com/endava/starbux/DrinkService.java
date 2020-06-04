package com.endava.starbux;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DrinkService {

    private final DrinkRepository drinkRepository;

    @Cacheable("drinkCache")
    @Transactional(readOnly = true)
    public List<Drink> findAll() {
        return drinkRepository.findAll();
    }

    @Cacheable("drinkCache")
    @Transactional(readOnly = true)
    public Drink findById(long id) {
        return drinkRepository.findById(id)
                .orElseThrow(() -> new DrinkNotFoundException("Drink with id: " + id + " was not found"));
    }

    @CacheEvict(value = "drinkCache", allEntries = true)
    @Transactional
    public void deleteById(long id) {
        drinkRepository.deleteById(id);
    }

    @CacheEvict(value = "drinkCache", allEntries = true)
    @Transactional
    public Drink create(Drink drink) {
        return drinkRepository.save(drink);
    }
}

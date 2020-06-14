package com.endava.starbux;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Service
//@RequiredArgsConstructor
@Slf4j
public class DrinkService {

    @Autowired
    private DrinkRepository drinkRepository;

    public DrinkService() {
    }

    @Cache
//    @Cacheable("drinkCache")
    @Transactional(readOnly = true)
    public Page<Drink> findAll(Pageable pageable) {
        log.debug("Finding all drinks");
        return drinkRepository.findAll(pageable);
    }

    @PostConstruct
    public void init() {

    }

//    @Cacheable("drinkCache")
    @Transactional(readOnly = true)
    public Drink findById(long id) {
        return drinkRepository.findById(id)
                .orElseThrow(() -> new DrinkNotFoundException("Drink with id: " + id + " was not found"));
    }

//    @CacheEvict(value = "drinkCache", allEntries = true)
    @Transactional
    public void deleteById(long id) {
        drinkRepository.deleteById(id);
    }

//    @CacheEvict(value = "drinkCache", allEntries = true)
    @Transactional
    public Drink create(Drink drink) {
        return drinkRepository.save(drink);
    }
}

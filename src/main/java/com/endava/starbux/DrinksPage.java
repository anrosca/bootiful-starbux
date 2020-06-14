package com.endava.starbux;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DrinksPage {
    private List<Drink> content;
    private int page;
    private int size;
    private int totalPages;
    private long totalElements;

    public static DrinksPage from(Page<Drink> page) {
        return new DrinksPage(page.getContent(), page.getNumber(),
                page.getSize(), page.getTotalPages(), page
                .getTotalElements());
    }
}

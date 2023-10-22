package service;

import java.time.LocalDate;

public interface TodoService {

    public Todo findById(Long id);

    public Todo findByDate(LocalDate date);
}

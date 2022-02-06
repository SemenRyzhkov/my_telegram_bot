package com.ryzhkov.telegram.dao;

import com.ryzhkov.telegram.model.Quotes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuotesRepository extends JpaRepository<Quotes, Long> {
}

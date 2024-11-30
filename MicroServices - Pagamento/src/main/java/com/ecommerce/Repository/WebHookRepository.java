package com.ecommerce.Repository;

import com.ecommerce.Model.WebHookPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebHookRepository extends JpaRepository<WebHookPagamento, Long> {
}

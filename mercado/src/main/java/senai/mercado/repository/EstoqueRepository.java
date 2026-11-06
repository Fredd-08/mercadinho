package senai.mercado.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import senai.mercado.entity.Estoque;

@Repository
public interface EstoqueRepository extends JpaRepository<Estoque, Long> {
}

package com.cd2tec.javaTest.calculoFrete.repository;

import com.cd2tec.javaTest.calculoFrete.entity.DadosFrete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FreteRepository extends JpaRepository<DadosFrete,Long> {
}

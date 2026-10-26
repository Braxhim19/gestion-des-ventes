package com.gestionventes.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gestionventes.model.Facture;

public interface FactureRepository extends JpaRepository<Facture, Long> {

    Optional<Facture> findByCommandeIdCmd(Long commandeId);

    List<Facture> findByDateFactBetween(LocalDate start, LocalDate end);

    @Query("SELECT SUM(f.totalFacture) FROM Facture f WHERE f.dateFact = :date")
    BigDecimal getTotalFacturesByDate(@Param("date") LocalDate date);

    @Query("SELECT SUM(f.totalFacture) FROM Facture f WHERE f.dateFact BETWEEN :start AND :end")
    BigDecimal getTotalFacturesByPeriod(@Param("start") LocalDate start, @Param("end") LocalDate end);
}
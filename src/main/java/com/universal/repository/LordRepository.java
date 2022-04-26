package com.universal.repository;

import com.universal.model.entity.Lord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LordRepository extends JpaRepository<Lord, Long> {
    @Query(value = """
    SELECT * FROM lord AS l
    LEFT JOIN planet AS p
    ON l.id = p.lord_id
    ORDER BY l.age ASC
    LIMIT 10;
    """, nativeQuery = true)
    Optional<List<Lord>> findTop10Youngest();
}

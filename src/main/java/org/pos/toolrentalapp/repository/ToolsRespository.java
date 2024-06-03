package org.pos.toolrentalapp.repository;

import org.pos.toolrentalapp.entity.Tool;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ToolsRespository extends JpaRepository<Tool, Integer> {
    Optional<Tool> findByToolCode(String toolCode);
}

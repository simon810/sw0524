package org.pos.toolrentalapp.repository;

import org.pos.toolrentalapp.entity.ToolType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ToolTypeRepository extends JpaRepository<ToolType, Integer> {
    Optional<ToolType> findToolTypeByToolTypeName(String toolTypeName);
}

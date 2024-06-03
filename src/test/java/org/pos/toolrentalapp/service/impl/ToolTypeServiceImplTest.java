package org.pos.toolrentalapp.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.pos.toolrentalapp.repository.ToolTypeRepository;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
class ToolTypeServiceImplTest {

    @Mock
    private  ToolTypeRepository toolTypeRepository;

    @InjectMocks
    private ToolTypeServiceImpl toolTypeService;
    @BeforeEach
    void setUp() {
    }

    @Test
    void insert() {
    }

    @Test
    void findByToolTypeName() {
    }
}
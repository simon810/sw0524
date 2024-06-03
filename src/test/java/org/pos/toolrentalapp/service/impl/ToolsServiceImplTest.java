package org.pos.toolrentalapp.service.impl;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.pos.toolrentalapp.repository.ToolsRespository;
import org.pos.toolrentalapp.service.ToolTypeService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
class ToolsServiceImplTest {

    @Mock
    private  ToolsRespository toolsRespository;

    @Mock
    private  ToolTypeService toolTypeService;

    @InjectMocks
    private ToolsServiceImpl toolsService;
    @Test
    void insert() {
    }

    @Test
    void getAllTools() {
    }

    @Test
    void getToolById() {
    }

    @Test
    void removeToolById() {
    }
}
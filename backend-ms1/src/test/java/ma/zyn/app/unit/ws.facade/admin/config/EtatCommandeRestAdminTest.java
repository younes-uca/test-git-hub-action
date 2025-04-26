package ma.zyn.app.unit.ws.facade.admin.config;

import ma.zyn.app.bean.core.config.EtatCommande;
import ma.zyn.app.service.impl.admin.config.EtatCommandeAdminServiceImpl;
import ma.zyn.app.ws.facade.admin.config.EtatCommandeRestAdmin;
import ma.zyn.app.ws.converter.config.EtatCommandeConverter;
import ma.zyn.app.ws.dto.config.EtatCommandeDto;
import org.aspectj.lang.annotation.Before;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EtatCommandeRestAdminTest {

    private MockMvc mockMvc;

    @Mock
    private EtatCommandeAdminServiceImpl service;
    @Mock
    private EtatCommandeConverter converter;

    @InjectMocks
    private EtatCommandeRestAdmin controller;

    @Before("")
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }


    @Test
    public void itShouldFindAllEtatCommandeTest() throws Exception {
        // Mock the service to return an empty list
        when(service.findAll()).thenReturn(Collections.emptyList());
        when(converter.toDto(Collections.emptyList())).thenReturn(Collections.emptyList());

        // Call the controller method
        ResponseEntity<List<EtatCommandeDto>> result = controller.findAll();

        // Verify the result
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());

        // Response body should be empty list
        List<EtatCommandeDto> responseBody = result.getBody();
        assertNotNull(responseBody);
        assertEquals(0, responseBody.size());
    }

    @Test
    public void itShouldSaveEtatCommandeTest() throws Exception {
        // Mock data
        EtatCommandeDto requestDto = new EtatCommandeDto();
        EtatCommande entity = new EtatCommande();
        EtatCommande saved = new EtatCommande();
        EtatCommandeDto savedDto = new EtatCommandeDto();

        // Mock the converter to return the etatCommande object when converting from DTO
        when(converter.toItem(requestDto)).thenReturn(entity);

        // Mock the service to return the saved client
        when(service.create(entity)).thenReturn(saved);

        // Mock the converter to return the saved etatCommande DTO
        when(converter.toDto(saved)).thenReturn(savedDto);

        // Call the controller method
        ResponseEntity<EtatCommandeDto> result = controller.save(requestDto);

        // Verify the result
        assertEquals(HttpStatus.CREATED, result.getStatusCode());

        // Verify the response body
        EtatCommandeDto responseBody = result.getBody();
        assertNotNull(responseBody);

        // Add assertions to compare the response body with the saved etatCommande DTO
        assertEquals(savedDto.getLibelle(), responseBody.getLibelle());
        assertEquals(savedDto.getCode(), responseBody.getCode());
        assertEquals(savedDto.getStyle(), responseBody.getStyle());
        assertEquals(savedDto.getDescription(), responseBody.getDescription());
    }

}

package ma.zyn.app.unit.ws.facade.admin.commande;

import ma.zyn.app.bean.core.commande.Commande;
import ma.zyn.app.service.impl.admin.commande.CommandeAdminServiceImpl;
import ma.zyn.app.ws.facade.admin.commande.CommandeRestAdmin;
import ma.zyn.app.ws.converter.commande.CommandeConverter;
import ma.zyn.app.ws.dto.commande.CommandeDto;
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
public class CommandeRestAdminTest {

    private MockMvc mockMvc;

    @Mock
    private CommandeAdminServiceImpl service;
    @Mock
    private CommandeConverter converter;

    @InjectMocks
    private CommandeRestAdmin controller;

    @Before("")
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }


    @Test
    public void itShouldFindAllCommandeTest() throws Exception {
        // Mock the service to return an empty list
        when(service.findAll()).thenReturn(Collections.emptyList());
        when(converter.toDto(Collections.emptyList())).thenReturn(Collections.emptyList());

        // Call the controller method
        ResponseEntity<List<CommandeDto>> result = controller.findAll();

        // Verify the result
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());

        // Response body should be empty list
        List<CommandeDto> responseBody = result.getBody();
        assertNotNull(responseBody);
        assertEquals(0, responseBody.size());
    }

    @Test
    public void itShouldSaveCommandeTest() throws Exception {
        // Mock data
        CommandeDto requestDto = new CommandeDto();
        Commande entity = new Commande();
        Commande saved = new Commande();
        CommandeDto savedDto = new CommandeDto();

        // Mock the converter to return the commande object when converting from DTO
        when(converter.toItem(requestDto)).thenReturn(entity);

        // Mock the service to return the saved client
        when(service.create(entity)).thenReturn(saved);

        // Mock the converter to return the saved commande DTO
        when(converter.toDto(saved)).thenReturn(savedDto);

        // Call the controller method
        ResponseEntity<CommandeDto> result = controller.save(requestDto);

        // Verify the result
        assertEquals(HttpStatus.CREATED, result.getStatusCode());

        // Verify the response body
        CommandeDto responseBody = result.getBody();
        assertNotNull(responseBody);

        // Add assertions to compare the response body with the saved commande DTO
        assertEquals(savedDto.getRef(), responseBody.getRef());
        assertEquals(savedDto.getMontant(), responseBody.getMontant());
        assertEquals(savedDto.getDateCommande(), responseBody.getDateCommande());
    }

}

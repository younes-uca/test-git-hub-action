package ma.zyn.app.unit.ws.facade.admin.commande;

import ma.zyn.app.bean.core.commande.CommandeDetail;
import ma.zyn.app.service.impl.admin.commande.CommandeDetailAdminServiceImpl;
import ma.zyn.app.ws.facade.admin.commande.CommandeDetailRestAdmin;
import ma.zyn.app.ws.converter.commande.CommandeDetailConverter;
import ma.zyn.app.ws.dto.commande.CommandeDetailDto;
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
public class CommandeDetailRestAdminTest {

    private MockMvc mockMvc;

    @Mock
    private CommandeDetailAdminServiceImpl service;
    @Mock
    private CommandeDetailConverter converter;

    @InjectMocks
    private CommandeDetailRestAdmin controller;

    @Before("")
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }


    @Test
    public void itShouldFindAllCommandeDetailTest() throws Exception {
        // Mock the service to return an empty list
        when(service.findAll()).thenReturn(Collections.emptyList());
        when(converter.toDto(Collections.emptyList())).thenReturn(Collections.emptyList());

        // Call the controller method
        ResponseEntity<List<CommandeDetailDto>> result = controller.findAll();

        // Verify the result
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());

        // Response body should be empty list
        List<CommandeDetailDto> responseBody = result.getBody();
        assertNotNull(responseBody);
        assertEquals(0, responseBody.size());
    }

    @Test
    public void itShouldSaveCommandeDetailTest() throws Exception {
        // Mock data
        CommandeDetailDto requestDto = new CommandeDetailDto();
        CommandeDetail entity = new CommandeDetail();
        CommandeDetail saved = new CommandeDetail();
        CommandeDetailDto savedDto = new CommandeDetailDto();

        // Mock the converter to return the commandeDetail object when converting from DTO
        when(converter.toItem(requestDto)).thenReturn(entity);

        // Mock the service to return the saved client
        when(service.create(entity)).thenReturn(saved);

        // Mock the converter to return the saved commandeDetail DTO
        when(converter.toDto(saved)).thenReturn(savedDto);

        // Call the controller method
        ResponseEntity<CommandeDetailDto> result = controller.save(requestDto);

        // Verify the result
        assertEquals(HttpStatus.CREATED, result.getStatusCode());

        // Verify the response body
        CommandeDetailDto responseBody = result.getBody();
        assertNotNull(responseBody);

        // Add assertions to compare the response body with the saved commandeDetail DTO
        assertEquals(savedDto.getPrix(), responseBody.getPrix());
        assertEquals(savedDto.getQte(), responseBody.getQte());
    }

}

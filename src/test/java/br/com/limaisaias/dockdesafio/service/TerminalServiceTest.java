package br.com.limaisaias.dockdesafio.service;

import br.com.limaisaias.dockdesafio.exceptions.BusinessException;
import br.com.limaisaias.dockdesafio.model.Terminal;
import br.com.limaisaias.dockdesafio.repository.TerminalRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TerminalServiceTest {

    @InjectMocks
    private TerminalService service;

    @Mock
    private TerminalRepository repository;

    private Terminal terminal;

    @BeforeAll
    public void setup() {
        terminal = fakeModel(44332511, "22569", 7);
    }

    @Test
    @DisplayName("Testing whether to register Terminal")
    void create() throws BusinessException {

        when(repository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        when(repository.save(Mockito.any())).thenReturn(terminal);

        Terminal terminalSalvo = service.create("44332211;123;PWWIN;0;F04A2E4088B;4;8.00b3;0;16777216;PWWIN");

        assertEquals(terminalSalvo.getLogic(), 44332511, "Diferent Logic!");
    }

    @Test
    @DisplayName("Testing if not Must register Terminal, as it already exists")
    void createExistsException() throws BusinessException {

        when(repository.findById(Mockito.anyInt())).thenReturn(Optional.of(terminal));

        assertThrows(BusinessException.class, () -> {
            service.create("44332211;123;PWWIN;0;F04A2E4088B;4;8.00b3;0;16777216;PWWIN");
        });
    }

    @Test
    @DisplayName("Test Find By Logic")
    void findByLogic() {

        when(repository.findById(Mockito.anyInt())).thenReturn(Optional.of(terminal));

        Integer logic = 44332511;

        Optional<Terminal> optional = service.findByLogic(logic);

        assertEquals(optional.get().getLogic(), logic);
    }

    @Test
    @DisplayName("Test Update")
    void update() throws BusinessException {
        Integer logic = 44332511;

        Terminal terminal = fakeModel(logic, "222555", 18);

        when(repository.findById(Mockito.anyInt())).thenReturn(Optional.of(terminal));
        when(repository.save(Mockito.any())).thenReturn(terminal);

        Terminal terminal2 = service.update(logic, terminal);

        assertEquals("222555", terminal2.getSerial(), "Diferent Serial");

    }

    private Terminal fakeModel(Integer logic, String s, int i) {
        return Terminal.builder().logic(logic).serial(s).model("PWWIN").sam(i).ptid("F04A2E4088B").plat(4)
                .version("8.00b3").mxr(0).mxf(16777216).verfm("PWWIN").build();
    }

    @Test
    @DisplayName("Must not update Terminal because Logic does not exist.\n")
    void updateLogicNotExistsException() throws BusinessException {
        when(repository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> {
            service.update(44332511, terminal);
        });

    }


    @Test
    @DisplayName("Testing whether to register Terminal STRING")
    void createString() throws BusinessException {

        when(repository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        when(repository.save(Mockito.any())).thenReturn(terminal);

        Terminal terminalSalvo = service.create("44332211;123;PWWIN;0;F04A2E4088B;4;8.00b3;0;16777216;PWWIN");

        assertEquals(terminalSalvo.getLogic(), 44332511, "Diferent Logic!");
    }
}

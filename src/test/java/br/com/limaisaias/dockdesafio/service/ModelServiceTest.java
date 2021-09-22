package br.com.limaisaias.dockdesafio.service;

import br.com.limaisaias.dockdesafio.exceptions.BusinessException;
import br.com.limaisaias.dockdesafio.model.Model;
import br.com.limaisaias.dockdesafio.repository.ModelRepository;
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
public class ModelServiceTest {

    @InjectMocks
    private ModelService service;

    @Mock
    private ModelRepository repository;

    private Model model;

    @BeforeAll
    public void setup() {
        model = fakeModel(44332511, "22569", 7);
    }

    @Test
    @DisplayName("Testing whether to register Model")
    void create() throws BusinessException {

        when(repository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        when(repository.save(Mockito.any())).thenReturn(model);

        Model modelSalvo = service.create(model);

        assertEquals(modelSalvo.getLogic(), 44332511, "Diferent Logic!");
    }

    @Test
    @DisplayName("Testing if not Must register Model, as it already exists")
    void createExistsException() throws BusinessException {

        when(repository.findById(Mockito.anyInt())).thenReturn(Optional.of(model));

        assertThrows(BusinessException.class, () -> {
            service.create(model);
        });
    }

    @Test
    @DisplayName("Test Find By Logic")
    void findByLogic() {

        when(repository.findById(Mockito.anyInt())).thenReturn(Optional.of(model));

        Integer logic = 44332511;

        Optional<Model> optional = service.findByLogic(logic);

        assertEquals(optional.get().getLogic(), logic);
    }

    @Test
    @DisplayName("Test Update")
    void update() throws BusinessException {
        Integer logic = 44332511;

        Model model = fakeModel(logic, "222555", 18);

        when(repository.findById(Mockito.anyInt())).thenReturn(Optional.of(model));
        when(repository.save(Mockito.any())).thenReturn(model);

        Model model2 = service.update(logic, model);

        assertEquals("222555",model2.getSerial(), "Diferent Serial");

    }

    private Model fakeModel(Integer logic, String s, int i) {
        return Model.builder().logic(logic).serial(s).model("PWWIN").sam(i).ptid("F04A2E4088B").plat(4)
                .version("8.00b3").mxr(0).mxf(16777216).VERFM("PWWIN").build();
    }

    @Test
    @DisplayName("Must not update Model because Logic does not exist.\n")
    void updateLogicNotExistsException() throws BusinessException {
        when(repository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> {
            service.update(44332511, model);
        });

    }
}

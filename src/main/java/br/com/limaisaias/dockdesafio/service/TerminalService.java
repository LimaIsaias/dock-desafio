package br.com.limaisaias.dockdesafio.service;

import br.com.limaisaias.dockdesafio.exceptions.BusinessException;
import br.com.limaisaias.dockdesafio.model.Terminal;
import br.com.limaisaias.dockdesafio.repository.TerminalRepository;
import com.google.gson.Gson;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Service
public class TerminalService {

    @Autowired
    TerminalRepository repository;


    public Optional<Terminal> findByLogic(Integer logic) {
        return repository.findById(logic);
    }
    @Transactional
    public Terminal update(Integer logic, Terminal terminal) throws BusinessException {
        validateTerminal(terminal);
        Optional<Terminal> optional = repository.findById(logic);

        if (optional.isEmpty()) {
            throw new BusinessException("Logic was not found in the database.");
        }

        ModelMapper modelMapper = new ModelMapper();

        Terminal newTerminal = modelMapper.map(terminal, Terminal.class);


        return repository.save(newTerminal);
    }

    @Transactional
    public Terminal create(String stringTerminal) throws BusinessException {
        Terminal terminal = getTerminal(stringTerminal);
        validateTerminal(terminal);
        return save(terminal);
    }

    private void validateTerminal(Terminal terminal) {
        StringBuilder sb = new StringBuilder();
        try (InputStream inputStream = getClass().getResourceAsStream("/schema.json")) {
            for (int ch; (ch = inputStream.read()) != -1; ) {
                sb.append((char) ch);
            }
        } catch (IOException e) {
            throw new RuntimeException("Schema file not found", e);
        }
        JSONObject rawSchema = new JSONObject(sb.toString());
        Schema schema = SchemaLoader.load(rawSchema);
        String jsonInString = new Gson().toJson(terminal);
        schema.validate(new JSONObject(jsonInString)); // throws a ValidationException if this object is invalid
    }

    @Transactional
    private Terminal save(Terminal terminal) {

        Optional<Terminal> optional = repository.findById(terminal.getLogic());

        if (optional.isPresent()) {
            throw new BusinessException("Terminal already registered. ");
        }

        return repository.save(terminal);
    }


    private Terminal getTerminal( String stringTerminal) {

        String[] split = stringTerminal.split(";");

        return Terminal.builder()
                .logic(split[0].isEmpty() ? null : Integer.valueOf(split[0]))
                .serial(split[1].isEmpty() ? null : split[1])
                .model(split[2].isEmpty() ? null : split[2])
                .sam(split[3].isEmpty() ? null : Integer.valueOf(split[3]))
                .ptid(split[4].isEmpty() ? null : split[4])
                .plat(split[5].isEmpty() ? null : Integer.valueOf(split[5]))
                .version(split[6].isEmpty() ? null : split[6])
                .mxr(split[7].isEmpty() ? null : Integer.valueOf(split[7]))
                .mxf(split[8].isEmpty() ? null : Integer.valueOf(split[8]))
                .verfm(split[9].isEmpty() ? null : split[9]).build();

    }

}

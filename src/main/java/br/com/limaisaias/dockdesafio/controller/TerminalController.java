package br.com.limaisaias.dockdesafio.controller;


import br.com.limaisaias.dockdesafio.exceptions.BusinessException;
import br.com.limaisaias.dockdesafio.model.Terminal;
import br.com.limaisaias.dockdesafio.service.TerminalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("v1/terminal")
public class TerminalController {

    @Autowired
    private TerminalService service;

    @PostMapping(consumes = MediaType.TEXT_HTML_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create( @RequestBody  String stringTerminal) throws BusinessException {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(stringTerminal));
    }

    @GetMapping("/{logic}")
    public ResponseEntity<?> findByLogic(@PathVariable Integer logic) {

        Optional<Terminal> optional = service.findByLogic(logic);

        if(optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(optional.get());
    }

    @PutMapping(value = "/{logic}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@PathVariable Integer logic, @Valid @RequestBody Terminal terminal) throws BusinessException {
        return ResponseEntity.ok(service.update(logic, terminal));
    }
}

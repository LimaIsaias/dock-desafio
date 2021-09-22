package br.com.limaisaias.dockdesafio.service;


import br.com.limaisaias.dockdesafio.exceptions.BusinessException;
import br.com.limaisaias.dockdesafio.model.Model;
import br.com.limaisaias.dockdesafio.repository.ModelRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ModelService {

    @Autowired
    ModelRepository repository;

    public Model create(Model model) throws BusinessException {

        Optional<Model> optional = repository.findById(model.getLogic());

        if (optional.isPresent()) {
            throw new BusinessException("Model already registered. ");
        }

        return repository.save(model);
    }

    public Optional<Model> findByLogic(Integer logic) {
        return repository.findById(logic);
    }

    public Model update(Integer logic, Model model) throws BusinessException {

        Optional<Model> optional = repository.findById(logic);

        if (optional.isEmpty()) {
            throw new BusinessException("Logic was not found in the database.");
        }

        ModelMapper modelMapper = new ModelMapper();

        Model newModel = modelMapper.map(model, Model.class);

        return repository.save(newModel);
    }
}

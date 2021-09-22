package br.com.limaisaias.dockdesafio.repository;

import br.com.limaisaias.dockdesafio.model.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelRepository extends JpaRepository<Model, Integer> {
}

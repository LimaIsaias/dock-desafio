package br.com.limaisaias.dockdesafio.repository;

import br.com.limaisaias.dockdesafio.model.Terminal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TerminalRepository extends JpaRepository<Terminal, Integer> {
}

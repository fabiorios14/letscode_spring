package br.com.letscode.spring.repository;

import br.com.letscode.spring.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long>  {
}

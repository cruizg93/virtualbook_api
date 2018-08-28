package com.virtualbook.api.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.virtualbook.api.models.Client;

@Repository("clientRepository")
public interface ClientsRepository extends CrudRepository<Client, Long>{

}

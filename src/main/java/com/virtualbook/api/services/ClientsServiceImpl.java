package com.virtualbook.api.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.virtualbook.api.models.Client;
import com.virtualbook.api.repositories.ClientsRepository;

@Service("clientsService")
public class ClientsServiceImpl implements ClientsService{

	@Autowired
	private ClientsRepository repo;
	
	@Override
	public List<Client> getAllClients() {
		return (List<Client>) repo.findAll();
	}

	@Override
	public Optional<Client> getClientById(Long id){
		if(id == null) {
			throw new NullPointerException("Client id must not be null");
		}
		
		Optional<Client> client = repo.findById(id); 
		if(client.isPresent()) {
			return client;
		}else {
			throw new EntityNotFoundException(String.format("Client con id %s no existe",id));
		}
	}

	@Override
	public Client saveClient(Client client) {
		if(client == null) {
			throw new NullPointerException("Client must not be null");
		}
		return repo.save(client);
	}

	@Override
	public void deleteClient(Client client) {
		Optional<Client> clientAux = getClientById(client.getId()); 
		repo.delete(clientAux.get());
	}

	@Override
	public Client updateClient(Client client) {
		Optional<Client> clientAux = getClientById(client.getId());
		clientAux.get().setAddress(client.getAddress());
		clientAux.get().setEmail(client.getEmail());
		clientAux.get().setName(client.getName());
		clientAux.get().setPhoneNumber(client.getPhoneNumber());
		clientAux.get().setStatus(client.getStatus());
		clientAux.get().setTaxId(client.getTaxId());
		return repo.save(clientAux.get());
	}

}

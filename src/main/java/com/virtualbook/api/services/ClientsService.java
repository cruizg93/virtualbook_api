package com.virtualbook.api.services;

import java.util.List;
import java.util.Optional;

import com.virtualbook.api.models.Client;

public interface ClientsService {

	public List<Client> getAllClients();
	public Optional<Client> getClientById(Long id);
	public Client saveClient(Client client);
	public void deleteClient(Client client);
	public Client updateClient(Client client);
}

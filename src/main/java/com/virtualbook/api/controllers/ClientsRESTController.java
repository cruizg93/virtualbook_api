package com.virtualbook.api.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.virtualbook.api.models.Client;
import com.virtualbook.api.services.ClientsService;

import lombok.extern.slf4j.Slf4j;

@Transactional
@Slf4j
@RestController
@RequestMapping("/api")
public class ClientsRESTController {

	@Autowired
	private ClientsService service;
	
	@GetMapping(value = "/clients")
	public ResponseEntity<List<Client>> getAllClients(){
		log.debug("GetAllClients.BEGIN");
		return new ResponseEntity<List<Client>>(service.getAllClients(),HttpStatus.OK);
	}
	
	@GetMapping(value = "/clients/{id}")
	public ResponseEntity<Client> getClientById(@PathVariable("id") long id){
		log.debug("GetClientById.BEGIN");
		Optional<Client> client = service.getClientById(id);
		return new ResponseEntity<Client>(client.get(),HttpStatus.OK);
	}
	
	@PostMapping(value="/clients")
	public ResponseEntity<Client> saveClient(@RequestBody Client client){
		log.debug("SaveClient.BEGIN");
		return new ResponseEntity<Client>(service.saveClient(client),HttpStatus.OK);
	}
	
	@DeleteMapping(value="/clients/{id}")
	public ResponseEntity<Client> deleteClientById(@PathVariable("id") long id){
		log.debug("DeleteClientById.BEGIN");
		Client client = service.getClientById(id).get();
		service.deleteClient(client);
		return new ResponseEntity<Client>(client,HttpStatus.OK);
	}
	
	@PatchMapping(value = "/clients")
	public ResponseEntity<Client> updateClient(@RequestBody Client client){
		log.debug("UpdateClient.BEGIN");
		Client result = service.updateClient(client);
		return new ResponseEntity<Client>(result,HttpStatus.OK);
	}
}

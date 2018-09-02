package com.virtualbook.api.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.virtualbook.api.models.Client;
import com.virtualbook.api.repositories.ClientsRepository;
import com.virtualbook.api.utils.AppConstants;

@RunWith(SpringJUnit4ClassRunner.class)
public class ClientServiceTest {

	@Mock
	private ClientsRepository clientsRepository;
	
	@InjectMocks
	private ClientsServiceImpl clientsService;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	private Long clientId = 32L;
	private Long clientIdWrong = 1L;
	
	@Test
	public void testGetAllClients() {
		List<Client> clientsList = new ArrayList<Client>();
		clientsList.add(new Client(1L,"Client1","client1@test.com","1234567890","Address1","1111",AppConstants.ACTIVE));
		clientsList.add(new Client(2L,"Client2","client2@test.com","1234567890","Address2","2222",AppConstants.ACTIVE));
		clientsList.add(new Client(3L,"Client3","client3@test.com","1234567890","Address3","3333",AppConstants.ACTIVE));
		when(clientsRepository.findAll()).thenReturn(clientsList);
		
		List<Client> result = clientsService.getAllClients();
		assertEquals(3, result.size());
	}
	
	@Test
	public void testGetClientById() {
		Optional<Client> client = Optional.of(new Client(clientId,"Client1","client1@test.com","1234567890","Address1","1111",AppConstants.ACTIVE));
		when(clientsRepository.findById(clientId)).thenReturn(client);
		
		Client result = clientsService.getClientById(clientId ).get();
		assertNotNull(result);
		assertEquals("Client1", result.getName());
		assertEquals(clientId, result.getId());
	}
	
	@Test(expected=NullPointerException.class)
	public void testGetClientByIdNullPointerException() {
		Optional<Client> result = clientsService.getClientById(null);
		assertNull(result);
	}
	
	@Test(expected=EntityNotFoundException.class)
	public void testGetClientByIdEntityNotFoundException() {
		Optional<Client> result= clientsService.getClientById(clientIdWrong);
	}
	
	@Test
	public void testSaveClient() {
		Optional<Client> client = Optional.of(new Client(clientId ,"Client1","client1@test.com","1234567890","Address1","1111",AppConstants.ACTIVE));
		when(clientsRepository.save(client.get())).thenReturn(client.get());
		
		Client result = clientsService.saveClient(client.get());
		assertNotNull(result);
		assertEquals("Client1", result.getName());
		assertEquals(clientId, result.getId());
	}
	
	@Test(expected=NullPointerException.class)
	public void testSaveClientNullPointerException() {
		Client client = null;
		Client result = clientsService.saveClient(client);
	}
	
	@Test
	public void testUpdateClient() {
		Optional<Client> client = Optional.of(new Client(clientId,"Client1","client1@test.com","1234567890","Address1","1111",AppConstants.ACTIVE));
		when(clientsRepository.findById(clientId)).thenReturn(client);
		Client result = clientsService.getClientById(clientId).get();
		result.setName("updated");
		
		when(clientsRepository.save(result)).thenReturn(result);
		Client saved = clientsService.saveClient(result);
		assertNotNull(saved);
		assertEquals("updated", saved.getName());
		assertEquals(clientId, saved.getId());
	}
	
	@Test
	public void testDeleteClient() {
		Optional<Client> client = Optional.of(new Client(clientId,"Client1","client1@test.com","1234567890","Address1","1111",AppConstants.ACTIVE));
		when(clientsRepository.findById(clientId)).thenReturn(client);
		Client result = clientsService.getClientById(clientId).get();
		clientsService.deleteClient(result);
		verify(clientsRepository,times(1)).delete(result);
	}
}

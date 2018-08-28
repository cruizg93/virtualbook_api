package com.virtualbook.api.controllers;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.virtualbook.api.VirtualbookApiApplication;
import com.virtualbook.api.utils.Globals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = VirtualbookApiApplication.class)
@SpringBootTest
@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ClientsRESTControllerTest {

	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext wac;
	
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	@Test
	public void connectionToGetAllClients() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/clients")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	public void getAllClientsNotNull() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/clients")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());
	}
	
	@Test
	public void getClientById() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/clients/32")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value("32"));
	}
	
	@Test
	public void getClientByIdNotEntityFound() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/clients/10000000")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
	
	@Test
	public void getClientByIdBadRequestException() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/clients/sam")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void saveClient() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.post("/clients")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":\"1\",\"name\":\"client1\",\"status\":\""+Globals.ACTIVE+"\"}")
				.accept(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(jsonPath("$.id").exists())
					.andExpect(jsonPath("$.name").exists())
					.andExpect(jsonPath("$.status").exists())
					.andExpect(jsonPath("$.name").value("client1"))
					.andExpect(jsonPath("$.status").value(Globals.ACTIVE));
	}
	
	@Test
	public void saveClientNoId() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.post("/clients")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"name\":\"clientNoId\",\"status\":\""+Globals.ACTIVE+"\"}")
				.accept(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(jsonPath("$.id").exists())
					.andExpect(jsonPath("$.name").exists())
					.andExpect(jsonPath("$.status").exists())
					.andExpect(jsonPath("$.name").value("clientNoId"))
					.andExpect(jsonPath("$.status").value(Globals.ACTIVE));
	}
	
	@Test
	public void updateClient() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.patch("/clients")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":\"32\",\"name\":\"client1\",\"status\":\""+Globals.ACTIVE+"\"}")
				.accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());
	}
	
	@Test
	public void updateClientNotFoundException() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.patch("/clients")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":\"1\",\"name\":\"client1\",\"status\":\""+Globals.ACTIVE+"\"}")
				.accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isNotFound());
	}

	@Test
	public void deleteClient() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.delete("/clients/32")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());
	}
	
	@Test
	public void deleteClientNotFoundException() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.delete("/clients/1")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isNotFound());
	}
}

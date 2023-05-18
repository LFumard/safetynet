package com.openclassrooms.safetynet.integration;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
//import org.junit.Test;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.openclassrooms.safetynet.SafetynetApplication;

@SpringBootTest(classes = {SafetynetApplication.class})
@AutoConfigureMockMvc
public class FirestationsControllerITest {
private final String ENDPOINT = "/firestation";
	
	@Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;
    
    @Before
    public void setupMockMvc() {
    	
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void firestationsCreation_whenAllCorrectInfos_thenReturnfirestationsCreated() throws Exception {
    	
        this.mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT)
                    .contentType(APPLICATION_JSON)
                    .content("{\"address\": \"NEW address\",\"station\": \"NEW station\"}")
                    .accept(APPLICATION_JSON))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isOk());
    }
    
    @Test
    public void firestationsCreation_whenFirestationsExisted_thenReturnfirestationsNotCreated() throws Exception {
    	
        this.mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT)
                    .contentType(APPLICATION_JSON)
                    .content("{\"address\": \"1509 Culver St\",\"station\": \"3\"}")
                    .accept(APPLICATION_JSON))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isConflict());
    }
    
    @Test
    public void firestationsUpdate_whenAllCorrectInfos_thenReturnfirestationsUpdated() throws Exception {
    	
        this.mockMvc.perform(MockMvcRequestBuilders.put(ENDPOINT)
                    .contentType(APPLICATION_JSON)
                    .content("{\"address\": \"1509 Culver St\",\"station\": \"NEW station\"}")
                    .accept(APPLICATION_JSON))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isOk());
    }
    
    @Test
    public void firestationsUpdate_whenfirestationNotExist_thenReturnfirestationsNotUpdated() throws Exception {
    	
        this.mockMvc.perform(MockMvcRequestBuilders.put(ENDPOINT)
                    .contentType(APPLICATION_JSON)
                    .content("{\"address\": \"address NOT IN DATA\",\"station\": \"station NOT IN DATA\"}")
                    .accept(APPLICATION_JSON))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isConflict());
    }
    
    @Test
    public void firestationsDelete_whenAllCorrectInfos_thenReturnfirestationsDelete() throws Exception {
    	
        this.mockMvc.perform(MockMvcRequestBuilders.delete(ENDPOINT)
                    .contentType(APPLICATION_JSON)
                    .content("{\"address\": \"1509 Culver St\",\"station\": \"3\"}")
                    .accept(APPLICATION_JSON))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isOk());
    }
    
    @Test
    public void firestationsDelete_whenfirestationNotExist_thenReturnfirestationsNotDelete() throws Exception {
    	
        this.mockMvc.perform(MockMvcRequestBuilders.delete(ENDPOINT)
                    .contentType(APPLICATION_JSON)
                    .content("{\"address\": \"address NOT IN DATA\",\"station\": \"station NOT IN DATA\"}")
                    .accept(APPLICATION_JSON))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isConflict());
    }
    
    @Test
    public void getPersonFromStation_whenAllCorrectInfos_thenReturnPersonFromStation() throws Exception {
    	
        this.mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT)
                    .contentType(APPLICATION_JSON)
                    .param("station_number", "4")
                    .accept(APPLICATION_JSON))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isOk())
                    .andExpect(content().string(
                    		"[{\"people\":[{\"firstName\":\"Lily\",\"address\":\"489 Manchester St\",\"phone\":\"841-874-9845\",\"lastName\":\"Cooper\"},{\"firstName\":\"Tony\",\"address\":\"112 Steppes Pl\",\"phone\":\"841-874-6874\",\"lastName\":\"Cooper\"},{\"firstName\":\"Ron\",\"address\":\"112 Steppes Pl\",\"phone\":\"841-874-8888\",\"lastName\":\"Peters\"},{\"firstName\":\"Allison\",\"address\":\"112 Steppes Pl\",\"phone\":\"841-874-9888\",\"lastName\":\"Boyd\"}],\"numberAdult\":4,\"numberChild\":0}]"
                    		));
    }
    
    @Test
    public void getPersonFromStation_whenFirestationNotExist_thenReturnPersonFromStationNotFound() throws Exception {
    	
        this.mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT)
        			.contentType(APPLICATION_JSON)
                    .param("station_number", "Station Number NOT IN DATA")
                    .accept(APPLICATION_JSON))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isNotFound());
    }
    
    @Test
    public void getPhonePersonFromStation_whenAllCorrectInfos_thenReturngetPhonePersonFromStation() throws Exception {
    	
        this.mockMvc.perform(MockMvcRequestBuilders.get("/phoneAlert")
                    .contentType(APPLICATION_JSON)
                    .param("firestation_number", "3")
                    .accept(APPLICATION_JSON))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isOk())
                    .andExpect(content().string(
                    		"[\"841-874-6512\",\"841-874-6513\",\"841-874-6544\",\"841-874-6741\",\"841-874-6874\",\"841-874-8888\",\"841-874-9888\"]"
                    		));
    }
    
    @Test
    public void getPhonePersonFromStation_whenFirestationNotExist_thenReturngetPhonePersonNotFound() throws Exception {
    	
        this.mockMvc.perform(MockMvcRequestBuilders.get("/phoneAlert")
                    .contentType(APPLICATION_JSON)
                    .param("firestation_number", "Station Number NOT IN DATA")
                    .accept(APPLICATION_JSON))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isNotFound());
    }
    
    @Test
    public void getPersonFromAddress_whenAllCorrectInfos_thenReturngetgetPersonFromAddress() throws Exception {
    	
        this.mockMvc.perform(MockMvcRequestBuilders.get("/fire")
                    .contentType(APPLICATION_JSON)
                    .param("address", "112 Steppes Pl")
                    .accept(APPLICATION_JSON))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isOk())
                    .andExpect(content().string(
                    		"[{\"fireAddress\":[{\"lastName\":\"Cooper\",\"phone\":\"841-874-6874\",\"age\":29,\"medications\":[\"hydrapermazol:300mg\",\"dodoxadin:30mg\"],\"allergies\":[\"shellfish\"]},{\"lastName\":\"Peters\",\"phone\":\"841-874-8888\",\"age\":58,\"medications\":[],\"allergies\":[]},{\"lastName\":\"Boyd\",\"phone\":\"841-874-9888\",\"age\":58,\"medications\":[\"aznol:200mg\"],\"allergies\":[\"nillacilan\"]}],\"station\":\"3\"}]"
                    		));
    }
    
    @Test
    public void getPersonFromAddress_whenAddressFirestationNotExist_thenReturngetPersonFromAddressNotFound() throws Exception {
    	
        this.mockMvc.perform(MockMvcRequestBuilders.get("/fire")
                    .contentType(APPLICATION_JSON)
                    .param("address", "address NOT IN DATA")
                    .accept(APPLICATION_JSON))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isNotFound());
    }
}

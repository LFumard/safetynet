package com.openclassrooms.safetynet.integration;

import com.openclassrooms.safetynet.SafetynetApplication;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest(classes = {SafetynetApplication.class})
@AutoConfigureMockMvc
public class PersonControllerITest {

	 	private final String ENDPOINT = "/person";

	    @Autowired
	    private MockMvc mockMvc;

	    @Autowired
	    private WebApplicationContext wac;
	    
	    @Before
	    public void setupMockMvc() {
	        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	    }
	    
	    
	    
	    @Test
	    public void getPersonInfo_whenLastNameExist_thenReturngetPersonInfo() 
	    		throws Exception {
		      this.mockMvc.perform(MockMvcRequestBuilders.get("/personInfo")
		                  .contentType(APPLICATION_JSON)
		                  .param("firstName", "Tessa")
		                  .param("lastName", "Carman")
		                  .accept(APPLICATION_JSON))
		                  .andDo(MockMvcResultHandlers.print())
		                  .andExpect(status().isOk())
		                  .andExpect(jsonPath("$.length()", is(1)))
		                  .andExpect(content().string(
	                              "[{\"lastName\":\"Carman\",\"address\":\"834 Binoc Ave\",\"age\":11,\"email\":\"tenz@email.com\",\"medications\":[],\"allergies\":[]}]"));
	    }
	    
	    @Test
	    public void getPersonInfo_whenLastNameNotExist_thenReturngetPersonNotFound() 
	    		throws Exception {
		      this.mockMvc.perform(MockMvcRequestBuilders.get("/personInfo")
		                  .contentType(APPLICATION_JSON)
		                  .param("firstName", "firstName NOT IN DATA")
		                  .param("lastName", "lastName NOT IN DATA")
		                  .accept(APPLICATION_JSON))
		                  .andDo(MockMvcResultHandlers.print())
		                  .andExpect(status().isNotFound());
	    }
	    
	    @Test
	    public void getChildFromAddress_whenChildFromAddressExist_thenReturngetChildFromAddress() 
	    		throws Exception {
		      this.mockMvc.perform(MockMvcRequestBuilders.get("/childAlert")
		                  .contentType(APPLICATION_JSON)
		                  .param("address", "947 E. Rose Dr"))
		                  .andExpect(status().isOk())
		                  .andExpect(content().string(
	                              "[{\"firstName\":\"Kendrik\",\"lastName\":\"Stelzer\",\"address\":\"947 E. Rose Dr\",\"age\":9,\"foyerAdress\":[{\"firstName\":\"Brian\",\"lastName\":\"Stelzer\"},{\"firstName\":\"Shawna\",\"lastName\":\"Stelzer\"}]}]"));
	    }
	    
	    @Test
	    public void getChildFromAddress_whenChildFromAddressNotExist_thenReturngetChildFromAddressNotFound() 
	    		throws Exception {
		      this.mockMvc.perform(MockMvcRequestBuilders.get("/childAlert")
		                  .contentType(APPLICATION_JSON)
		                  .param("address", "Address NOT IN DATA"))
		      			  .andExpect(status().isNotFound());
	    }
	    
	    @Test
	    public void getCommunityEmail_whenCityExist_thenReturngetCommunityEmail() 
	    		throws Exception {
		      this.mockMvc.perform(MockMvcRequestBuilders.get("/communityEmail")
		                  .contentType(APPLICATION_JSON)
		                  .param("city", "Culver"))
		                  .andExpect(status().isOk());
	    }

	    @Test
	    public void getCommunityEmail_whenCityNotExist_thenReturngetCommunityEmailNotFound() 
	    		throws Exception {
		      this.mockMvc.perform(MockMvcRequestBuilders.get("/communityEmail")
		                  .contentType(APPLICATION_JSON)
		                  .param("city", "city NOT IN DATA"))
		      			  .andExpect(status().isNotFound());
	    }
	    
	    @Test
	    public void personCreation_whenAllCorrectInfos_thenReturnPersonCreated()
	               throws Exception {
	      this.mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT)
	                  .contentType(APPLICATION_JSON)
	                  .content("{\"firstName\": \"NEWfirstName\",\"lastName\": \"NEWlastName\",\"address\": \"myaddress\",\"city\": \"mycity\",\"zip\": \"myzip\",\"phone\": \"myphone\",\"email\": \"email@email.com\"}")
	                  .accept(APPLICATION_JSON))
	                  .andDo(MockMvcResultHandlers.print())
	                  .andExpect(status().isOk());
	    }
	    
	    @Test
	    public void personCreation_whenAlreadyExistingPerson_thenReturnPersonNotCreated()
	               throws Exception {
	      this.mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT)
	                  .contentType(APPLICATION_JSON)
	                  .content("{\"firstName\": \"John\",\"lastName\": \"Boyd\",\"address\": \"1509 Culver St\",\"city\": \"Culver\",\"zip\": \"97451\",\"phone\": \"841-874-6512\",\"email\": \"jaboyd@email.com\"}")
	                  .accept(APPLICATION_JSON))
	                  .andDo(MockMvcResultHandlers.print())
	                  .andExpect(status().isConflict());
	    }
	    
	    @Test
	    public void personUpdate_whenPersonExist_thenReturnPersonUpdated()
	               throws Exception {
	      this.mockMvc.perform(MockMvcRequestBuilders.put(ENDPOINT)
	                  .contentType(APPLICATION_JSON)
	                  .content("{\"firstName\": \"John\",\"lastName\": \"Boyd\",\"address\": \"NEW Address\",\"city\": \"Culver\",\"zip\": \"97451\",\"phone\": \"841-874-6512\",\"email\": \"NEWMAIL@email.com\"}")
	                  .accept(APPLICATION_JSON))
	                  .andDo(MockMvcResultHandlers.print())
	                  .andExpect(status().isOk());
	    }
	    
	    @Test
	    public void personUpdate_whenPersonNotExist_thenReturnPersonNotUpdated()
	               throws Exception {
	      this.mockMvc.perform(MockMvcRequestBuilders.put(ENDPOINT)
	                  .contentType(APPLICATION_JSON)
	                  .content("{\"firstName\": \"firstName NOT IN DATA\",\"lastName\": \"lastName NOT IN DATA\",\"address\": \"NEW Address\",\"city\": \"Culver\",\"zip\": \"97451\",\"phone\": \"841-874-6512\",\"email\": \"NEWMAIL@email.com\"}")
	                  .accept(APPLICATION_JSON))
	                  .andDo(MockMvcResultHandlers.print())
	                  .andExpect(status().isConflict());
	    }
	    
	    @Test
	    public void personDelete_whenDeletePersonExist_thenReturnPersonDeleted()
	               throws Exception {
	      this.mockMvc.perform(MockMvcRequestBuilders.delete(ENDPOINT)
	                  .contentType(APPLICATION_JSON)
	                  .param("firstName", "Eric")
	                  .param("lastName", "Cadigan")
	                  .accept(APPLICATION_JSON))
	                  .andDo(MockMvcResultHandlers.print())
	                  .andExpect(status().isOk());
	   }
	    
	    @Test
	    public void personDelete_whenDeletePersonNotExist_thenReturnPersonNotDeleted()
	               throws Exception {
	      this.mockMvc.perform(MockMvcRequestBuilders.delete(ENDPOINT)
	                  .contentType(APPLICATION_JSON)
	                  .param("firstName", "firstName NOT IN DATA")
	                  .param("lastName", "lastName NOT IN DATA")
	                  .accept(APPLICATION_JSON))
	                  .andDo(MockMvcResultHandlers.print())
	                  .andExpect(status().isConflict());
	   }
}

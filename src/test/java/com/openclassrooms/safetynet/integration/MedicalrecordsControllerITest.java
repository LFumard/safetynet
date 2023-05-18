package com.openclassrooms.safetynet.integration;

import com.openclassrooms.safetynet.SafetynetApplication;
import org.junit.Before;
//import org.junit.Test;
import org.junit.jupiter.api.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(classes = {SafetynetApplication.class})
@AutoConfigureMockMvc
public class MedicalrecordsControllerITest {

	private final String ENDPOINT = "/medicalrecords";
	
	@Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;
    
    @Before
    public void setupMockMvc() {
    	
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
    
    @Test
    public void medicalrecordsGet_whenAllCorrectInfos_thenReturnmedicalrecords() throws Exception {
    	
        this.mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT)
                    .contentType(APPLICATION_JSON)
                    .accept(APPLICATION_JSON))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isOk())
        			.andExpect(jsonPath("$.length()", is(24)));
    }
    
    @Test
    public void medicalrecordsCreation_whenAllCorrectInfos_thenReturnmedicalrecordsCreated() throws Exception {
    	
        this.mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT)
                    .contentType(APPLICATION_JSON).content(" { \r\n"
                                + "     \"firstName\":\"NEW FirstName\", \r\n"
                                + "     \"lastName\":\"NEW LastName\", \r\n"
                                + "     \"birthdate\":\"01/01/2000\", \r\n"
                                + "     \"medications\":[\"NEW MEDICATION:Medication1\"], \r\n"
                                + "     \"allergies\":[\"NEW ALLERGY:Allergie1\"] \r\n"
                                + "     }")
                    .accept(APPLICATION_JSON))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isOk());
    }
    
    @Test
    public void medicalrecordsCreation_whenAlreadyExistingmedicalrecords_thenReturnmedicalrecordsNotCreated() throws Exception {
    	
        this.mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT)
                    .contentType(APPLICATION_JSON).content(" { \r\n"
                                + "     \"firstName\":\"Warren\", \r\n"
                                + "     \"lastName\":\"Zemicks\", \r\n"
                                + "     \"birthdate\":\"03/06/1985\", \r\n"
                                + "     \"medications\":[\"NEW MEDICATION:Medication1\"], \r\n"
                                + "     \"allergies\":[\"NEW ALLERGY:Allergie1\"] \r\n"
                                + "     }")
                    .accept(APPLICATION_JSON))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isConflict());
    }
    
    @Test
    public void medicalrecordsUpdated_whenAllCorrectInfos_thenReturnmedicalrecordsUpdated() throws Exception {
    	
        this.mockMvc.perform(MockMvcRequestBuilders.put(ENDPOINT)
                    .contentType(APPLICATION_JSON).content(" { \r\n"
                                + "     \"firstName\":\"Warren\", \r\n"
                                + "     \"lastName\":\"Zemicks\", \r\n"
                                + "     \"birthdate\":\"01/01/2000\", \r\n"
                                + "     \"medications\":[\"NEW MEDICATION:Medication1\"], \r\n"
                                + "     \"allergies\":[\"NEW ALLERGY:Allergie1\"] \r\n"
                                + "     }")
                    .accept(APPLICATION_JSON))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isOk());
    }
    
    @Test
    public void medicalrecordsUpdated_whenNotExistingMedicalRecords_thenReturnmedicalrecordsNotUpdated() throws Exception {
    	
        this.mockMvc.perform(MockMvcRequestBuilders.put(ENDPOINT)
                    .contentType(APPLICATION_JSON).content(" { \r\n"
                                + "     \"firstName\":\"firstName NOT IN DATA\", \r\n"
                                + "     \"lastName\":\"lastName NOT IN DATA\", \r\n"
                                + "     \"birthdate\":\"01/01/2000\", \r\n"
                                + "     \"medications\":[\"NEW MEDICATION:Medication1\"], \r\n"
                                + "     \"allergies\":[\"NEW ALLERGY:Allergie1\"] \r\n"
                                + "     }")
                    .accept(APPLICATION_JSON))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isConflict());
    }
    
    @Test
    public void medicalrecordsDeleted_whenAllCorrectInfos_thenReturnmedicalrecordsDeleted() throws Exception {
    	
        this.mockMvc.perform(MockMvcRequestBuilders.delete(ENDPOINT)
                    .contentType(APPLICATION_JSON).content(" { \r\n"
                                + "     \"firstName\":\"Jamie\", \r\n"
                                + "     \"lastName\":\"Peters\", \r\n"
                                + "     \"birthdate\":\"03/06/1982\", \r\n"
                                + "     \"medications\":[], \r\n"
                                + "     \"allergies\":[] \r\n"
                                + "     }")
                    .accept(APPLICATION_JSON))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isOk());
    }
    
    @Test
    public void medicalrecordsDeleted_whenNotExistingMedicalRecords_thenReturnmedicalrecordsNotDeleted() throws Exception {
    	
        this.mockMvc.perform(MockMvcRequestBuilders.delete(ENDPOINT)
                    .contentType(APPLICATION_JSON).content(" { \r\n"
                                + "     \"firstName\":\"firstName NOT IN DATA\", \r\n"
                                + "     \"lastName\":\"lastName NOT IN DATA\", \r\n"
                                + "     \"birthdate\":\"01/01/2000\", \r\n"
                                + "     \"medications\":[], \r\n"
                                + "     \"allergies\":[] \r\n"
                                + "     }")
                    .accept(APPLICATION_JSON))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isConflict());
    }
}

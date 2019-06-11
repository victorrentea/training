package ro.victor.unittest.mvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
//@WebAppConfiguration
//@ContextConfiguration(classes = MvcApplication.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MvcTest {

//	@Autowired
//    private WebApplicationContext wac;

	@Autowired
    private MockMvc mockMvc;

//    @Before
//    public void setup() {
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
//    }
    
    @Test
    public void peaceTest() throws Exception {
    	mockMvc.perform(get("/peace/{ssn}","abc"))
    		.andExpect(status().isOk())
    		.andExpect(header().string("Head-Shot", "true"))
    		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
    		.andExpect(jsonPath("$.ssn").value("ABC"));
    }

}



package queryapi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.cbioportal.cbio_engine.CBioEngine;
import org.cbioportal.cbio_engine.domain.CBioQuery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by jlindsay on 12/8/15.
 */
@WebIntegrationTest
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes={CBioEngine.class})
public class QueryApiUnitTest {

    @Autowired
    private WebApplicationContext context;

    // variable for mockMvc
    private final String uriPrefix = "/";
    private MockMvc mockMvc;
    Gson gson;

    @Before
    public void setUp() throws Exception {

        // create the mockMvc object.
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .build();
    }

    @Test
    public void testQuery() throws Exception {

        // create the parser.
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSz").create();

        // create object.
        CBioQuery cq = new CBioQuery();
        cq.setClinical_filter("{\"attributes\": {\"$elemMatch\": {\"key\": \"AGE\", \"value\": {\"$gte\": 40}}}}");
        cq.setGenomic_filter("pizza is good");
        cq.setTransform("trans1");

        // make this query.
        MvcResult result = this.mockMvc.perform(
                post(uriPrefix + "cbioquery/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(cq, CBioQuery.class)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        // parse results.
        //gson.fromJson(result.getResponse(), ArrayList.class)
    }



}

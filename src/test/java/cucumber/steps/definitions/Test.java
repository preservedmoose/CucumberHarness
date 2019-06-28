package cucumber.steps.definitions;

import com.preservedmoose.cucumberharness.Application;

import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import cucumber.api.java.en.Given;
import io.cucumber.datatable.DataTable;

@ActiveProfiles({ "test", "cucumber" })
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = { Application.class },
                      initializers = ConfigFileApplicationContextInitializer.class)
public class Test
{
    @Given("^I have this table$")
    public void IHaveThisTable(DataTable dataTable)
    {
    }

    public class TestTable
    {
        public String id_pods;
        public String id_station;
        public String days;
        public String daypart;
        public String time;
        public String allowed_industry_codes;
    }
}

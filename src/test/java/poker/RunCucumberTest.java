package poker;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
	plugin   = {"junit:output"},
	features = {"src/test/resources/poker"},
	glue     = {"classpath:steps"}
)
public class RunCucumberTest {
	
}
package com.example.webguidemo;

import static java.util.Arrays.asList;
import static org.jbehave.core.io.CodeLocations.codeLocationFromClass;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.jbehave.core.Embeddable;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.jbehave.core.steps.SilentStepMonitor;
import org.jbehave.web.selenium.ContextView;
import org.jbehave.web.selenium.LocalFrameContextView;
import org.jbehave.web.selenium.PerStoryWebDriverSteps;
import org.jbehave.web.selenium.SeleniumConfiguration;
import org.jbehave.web.selenium.SeleniumContext;
import org.jbehave.web.selenium.SeleniumStepMonitor;
import org.jbehave.web.selenium.TypeWebDriverProvider;
import org.jbehave.web.selenium.WebDriverProvider;
import org.jbehave.web.selenium.WebDriverScreenshotOnFailure;
import org.jbehave.web.selenium.WebDriverSteps;
import org.openqa.selenium.chrome.ChromeDriver;

public class PracticeWebTest extends JUnitStories {
	
	private WebDriverProvider driverProvider = new TypeWebDriverProvider(ChromeDriver.class);
    private WebDriverSteps lifecycleSteps = new PerStoryWebDriverSteps(driverProvider); // or PerStoryWebDriverSteps(driverProvider)
    private Pages pages = new Pages(driverProvider);
    private SeleniumContext context = new SeleniumContext();
    private ContextView contextView = new LocalFrameContextView().sized(500, 100);

    public PracticeWebTest() {
    	Properties properties = new Properties();
    	InputStream is = null;
    	
    	try {
    		is = getClass().getResourceAsStream("config.properties");
    		if (is == null) {
    			System.out.println("Error while loading properties file");
    			return;
    		}
			properties.load(is);
			
			Set<Object> keys = properties.keySet();
			for(Object k:keys){
				System.setProperty((String) k, (String)properties.getProperty((String)k));
			
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

    	System.setProperty("webdriver.chrome.driver", System.getProperty("driverLocation"));
	}

	@Override
    public Configuration configuration() {
        Class<? extends Embeddable> embeddableClass = this.getClass();
        return new SeleniumConfiguration()
                .useSeleniumContext(context)
                .useWebDriverProvider(driverProvider)
                .useStepMonitor(new SeleniumStepMonitor(contextView, context, new SilentStepMonitor()))
                .useStoryLoader(new LoadFromClasspath(embeddableClass))
                .useStoryReporterBuilder(new StoryReporterBuilder()
                    .withCodeLocation(codeLocationFromClass(embeddableClass))
                    .withDefaultFormats()
                    .withFormats(Format.CONSOLE, Format.TXT));
    }
 
    @Override
    public InjectableStepsFactory stepsFactory() {
        Configuration configuration = configuration();
        return new InstanceStepsFactory(configuration, 
                new PracticeSteps(pages),
                lifecycleSteps,
                new WebDriverScreenshotOnFailure(driverProvider, configuration.storyReporterBuilder()));
    }
	@Override
	protected List<String> storyPaths() {
		return new StoryFinder().findPaths(
				codeLocationFromClass(this.getClass()).getFile(),
				asList("**/*.story"), null);
	}

}

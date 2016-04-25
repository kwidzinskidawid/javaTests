package com.example.webguidemo;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;


import static org.junit.Assert.*;

public class PracticeSteps {
	
	private final Pages pages;

	public PracticeSteps(Pages pages) {
		this.pages = pages;
	}
	
	@Given("user is on Home page")
    public void userIsOnHomePage(){        
        pages.home().open();        
    }
    
    @When("user selects radio button number $n")
    public void radioButtonClick(int n) {
    	pages.home().clickRadioButton(n);
    }
    
    @Then("radio button number $n is selected")
    public void radioButtonTest(int n) {
    	assertTrue(pages.home().isRadioButtonSelected(n));
    }
    
    @When("user selects checkbox number $n")
    public void checkBoxClick(int n) {
    	pages.home().clickCheckBox(n);
    }
    
    @Then("checkbox number $n is selected")
    public void checkBoxTest(int n) {
    	assertTrue(pages.home().isCheckBoxSelected(n));
    }
    
    @When("user enters $val to verification textbox")
    public void enterVerificationText(String val) {
    	pages.home().enterVerificationText(val);
    }
    
    @Then("error is visible")
    public void errorLabelShown() {
    	assertTrue(pages.home().isErrorLabelVisible());
    }
    
    @Then("error is not visible")
    public void errorNotShown() {
    	assertFalse(pages.home().hasErrorClass());
    }
    
    @When("user clicks alert")
    public void alertClicked() {
    	pages.home().clickAlert();
    }
    
    @Then("alert is shown")
    public void alertShown() {
    	assertTrue(pages.home().isAlertVisible());
    }
    
}

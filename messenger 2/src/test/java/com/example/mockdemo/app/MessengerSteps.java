package com.example.mockdemo.app;

import static org.junit.Assert.assertEquals;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.example.mockdemo.messenger.MessageServiceSimpleImpl;

public class MessengerSteps {
	
	private Messenger messenger;
	
	@Given("a messenger")
	public void calulatorSetup(){
		messenger = new Messenger(new MessageServiceSimpleImpl());
	}
	
	@When("set arguments to $a and $b")
	public void setArguments(int a, int b){

	}
	
    @Then("adding should return $result")
	public void shouldAdd(int result){

	}
    
    @Then("subtracting should return $result")
  	public void shouldSubstract(int result){

  	}
}

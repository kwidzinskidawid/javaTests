package com.example.mockdemo.app;

import static org.hamcrest.CoreMatchers.either;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.example.mockdemo.messenger.ConnectionStatus;
import com.example.mockdemo.messenger.MalformedRecipientException;
import com.example.mockdemo.messenger.MessageService;
import com.example.mockdemo.messenger.SendingStatus;

public class MessengerSteps {
	
	private Messenger messenger;
	private MessageService msMock;
	
	private static String VALID_SERVER;
	private static String INVALID_SERVER;

	private static String VALID_MESSAGE;
	private static String INVALID_MESSAGE;
	
	private String currentServer;
	private String currentMsg;
	
	@Given("a messenger where valid server is $vs and invalid server is $is valid message is $vm and invalid message is $im")
	public void calulatorSetup(String vs, String is, String vm, String im) throws MalformedRecipientException{
		msMock = mock(MessageService.class);
		messenger = new Messenger(msMock);
		
		VALID_SERVER = vs;
		INVALID_SERVER = is;
		VALID_MESSAGE = vm;
		INVALID_MESSAGE = im;
		
		when(msMock.send(VALID_SERVER, VALID_MESSAGE)).thenReturn(
				SendingStatus.SENT);
		when(msMock.send(VALID_SERVER, INVALID_MESSAGE)).thenThrow(
				new MalformedRecipientException());
		when(msMock.send(INVALID_SERVER, VALID_MESSAGE)).thenReturn(
				SendingStatus.SENDING_ERROR);
		when(msMock.send(INVALID_SERVER, INVALID_MESSAGE)).thenReturn(
				SendingStatus.SENDING_ERROR);
		

		when(msMock.checkConnection(INVALID_SERVER)).thenReturn(
				ConnectionStatus.FAILURE);
		when(msMock.checkConnection(VALID_SERVER)).thenReturn(
				ConnectionStatus.SUCCESS);
	}	
	
	
	@When("server is $server and message is $msg")
	public void setArguments(String server, String msg){
		this.currentServer = server.equals("valid") ? VALID_SERVER : INVALID_SERVER;
		this.currentMsg = msg.equals("valid") ? VALID_MESSAGE : INVALID_MESSAGE;
	}
	
    @Then("sending message should return code status $result")
	public void checkSending(int result) throws MalformedRecipientException{
    	
			assertEquals(result, messenger.sendMessage(currentServer, currentMsg));
			
			verify(msMock, atLeastOnce()).send(currentServer, currentMsg);
	}
    
    @Then("sending message should return code status $result or $result2")
	public void checkSending(int result, int result2) throws MalformedRecipientException{

		assertThat(messenger.sendMessage(currentServer, currentMsg),
				either(equalTo(result)).or(equalTo(result2)));
			
			verify(msMock, atLeastOnce()).send(currentServer, currentMsg);
	}
    
    @Then("checking connection should return $result")
    public void checkChecking(int result) {
    	
		assertEquals(result, messenger.testConnection(currentServer));
		
		verify(msMock, atLeastOnce()).checkConnection(currentServer);
    }

}

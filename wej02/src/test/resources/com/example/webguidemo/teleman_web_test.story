Scenario: User searches for a single step
 
Given user is on Home page
When user selects radio button number 2
Then radio button number 2 is selected

When user selects checkbox number 2
And user selects checkbox number 3
Then checkbox number 2 is selected
And checkbox number 3 is selected

When user enters ff to verification textbox
Then error is visible

When user enters 333 to verification textbox
Then error is visible

When user enters 10 to verification textbox
Then error is not visible

When user clicks alert
Then alert is shown


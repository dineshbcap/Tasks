Scenario: To verify the items when clicking on the Link (Calls -> Ask)
Given the user logged in with username and password
When the user clicks on 'Options' Menu
And the user enters the ticker symbol 'AAPL'
And the user selects first quote for call option
Then the user should see the menu items Buy Call, Buy Advanced, Buy With OCO,Exchange Lookup,Add To watchList, Set Alert and Close Menu.
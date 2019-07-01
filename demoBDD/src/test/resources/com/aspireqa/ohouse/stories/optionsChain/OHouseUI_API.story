
Scenario: Verify if for PUT options Bid/Ask price, Bid/Ask Size, Vol and OI values in OptionsChain Listings match with ViewQuote api response
Given the user logged in with username and password
When the user clicks on 'Options' Menu
And the user enters the ticker symbol 'GOOG'
Then the Bid price, Ask price, Bid size, Ask size, Vol and OI values for PUT Option in OptionsChain listing should match with ViewQuote api response

Scenario: Verify if for CALL options Bid/Ask price, Bid/Ask Size, Vol and OI values in OptionsChain Listings match with ViewQuote api response
Given the user logged in with username and password
When the user clicks on 'Options' Menu
And the user enters the ticker symbol 'GOOG'
Then the Bid price, Ask price, Bid size, Ask size, Vol and OI values for CALL Option in OptionsChain listing should match with ViewQuote api response
Feature: user able to create Account for login

  Scenario: verify the user able to create an account
    When User is on createAccount page
    Given user enter the data for creating account
      |Name      | EmailAddress     | Password    |
      |TestUser  | Testmail1@vit.com| Tester#1234 |
    Then User should able to create the account 'TestUser'

  @API
  Scenario: verify the user able to create an account with API
    Given user enter the data for registration API
      |Name      | EmailAddress     | Password    |
      |TestUser  | Testmail1@hit.com| Tester#1234 |
    Then User should able to see the 201 success registration

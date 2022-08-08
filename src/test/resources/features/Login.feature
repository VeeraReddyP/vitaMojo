Feature: user able to login into application

  Scenario: verify the user able to login
    When User is on Login page
    Given user enter the data to login
            | EmailAddress     | Password    |
            | Testmail1@vit.com| Tester#1234 |
    Then User should able to see the account 'test'

  @API
  Scenario: verify the user able to login with API
    Given user enter the data for login API
      | EmailAddress     | Password    |
      | Testmail1@vit.com| Tester#1234 |
    Then User should able to see the 201 success

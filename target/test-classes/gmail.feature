Feature: Send email with image attachment

  #Normal flow
  Scenario: Send the email after adding an image attachment
    Given I am a user with an existing account
    And I am on the "compose new message" page with the recipient email and subject specified
    When I press "attach files" and select an image and I press "open"
    And I press "Send"
    Then the email should be sent to the recipient with the image attachment

  #Alternative flow
  Scenario: Send the email after adding more than one image attachment
    Given I am a user with an existing account
    And I am on the "compose new message" page with the recipient email and subject specified
    And I have an image attachment in the email
    When I press "attach files" and select an image and I press "open"
    And I press "Send"
    Then the email should be sent to the recipient with both image attachments

  #Error flow
  Scenario: Send the email after removing the previously added image attachment
    Given I am a user with an existing account
    And I am on the "compose new message" page with the recipient email and subject specified
    And I have an image attachment in the email
    When I press the remove button
    And I press "Send"
    Then the email should be sent to the recipient without the image attachment

  #Error Flow
  Scenario: Send the email to an invalid address after adding an image attachment
    Given I am a user with an existing account
    And I am on the "compose new message" page with the recipient email and subject specified, but the recipient email is invalid
    When I press "attach files" and select an image and I press "open"
    And I press "Send"
    Then an error dialog should be popped up indicating that the email address is invalid

  #Error Flow:
  Scenario: Send the email after adding a regular file attachment
    Given I am a user with an existing account
    And I am on the "compose new message" page with the recipient email and subject specified
    When I press "attach files" and select a regular file and I press "open"
    And I press "Send"
    Then the email should be sent to the recipient with the regular file attachment


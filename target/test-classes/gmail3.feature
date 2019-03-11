Feature: Send email with image attachment

  #Error flow
  Scenario Outline: Send the email after removing the previously added image attachment
  Given I am a user with an existing account
  And I am on the "compose new message" page with the recipient email <receiver> and subject specified
  And I have an image attachment in the email
  When I press the remove button
  And I press "Send"
  Then the email should be sent to the receiver without the image attachment

  Examples:
  | receiver                 |
  | borui.tao@mail.mcgill.ca |
  | borui.tao@gmail.com      |
  | tbrtree@126.com          |
  | 346394775@qq.com         |
  | barry.angela111@gmail.com|
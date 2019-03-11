Feature: Send email with image attachment

  #Normal flow
  Scenario Outline: Send the email after adding an image attachment
    Given I am a user with an existing account
    And I am on the "compose new message" page with the recipient email <recipient> and subject specified
    When I press "attach files" and select an image <image> and I press "open"
    And I press "Send"
    Then the email should be sent to the receiver with the image attachment

    Examples:
      | recipient                 | image    |
      | borui.tao@mail.mcgill.ca  | img1.png |
      | borui.tao@gmail.com       | img2.png |
      | tbrtree@126.com           | img3.png |
      | 346394775@qq.com          | img4.png |
      | barry.angela111@gmail.com | img5.png |

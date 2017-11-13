# Learn how to consume and test REST APIs using Spring 5's Reactive WebClient and WebTestClient.

## Steps to Setup

1. Create a personal access token on Github - https://github.com/settings/tokens

2. Open `src/main/resources/application.properties` and specify your github username in `app.github.username` property, and your personal access token in `app.github.token` property.

3. Run the app using `mvn spring-boot:run`
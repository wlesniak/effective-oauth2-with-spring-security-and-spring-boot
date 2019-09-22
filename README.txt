To run this demo:
- Ensure your client secrets are correct, and updated in the application.yml file.
- From modules 4 - 8 an instance of a keycloak server runnning, with a user created. I have included realm-export file to setup
the demo realm if you do not wish to configure it manually by following the modules, this will set everything up for you.
- For the admin features you need a user with the portfolio_admin role, see this modules clips for instructions.
- You need to start keycloak before any of the other services as they use the issuer URI to bootstap security.
- All service pricing, portfolio and support need to be running.
- In module 7 - 8 your access token created by keycloak, needs to have the "portfolio-service" and "support-service" "aud" - audience
claim in the token, if it does not the "com.pluralsight.security.validatorsCryptoJwtTokenValidator" will deny the request. See the module demo on how to set this up.
- From module 7 the token created by the portfolio service via the client credentials grant needs to "pricing" 
scope in the user info claims, otherwise the pricing service will not start.
- Also ensure the roles mapper, and portfolio and support service audience mappers are created for the react client in keycloak.
***********************
Trouble shooting
***********************
If you have any issues try the following:
- 
- Remove your localhost browser cookie and try to re-authenticate.
- Your access token created by keycloak
- Ensure your client id and secrets are correct in the services: application.yml file.
- Enable debug logging in the application.yml file of your properties file to check the logs.
- You can use the realm-export.json file to import the keycloak demo realm with all the settings.
package org.scrumfall.webapi.contract.consumers;

import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import au.com.dius.pact.provider.spring.junit5.PactVerificationSpringProvider;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Provider("web-api")
@PactFolder("../pacts")
public class ProviderTest {

    @State("I have a user_id and wallet")
    public void userIdAndWallet(Map<String, Object> params) {
    }

    @State("I have an non-existent user_id")
    public void nonExistentUserId(Map<String, Object> params) {
    }

    @State("There is a certifications endpoint")
    public void certificationsEndpoint(Map<String, Object> params) {
    }

    @State("There is a certification price")
    public void certificationPrice() {
    }

    @TestTemplate
    @ExtendWith(PactVerificationSpringProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }
}

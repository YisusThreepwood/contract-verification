package my.example.order;

import au.com.dius.pact.provider.PactVerifyProvider;
import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit5.AmpqTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Collections;

@PactFolder("pacts")
@Provider("computeOrderPrice")
public class ComputeOrderPriceContractVerificationTest {
    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @BeforeEach
    void before(PactVerificationContext context) {
        context.setTarget(new AmpqTestTarget(Collections.singletonList("my.example.order")));
    }

    @PactVerifyProvider("the price for an order")
    public String thePriceForAnOrder()
    {
        ComputeOrderPrice computeOrderPrice = new ComputeOrderPrice();
        return computeOrderPrice.transform(123.45);
    }
}

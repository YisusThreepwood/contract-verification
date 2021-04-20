package my.example.notificator;

import au.com.dius.pact.consumer.MessagePactBuilder;
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.consumer.junit5.ProviderType;
import au.com.dius.pact.core.model.annotations.Pact;
import au.com.dius.pact.core.model.messaging.Message;
import au.com.dius.pact.core.model.messaging.MessagePact;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static io.pactfoundation.consumer.dsl.LambdaDsl.newJsonBody;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@ExtendWith(PactConsumerTestExt.class)
@PactFolder("pacts")
class NotifyUserContractTest {

    @Pact(consumer = "notifyUser", provider = "createOrder")
    MessagePact createdOrderPact(MessagePactBuilder builder) {
        final DslPart messageBody = newJsonBody((jsonObj) -> {
            jsonObj.stringType("orderId");
            jsonObj.stringType("userEmail");
        }).build();

        return builder
                .hasPactWith("createOrder")
                .expectsToReceive("a message for a created order")
                .withContent(messageBody)
                .toPact();
    }

    @Test
    @PactTestFor(
        providerName = "createOrder",
        providerType = ProviderType.ASYNCH,
        pactMethod = "createdOrderPact"
    )
    void testContractForACreatedOrder(List<Message> messages) {
        for (Message message: messages) {
            assertThat(
                    message.getContents().valueAsString(),
                    is("{\"orderId\":\"string\",\"userEmail\":\"string\"}")
            );
        }
    }

    @Pact(consumer = "notifyUser", provider = "computeOrderPrice")
    MessagePact computeOrderPact(MessagePactBuilder builder) {
        final DslPart messageBody = newJsonBody((jsonObj) -> {
            jsonObj.decimalType("orderPrice", 123.45);
        }).build();

        return builder
                .hasPactWith("computeOrderPrice")
                .expectsToReceive("the price for an order")
                .withContent(messageBody)
                .toPact();
    }

    @Test
    @PactTestFor(
        providerName = "computeOrderPrice",
        providerType = ProviderType.ASYNCH,
        pactMethod = "computeOrderPact"
    )
    void testContractForThePriceOfAnOrder(List<Message> messages) {
        for (Message message: messages) {
            assertThat(
                    message.getContents().valueAsString(),
                    is("{\"orderPrice\":123.45}")
            );
        }
    }
}

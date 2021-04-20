package my.example.order;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;

import java.util.Map;

public class CreateOrder
        implements RequestHandler<Map<String, String>, String> {
    AmazonSQS sqsClient = AmazonSQSClientBuilder.standard().build();

    @Override
    public String handleRequest(Map<String, String> event, Context context)
    {
        // TODO: process for creating the order and get the user's email

        String orderId = "1234-fake-order";
        String userEmail = "fake.user.email@test.es";

        sqsClient.sendMessage(createMessage(orderId, userEmail));
        return "Order created successfully";
    }

    public SendMessageRequest createMessage(String orderId, String userEmail)
    {
        return new SendMessageRequest(
                "https://eu-west-1/queue.f4K3/123456/fakesqs",
                String.format("{\"orderId\": \"%s\", \"userEmail\": \"%s\"}", orderId, userEmail)
        );
    }
}

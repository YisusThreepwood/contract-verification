package my.example.order;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.Map;

public class ComputeOrderPrice
    implements RequestHandler<Map<String, String>, String> {
    @Override
    public String handleRequest(Map<String, String> event, Context context)
    {
        // TODO: compute the order price
        Double orderPrice = 123.45;
        return transform(orderPrice);
    }

    public String transform(Double orderPrice)
    {
        return String.format("{\"orderPrice\":%f}", orderPrice);
    }
}

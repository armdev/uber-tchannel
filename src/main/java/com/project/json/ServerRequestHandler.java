package com.project.json;

import com.uber.tchannel.messages.JsonRequest;
import com.uber.tchannel.messages.JsonResponse;
import com.uber.tchannel.api.handlers.JSONRequestHandler;

public class ServerRequestHandler<T, U> extends JSONRequestHandler<T, U> {

    @Override
    public JsonResponse<U> handleImpl(JsonRequest<T> request) {
        System.out.println("I am handler: " + request.toString());
        return new JsonResponse.Builder<U>(request).build();
    }

}

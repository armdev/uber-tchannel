package com.project.json;

import com.uber.tchannel.api.TChannel;
import com.uber.tchannel.api.TFuture;
import com.uber.tchannel.messages.JsonRequest;
import com.uber.tchannel.messages.JsonResponse;
import com.uber.tchannel.utils.TChannelUtilities;

public class JsonClient {

    public static void main(String[] args) throws Exception {
        final TChannel tchannel = new TChannel.Builder("json-server").build();

        JsonRequest<UserModel> req = new JsonRequest.Builder<UserModel>(
                "json-server",
                "json-endpoint")
                .setBody(new UserModel("armen@gmail.com", "123456"))
                .setTimeout(1000)
                .build();
        TFuture<JsonResponse<UserModel>> p = tchannel
                .makeSubChannel("json-service").send(
                req,
                TChannelUtilities.getCurrentIp(),
                8888
        );

        try (JsonResponse<UserModel> res = p.get()) {
            System.out.println(res);
        }

        tchannel.shutdown();
    }
}

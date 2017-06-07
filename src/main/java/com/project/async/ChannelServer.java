/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.async;

import com.uber.tchannel.api.ResponseCode;
import com.uber.tchannel.api.TChannel;
import com.uber.tchannel.api.handlers.RawRequestHandler;
import com.uber.tchannel.messages.RawRequest;
import com.uber.tchannel.messages.RawResponse;
import java.net.InetAddress;

/**
 *
 * @author armenar
 */
public class ChannelServer {

    public static void main(String[] args) {
        try {
            TChannel server = createServer();
        } catch (Exception ex) {
            ex.getLocalizedMessage();
        }
    }

    private static TChannel createServer() throws Exception {
        // create TChannel
        TChannel tchannel = new TChannel.Builder("server")
                .setServerHost(InetAddress.getByName("127.0.0.1"))
                .setServerPort(8888)
                .build();
        System.out.println("Server started");
        tchannel.makeSubChannel("server")
                .register("pong", new RawRequestHandler() {
                    private int count = 0;
                    @Override
                    public RawResponse handleImpl(RawRequest request) {
                        System.out.println(String.format("Request received: header: %s, body: %s",
                                request.getHeader(),
                                request.getBody()));
                        count++;
                        switch (count) {
                            case 1:
                                return new RawResponse.Builder(request)
                                        .setTransportHeaders(request.getTransportHeaders())
                                        .setHeader("Polo")
                                        .setBody("Pong!")
                                        .build();
                            case 2:
                                return new RawResponse.Builder(request)
                                        .setTransportHeaders(request.getTransportHeaders())
                                        .setResponseCode(ResponseCode.Error)
                                        .setHeader("Polo")
                                        .setBody("I feel bad ...")
                                        .build();
                            default:
                                throw new UnsupportedOperationException("I feel very bad!");
                        }
                    }
                });
        tchannel.listen();
        return tchannel;
    }

}

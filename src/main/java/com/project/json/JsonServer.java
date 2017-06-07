package com.project.json;

import com.uber.tchannel.api.TChannel;

public class JsonServer {
    
    public static void main(String[] args) throws Exception {        
        final TChannel tchannel = new TChannel.Builder("json-server")
            .setServerPort(8888)
            .build();
        
        tchannel.makeSubChannel("json-server")
            .register("json-endpoint", new ServerRequestHandler<>());
        tchannel.listen().channel().closeFuture().sync();
        tchannel.shutdown();
    }
}

package com.polkadot.polkadot_host.config;

import io.emeraldpay.polkaj.api.PolkadotApi;
import io.emeraldpay.polkaj.apihttp.JavaHttpAdapter;
import io.emeraldpay.polkaj.apiws.JavaHttpSubscriptionAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Configuration
public class PolkadotConfig {

    @Bean("polkadotApiHTTP")
    public PolkadotApi polkadotApiHTTP() throws URISyntaxException {
        return PolkadotApi.newBuilder()
                .rpcCallAdapter(JavaHttpAdapter.newBuilder()
                        .connectTo("https://rpc.polkadot.io")    // Public Polkadot node - http
                        .build())
                .build();
    }

    @Bean("polkadotApiWebSocket")
    public PolkadotApi polkadotApiWebSocket() throws URISyntaxException, ExecutionException, InterruptedException, TimeoutException {
        JavaHttpSubscriptionAdapter wsAdapter = JavaHttpSubscriptionAdapter.newBuilder()
                .connectTo("wss://polkadot.api.onfinality.io/public-ws").build();
        PolkadotApi api = PolkadotApi.newBuilder()
                .subscriptionAdapter(wsAdapter)
                .build();
        wsAdapter.connect().get(5, TimeUnit.SECONDS);
        return api;
    }
}

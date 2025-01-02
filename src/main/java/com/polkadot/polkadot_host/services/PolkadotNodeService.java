package com.polkadot.polkadot_host.services;

import com.fasterxml.jackson.databind.JsonNode;
import io.emeraldpay.polkaj.api.PolkadotApi;
import io.emeraldpay.polkaj.api.RpcCall;
import io.emeraldpay.polkaj.api.SubscribeCall;
import io.emeraldpay.polkaj.api.Subscription;
import io.emeraldpay.polkaj.apiws.DefaultSubscription;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static com.polkadot.polkadot_host.exceptions.messages.ExceptionMessage.POLKADOT_RPC_RESPONSE_IS_NOT_VALID_MAP;
import static com.polkadot.polkadot_host.exceptions.messages.ExceptionMessage.POLKADOT_RPC_RESPONSE_METHODS_KEY_IS_NOT_VALID_LIST;

@AllArgsConstructor
@Service
public class PolkadotNodeService {

    @Qualifier("polkadotApiHTTP")
    private final PolkadotApi polkadotApiHTTP;

    @Qualifier("polkadotApiWebSocket")
    private final PolkadotApi polkadotApiWebSocket;

    public List<String> getPolkadotNodeRpcMethods() throws InterruptedException, ExecutionException {

        Future<Object> result = polkadotApiHTTP.execute(RpcCall.create(Object.class, "rpc_methods"));
        Object response = result.get();

        if (!(response instanceof Map<?, ?>)) {
            throw new IllegalStateException(POLKADOT_RPC_RESPONSE_IS_NOT_VALID_MAP);
        }

        Object methods = ((Map<?, ?>) response).get("methods");
        if (!(methods instanceof List<?>)) {
            throw new IllegalStateException(POLKADOT_RPC_RESPONSE_METHODS_KEY_IS_NOT_VALID_LIST);
        }

        return ((List<?>) methods).stream()
                .filter(String.class::isInstance).map(String.class::cast).toList();
    }

    public String getPolkadotNodeGenesisHash() throws ExecutionException, InterruptedException {

        Future<String> result = polkadotApiWebSocket.execute(RpcCall.create(String.class, "chainSpec_v1_genesisHash"));
        return result.get();
    }

    private String getLastBlockHash() throws ExecutionException, InterruptedException {
        Future<String> hashFuture = polkadotApiHTTP.execute(
                RpcCall.create(String.class, "chain_getBlockHash")
        );
        return hashFuture.get();
    }

    public String getPolkadotNodeHeader() throws ExecutionException, InterruptedException, TimeoutException {
        String lastBlockHash = getLastBlockHash();
        Future<Subscription<JsonNode>> followSubscriptionFuture = polkadotApiWebSocket.subscribe(
                SubscribeCall.create(JsonNode.class, "chainHead_v1_follow", "chainHead_v1_unfollow",
                        List.of(false))
        );
        Subscription<JsonNode> followSubscription = followSubscriptionFuture.get(5, TimeUnit.SECONDS);
        String followSubscriptionId = ((DefaultSubscription) followSubscription).getId();
        Future<String> chainHeadHeaderFuture = polkadotApiWebSocket.execute(
                RpcCall.create(String.class, "chainHead_v1_header", List.of(followSubscriptionId, lastBlockHash))
        );

        return chainHeadHeaderFuture.get();
    }
}

package com.polkadot.polkadot_host.exceptions.messages;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class ExceptionMessage {

    public static final String GLOBAL_INVALID_STATE_EXCEPTION = "Global invalid state exception.";
    public static final String GLOBAL_EXECUTION_EXCEPTION = "Global execution exception.";
    public static final String GLOBAL_INTERRUPTED_EXCEPTION = "Global interrupted exception.";
    public static final String POLKADOT_RPC_RESPONSE_IS_NOT_VALID_MAP = "The Polkadot rpc method returned an invalid response, it is not a valid map.";
    public static final String POLKADOT_RPC_RESPONSE_METHODS_KEY_IS_NOT_VALID_LIST = "The Polkadot rpc method returned an invalid 'methods' key, it is not a valid list.";
    public static final String POLKADOT_RPC_EXECUTION_EXCEPTION = "An executions exception: %s occurred.";
    public static final String POLKADOT_RPC_INTERRUPTED_EXCEPTION = "An interrupted exception: %s occurred.";
}

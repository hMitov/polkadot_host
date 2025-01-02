package com.polkadot.polkadot_host.controllers;

import com.polkadot.polkadot_host.services.PolkadotNodeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("/dot")
public class PolkadotNodeController {

    private final PolkadotNodeService polkadotNodeService;

    @GetMapping("/rpc")
    public ResponseEntity<List<String>> getPolkadotNodeRpcMethods() throws ExecutionException, InterruptedException {
        log.debug("Fetch all rpc methods that are supported from the node.");

        return ResponseEntity.ok(polkadotNodeService.getPolkadotNodeRpcMethods());
    }

    @GetMapping("/genesis")
    public ResponseEntity<String> getPolkadotNodeGenesisHash() throws ExecutionException, InterruptedException {
        log.debug("Fetch the genesis hash of the node.");

        return ResponseEntity.ok(polkadotNodeService.getPolkadotNodeGenesisHash());
    }

    @GetMapping("/header")
    public ResponseEntity<String> getPolkadotNodeHeader() throws ExecutionException, InterruptedException, TimeoutException {
        log.debug("Fetch the SCALE encoded header of the chain head.");

        return ResponseEntity.ok(polkadotNodeService.getPolkadotNodeHeader());
    }
}

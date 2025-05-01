# pod-ilmoraunio-conftest

A [babashka pod](https://github.com/babashka/pods) for interacting with [conftest](https://github.com/open-policy-agent/conftest).

## Usage

```clojure
(ns my-ns
  (:require [babashka.pods :as pods]))

(pods/load-pod 'ilmoraunio/conftest "0.0.3")
(require '[pod.ilmoraunio.conftest :as conftest])
```

The namespace exposes two functions: `parse` and `parse-as`.

```clojure
(conftest/parse "test-resources/test.json")
;; => {"test-resources/test.json" {"hello" [1.0 2.0 4.0]}}

(conftest/parse-as "hocon" "test-resources/hocon.conf")
;; => {"test-resources/hocon.conf" {"play" {"editor" "<<unknown value>>", "server" {"http" {"idleTimeout" "\"75 seconds\"", "port" 9001.0, "address" "0.0.0.0"}, "https" {"address" "0.0.0.0", "engineProvider" "play.core.server.ssl.DefaultSSLEngineProvider", "idleTimeout" "\"75 seconds\"", "keyStore" {"algorithm" "<<unknown value>>", "password" "\"\"", "path" "<<unknown value>>", "type" "JKS"}, "needClientAuth" false, "port" "<<unknown value>>", "trustStore" {"noCaVerification" false}, "wantClientAuth" false}, "pidfile" {"path" "<<unknown value>>"}, "websocket" {"frame" {"maxLength" "64k"}}, "debug" {"addDebugInfoToRequests" false}, "dir" "<<unknown value>>"}}}}
```

## Development

### Build instructions

Run `make build`.

Assumes Go version 1.22+.

### Test instructions

Run `make test`.

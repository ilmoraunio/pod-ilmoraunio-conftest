(ns api-test
  (:require [babashka.pods :as pods]
            [clojure.test :refer [deftest is testing]]))

(pods/load-pod "./pod-ilmoraunio-conftest")
(require '[pod.ilmoraunio.conftest :as conftest])

(deftest parse-test
  (testing "parse"
    (is (= {"test-resources/test.json" {"hello" [1.0 2.0 4.0]},
            "test-resources/test.edn" {":foo" ":bar"},
            "test-resources/test.yaml" {"apiVersion" "v1",
                                        "kind" "Service",
                                        "metadata" {"name" "hello-kubernetes"},
                                        "spec" {"type" "LoadBalancer",
                                                "ports" [{"port" 80.0, "targetPort" 8080.0}],
                                                "selector" {"app" "hello-kubernetes"}}}}
           (conftest/parse "test-resources/test.json" "test-resources/test.edn" "test-resources/test.yaml"))))
  (testing "parse-as"
    (is (= {"test-resources/hocon.conf" {"play" {"server" {"websocket" {"frame" {"maxLength" "64k"}}, "debug" {"addDebugInfoToRequests" false}, "dir" "<<unknown value>>", "http" {"address" "0.0.0.0", "idleTimeout" "\"75 seconds\"", "port" 9001.0}, "https" {"address" "0.0.0.0", "engineProvider" "play.core.server.ssl.DefaultSSLEngineProvider", "idleTimeout" "\"75 seconds\"", "keyStore" {"algorithm" "<<unknown value>>", "password" "\"\"", "path" "<<unknown value>>", "type" "JKS"}, "needClientAuth" false, "port" "<<unknown value>>", "trustStore" {"noCaVerification" false}, "wantClientAuth" false}, "pidfile" {"path" "<<unknown value>>"}}, "editor" "<<unknown value>>"}}}
           (conftest/parse-as "hocon" "test-resources/hocon.conf")))
    (testing "actually-json.xml"
      (is (= {"test-resources/actually-json.xml" "{\"hello\": [1, 2, 4]}"}
             (conftest/parse "test-resources/actually-json.xml"))
          "`parse` will use an xml parser due to incorrect file extension")
      (is (= {"test-resources/actually-json.xml" {"hello" [1.0 2.0 4.0]}}
             (conftest/parse-as "json" "test-resources/actually-json.xml"))
          "`parse-as` with explicit parser as arg returns correct output"))))

(ns api-test
  (:require [babashka.pods :as pods]
            [clojure.test :refer [deftest is testing]]))

(pods/load-pod "./pod-ilmoraunio-conftest")
(require '[pod.ilmoraunio.conftest :as conftest])

(deftest parse-test
  (testing "parse smoke test"
    (is (= {"test-resources/test.json" {"hello" [1.0 2.0 4.0]},
            "test-resources/test.edn" {":foo" ":bar"},
            "test-resources/test.yaml" {"apiVersion" "v1",
                                        "kind" "Service",
                                        "metadata" {"name" "hello-kubernetes"},
                                        "spec" {"type" "LoadBalancer",
                                                "ports" [{"port" 80.0, "targetPort" 8080.0}],
                                                "selector" {"app" "hello-kubernetes"}}}}
           (conftest/parse "test-resources/test.json" "test-resources/test.edn" "test-resources/test.yaml")))))

(ns api-test
  (:require [babashka.pods :as pods]
            [clojure.test :refer [deftest is testing]]
            [matcher-combinators.test]))

(pods/load-pod "./pod-ilmoraunio-conftest")
(require '[pod.ilmoraunio.conftest :as conftest])

(deftest parse-test
  (testing "parse"
    (is (match? {"test-resources/test.dockerignore" [[{"Value" #".idea\r?", "Original" #".idea\r?", "Kind" "Path"}
                                                      {"Kind" "Empty", "Value" "", "Original" ""}]],
                 "test-resources/test.env" {"APP_NAME" "test", "MYSQL_USER" "user2"},
                 "test-resources/test.toml" {"defaultEntryPoints" ["http" "https"],
                                             "entryPoints" {"http" {"proxyProtocol" {"insecure" true,
                                                                                     "trustedIPs" ["10.10.10.1" "10.10.10.2"]},
                                                                    "forwardedHeaders" {"trustedIPs" ["10.10.10.1" "10.10.10.2"]},
                                                                    "address" ":80",
                                                                    "compress" true,
                                                                    "whitelist" {"sourceRange" ["10.42.0.0/16"
                                                                                                "152.89.1.33/32"
                                                                                                "afed:be44::/16"],
                                                                                 "useXForwardedFor" true},
                                                                    "tls" {"minVersion" "VersionTLS12",
                                                                           "cipherSuites" ["TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256"
                                                                                           "TLS_RSA_WITH_AES_256_GCM_SHA384"],
                                                                           "certificates" [{"certFile" "path/to/my.cert",
                                                                                            "keyFile" "path/to/my.key"}
                                                                                           {"certFile" "path/to/other.cert",
                                                                                            "keyFile" "path/to/other.key"}],
                                                                           "clientCA" {"files" ["path/to/ca1.crt" "path/to/ca2.crt"],
                                                                                       "optional" false}},
                                                                    "redirect" {"entryPoint" "https",
                                                                                "regex" "^http://localhost/(.*)",
                                                                                "replacement" "http://mydomain/$1",
                                                                                "permanent" true},
                                                                    "auth" {"forward" {"tls" {"caOptional" true,
                                                                                              "cert" "path/to/foo.cert",
                                                                                              "key" "path/to/foo.key",
                                                                                              "insecureSkipVerify" true,
                                                                                              "ca" "path/to/local.crt"},
                                                                                       "address" "https://authserver.com/auth",
                                                                                       "trustForwardHeader" true,
                                                                                       "authResponseHeaders" ["X-Auth-User"]},
                                                                            "headerField" "X-WebAuth-User",
                                                                            "basic" {"removeHeader" true,
                                                                                     "users" ["test:$apr1$H6uskkkW$IgXLP6ewTrSuBkTrqE8wj/"
                                                                                              "test2:$apr1$d9hr9HBB$4HxwgUir3HP4EsggP/QNo0"],
                                                                                     "usersFile" "/path/to/.htpasswd"},
                                                                            "digest" {"removeHeader" true,
                                                                                      "users" ["test:traefik:a2688e031edb4be6a3797f3882655c05"
                                                                                               "test2:traefik:518845800f9e2bfb1f1f740ec24f074e"],
                                                                                      "usersFile" "/path/to/.htdigest"}}}}},
                 "test-resources/test.gitignore" [[{"Kind" "Path", "Value" #"foo\r?", "Original" #"foo\r?"}
                                                   {"Value" #"bar\r?", "Original" #"!bar\r?", "Kind" "NegatedPath"}
                                                   {"Kind" #"(Empty|Path)", "Value" #"\r?", "Original" #"\r?"}
                                                   {"Kind" "Comment", "Value" "Baz", "Original" #"# Baz\r?"}
                                                   {"Kind" "Path", "Value" #"qux/\r?", "Original" #"qux/\r?"}
                                                   {"Kind" "Empty", "Value" "", "Original" ""}]],
                 "test-resources/test.vcl" {"acl" {"purge" ["127.0.0.1"]},
                                            "backend" {"app" {"connect_timeout" 60.0,
                                                              "first_byte_timeout" 60.0,
                                                              "host" "127.0.0.1",
                                                              "max_connections" 800.0,
                                                              "port" "8081",
                                                              "between_bytes_timeout" 60.0}}},
                 "test-resources/test.Dockerfile" [[{"Cmd" "from",
                                                     "SubCmd" "",
                                                     "JSON" false,
                                                     "Flags" [],
                                                     "Value" ["openjdk:8-jdk-alpine"],
                                                     "Stage" 0.0}
                                                    {"Cmd" "volume",
                                                     "SubCmd" "",
                                                     "JSON" false,
                                                     "Flags" [],
                                                     "Value" ["/tmp"],
                                                     "Stage" 0.0}
                                                    {"Value" ["DEPENDENCY=target/dependency"],
                                                     "Stage" 0.0,
                                                     "Cmd" "arg",
                                                     "SubCmd" "",
                                                     "JSON" false,
                                                     "Flags" []}
                                                    {"Cmd" "copy",
                                                     "SubCmd" "",
                                                     "JSON" false,
                                                     "Flags" [],
                                                     "Value" ["${DEPENDENCY}/BOOT-INF/lib" "/app/lib"],
                                                     "Stage" 0.0}
                                                    {"Cmd" "copy",
                                                     "SubCmd" "",
                                                     "JSON" false,
                                                     "Flags" [],
                                                     "Value" ["${DEPENDENCY}/META-INF" "/app/META-INF"],
                                                     "Stage" 0.0}
                                                    {"Cmd" "copy",
                                                     "SubCmd" "",
                                                     "JSON" false,
                                                     "Flags" [],
                                                     "Value" ["${DEPENDENCY}/BOOT-INF/classes" "/app"],
                                                     "Stage" 0.0}
                                                    {"SubCmd" "",
                                                     "JSON" false,
                                                     "Flags" [],
                                                     "Value" ["apk add --no-cache python3 python3-dev build-base && pip3 install awscli==1.18.1"],
                                                     "Stage" 0.0,
                                                     "Cmd" "run"}
                                                    {"Stage" 0.0,
                                                     "Cmd" "entrypoint",
                                                     "SubCmd" "",
                                                     "JSON" true,
                                                     "Flags" [],
                                                     "Value" ["java" "-cp" "app:app/lib/*" "hello.Application"]}]],
                 "test-resources/test.cue" {"spec" {"replicas" 3.0,
                                                    "selector" {"matchLabels" {"app" "hello-kubernetes"}},
                                                    "template" {"spec" {"containers" [{"ports" [{"containerPort" 8081.0}],
                                                                                       "name" "hello-kubernetes",
                                                                                       "image" "paulbouwer/hello-kubernetes:1.5"}]},
                                                                "metadata" {"labels" {"app" "hello-kubernetes"}}}},
                                            "apiVersion" "apps/v1",
                                            "kind" "Deployment",
                                            "metadata" {"name" "hello-kubernetes"}},
                 "test-resources/test.jsonnet" {"str1" "The value of self.ex2 is 3.",
                                                "concat_array" [1.0 2.0 3.0 4.0],
                                                "obj" {"a" 1.0, "b" 3.0, "c" 4.0},
                                                "str5" #"ex1=1.67\r?\nex2=3.00\r?\n",
                                                "str2" "The value of self.ex2 is 3.",
                                                "obj_member" true,
                                                "concat_string" "1234",
                                                "str4" "ex1=1.67, ex2=3.00",
                                                "equality1" false,
                                                "ex4" true,
                                                "ex1" 1.6666666666666665,
                                                "ex2" 3.0,
                                                "equality2" true,
                                                "str3" "ex1=1.67, ex2=3.00",
                                                "ex3" 1.6666666666666665},
                 "test-resources/test.yaml" {"kind" "Service",
                                             "metadata" {"name" "hello-kubernetes"},
                                             "spec" {"ports" [{"port" 80.0, "targetPort" 8080.0}],
                                                     "selector" {"app" "hello-kubernetes"},
                                                     "type" "LoadBalancer"},
                                             "apiVersion" "v1"},
                 "test-resources/test.edn" {":foo" ":bar", ":duration" "#duration 20m"},
                 "test-resources/test.spdx" {"documentNamespace" "https://swinslow.net/spdx-examples/example1/hello-v3",
                                             "creationInfo" {"creators" ["Person: Steve Winslow (steve@swinslow.net)"
                                                                         "Tool: github.com/spdx/tools-golang/builder"
                                                                         "Tool: github.com/spdx/tools-golang/idsearcher"],
                                                             "created" "2021-08-26T01:46:00Z"},
                                             "spdxVersion" "SPDX-2.3",
                                             "dataLicense" "conftest-demo",
                                             "SPDXID" "SPDXRef-DOCUMENT",
                                             "name" "hello"},
                 "test-resources/test.ini" {"auth" {"disable_login_form" false,
                                                    "login_cookie_name" "grafana_session",
                                                    "login_maximum_inactive_lifetime_days" 7.0,
                                                    "login_maximum_lifetime_days" 30.0,
                                                    "token_rotation_interval_minutes" 10.0},
                                            "auth.basic" {"enabled" true},
                                            "server" {"http_port" 3000.0,
                                                      "protocol" "http",
                                                      "serve_from_sub_path" false,
                                                      "http_addr" "",
                                                      "enforce_domain" false,
                                                      "static_root_path" "public",
                                                      "root_url" "%(protocol)s://%(domain)s:%(http_port)s/",
                                                      "domain" "localhost",
                                                      "enable_gzip" false,
                                                      "router_logging" false},
                                            "users" {"viewers_can_edit" false,
                                                     "allow_sign_up" false,
                                                     "password_hint" "password",
                                                     "auto_assign_org" true,
                                                     "verify_email_enabled" false,
                                                     "auto_assign_org_id" 1.0,
                                                     "login_hint" "email or username",
                                                     "auto_assign_org_role" "Viewer",
                                                     "allow_org_create" false,
                                                     "editors_can_admin" false,
                                                     "default_theme" "dark"},
                                            "alerting" {"execute_alerts" true,
                                                        "max_attempts" 3.0,
                                                        "nodata_or_nullvalues" "no_data",
                                                        "notification_timeout_seconds" 30.0,
                                                        "concurrent_render_limit" 5.0,
                                                        "enabled" true,
                                                        "error_or_timeout" "alerting",
                                                        "evaluation_timeout_seconds" 30.0}},,
                 "test-resources/test.hcl2.tf" {"resource" {"aws_alb_listener" {"my-alb-listener" [{"port" "80", "protocol" "HTTP"}]},
                                                            "aws_db_security_group" {"my-group" [{}]},
                                                            "aws_s3_bucket" {"valid" [{"acl" "private",
                                                                                       "bucket" "validBucket",
                                                                                       "tags" {"environment" "prod", "owner" "devops"}}]},
                                                            "aws_security_group_rule" {"my-rule" [{"cidr_blocks" ["0.0.0.0/0"],
                                                                                                   "type" "ingress"}]},
                                                            "azurerm_managed_disk" {"source" [{"encryption_settings" [{"enabled" false}]}]}}},
                 "test-resources/test.properties" {"other.value.url" "https://example.com/",
                                                   "secret.value.exception" "f9761ebe-d4dc-11eb-8046-1e00e20cdb95",
                                                   "SAMPLE_VALUE" "something-here"},
                 "test-resources/test.xml" {"project" {"properties" {"activejdbc.version" "2.3",
                                                                     "environments" "development.test,development"},
                                                       "groupId" "org.javalite",
                                                       "dependencies" {"dependency" [{"groupId" "junit",
                                                                                      "artifactId" "junit",
                                                                                      "version" "4.13.1",
                                                                                      "scope" "test"}
                                                                                     {"version" "${activejdbc.version}",
                                                                                      "exclusions" {"exclusion" {"groupId" "opensymphony",
                                                                                                                 "artifactId" "oscache"}},
                                                                                      "groupId" "org.javalite",
                                                                                      "artifactId" "activejdbc"}
                                                                                     {"groupId" "mysql",
                                                                                      "artifactId" "mysql-connector-java",
                                                                                      "version" "5.1.34"}
                                                                                     {"artifactId" "slf4j-simple",
                                                                                      "version" "1.7.9",
                                                                                      "groupId" "org.slf4j"}]},
                                                       "packaging" "jar",
                                                       "name" "ActiveJDBC - Simple Maven Example",
                                                       "-schemaLocation" "http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd",
                                                       "modelVersion" "4.0.0",
                                                       "build" {"plugins" {"plugin" [{"configuration" {"source" "1.8",
                                                                                                       "target" "1.8",
                                                                                                       "encoding" "UTF-8"},
                                                                                      "groupId" "org.apache.maven.plugins",
                                                                                      "artifactId" "maven-compiler-plugin",
                                                                                      "version" "3.6.0"}
                                                                                     {"groupId" "org.javalite",
                                                                                      "artifactId" "activejdbc-instrumentation",
                                                                                      "version" "${activejdbc.version}",
                                                                                      "executions" {"execution" {"phase" "process-classes",
                                                                                                                 "goals" {"goal" "instrument"}}}}
                                                                                     {"dependencies" {"dependency" {"groupId" "mysql",
                                                                                                                    "artifactId" "mysql-connector-java",
                                                                                                                    "version" "5.1.34"}},
                                                                                      "groupId" "org.javalite",
                                                                                      "artifactId" "db-migrator-maven-plugin",
                                                                                      "version" "${activejdbc.version}",
                                                                                      "configuration" {"configFile" "${project.basedir}/src/main/resources/database.properties",
                                                                                                       "environments" "${environments}"},
                                                                                      "executions" {"execution" {"id" "dev_migrations",
                                                                                                                 "phase" "validate",
                                                                                                                 "goals" {"goal" "migrate"}}}}
                                                                                     {"groupId" "org.apache.maven.plugins",
                                                                                      "artifactId" "maven-surefire-plugin",
                                                                                      "version" "2.18.1",
                                                                                      "configuration" {"trimStackTrace" "true",
                                                                                                       "useFile" "false",
                                                                                                       "includes" {"include" ["**/*Spec*.java"
                                                                                                                              "**/*Test*.java"]},
                                                                                                       "excludes" {"exclude" ["**/helpers/*"
                                                                                                                              "**/*$*"]},
                                                                                                       "reportFormat" "brief"}}]}},
                                                       "-xmlns" "http://maven.apache.org/POM/4.0.0",
                                                       "version" "1.0-SNAPSHOT",
                                                       "-xsi" "http://www.w3.org/2001/XMLSchema-instance",
                                                       "artifactId" "simple-example"}},
                 "test-resources/test.json" {"hello" [1.0 2.0 4.0], "@foo" "bar"}}
                (conftest/parse "test-resources/test.cue"
                                "test-resources/test.Dockerfile"
                                "test-resources/test.dockerignore"
                                "test-resources/test.env"
                                "test-resources/test.edn"
                                "test-resources/test.gitignore"
                                "test-resources/test.hcl2.tf"
                                "test-resources/test.ini"
                                "test-resources/test.json"
                                "test-resources/test.jsonnet"
                                "test-resources/test.properties"
                                "test-resources/test.spdx"
                                "test-resources/test.toml"
                                "test-resources/test.vcl"
                                "test-resources/test.xml"
                                "test-resources/test.yaml"))))
  (testing "parse-as"
    (is (match? {"test-resources/test.dockerignore" [[{"Value" #".idea\r?", "Original" #".idea\r?", "Kind" "Path"}
                                                      {"Kind" "Empty", "Value" "", "Original" ""}]],
                 "test-resources/test.env" {"APP_NAME" "test", "MYSQL_USER" "user2"},
                 "test-resources/test.toml" {"defaultEntryPoints" ["http" "https"],
                                             "entryPoints" {"http" {"proxyProtocol" {"insecure" true,
                                                                                     "trustedIPs" ["10.10.10.1" "10.10.10.2"]},
                                                                    "forwardedHeaders" {"trustedIPs" ["10.10.10.1" "10.10.10.2"]},
                                                                    "address" ":80",
                                                                    "compress" true,
                                                                    "whitelist" {"sourceRange" ["10.42.0.0/16"
                                                                                                "152.89.1.33/32"
                                                                                                "afed:be44::/16"],
                                                                                 "useXForwardedFor" true},
                                                                    "tls" {"minVersion" "VersionTLS12",
                                                                           "cipherSuites" ["TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256"
                                                                                           "TLS_RSA_WITH_AES_256_GCM_SHA384"],
                                                                           "certificates" [{"certFile" "path/to/my.cert",
                                                                                            "keyFile" "path/to/my.key"}
                                                                                           {"certFile" "path/to/other.cert",
                                                                                            "keyFile" "path/to/other.key"}],
                                                                           "clientCA" {"files" ["path/to/ca1.crt" "path/to/ca2.crt"],
                                                                                       "optional" false}},
                                                                    "redirect" {"entryPoint" "https",
                                                                                "regex" "^http://localhost/(.*)",
                                                                                "replacement" "http://mydomain/$1",
                                                                                "permanent" true},
                                                                    "auth" {"forward" {"tls" {"caOptional" true,
                                                                                              "cert" "path/to/foo.cert",
                                                                                              "key" "path/to/foo.key",
                                                                                              "insecureSkipVerify" true,
                                                                                              "ca" "path/to/local.crt"},
                                                                                       "address" "https://authserver.com/auth",
                                                                                       "trustForwardHeader" true,
                                                                                       "authResponseHeaders" ["X-Auth-User"]},
                                                                            "headerField" "X-WebAuth-User",
                                                                            "basic" {"removeHeader" true,
                                                                                     "users" ["test:$apr1$H6uskkkW$IgXLP6ewTrSuBkTrqE8wj/"
                                                                                              "test2:$apr1$d9hr9HBB$4HxwgUir3HP4EsggP/QNo0"],
                                                                                     "usersFile" "/path/to/.htpasswd"},
                                                                            "digest" {"removeHeader" true,
                                                                                      "users" ["test:traefik:a2688e031edb4be6a3797f3882655c05"
                                                                                               "test2:traefik:518845800f9e2bfb1f1f740ec24f074e"],
                                                                                      "usersFile" "/path/to/.htdigest"}}}}},
                 "test-resources/test.gitignore" [[{"Kind" "Path", "Value" #"foo\r?", "Original" #"foo\r?"}
                                                   {"Value" #"bar\r?", "Original" #"!bar\r?", "Kind" "NegatedPath"}
                                                   {"Kind" #"(Empty|Path)", "Value" #"\r?", "Original" #"\r?"}
                                                   {"Kind" "Comment", "Value" "Baz", "Original" #"# Baz\r?"}
                                                   {"Kind" "Path", "Value" #"qux/\r?", "Original" #"qux/\r?"}
                                                   {"Kind" "Empty", "Value" "", "Original" ""}]],
                 "test-resources/test.vcl" {"acl" {"purge" ["127.0.0.1"]},
                                            "backend" {"app" {"connect_timeout" 60.0,
                                                              "first_byte_timeout" 60.0,
                                                              "host" "127.0.0.1",
                                                              "max_connections" 800.0,
                                                              "port" "8081",
                                                              "between_bytes_timeout" 60.0}}},
                 "test-resources/test.Dockerfile" [[{"Cmd" "from",
                                                     "SubCmd" "",
                                                     "JSON" false,
                                                     "Flags" [],
                                                     "Value" ["openjdk:8-jdk-alpine"],
                                                     "Stage" 0.0}
                                                    {"Cmd" "volume",
                                                     "SubCmd" "",
                                                     "JSON" false,
                                                     "Flags" [],
                                                     "Value" ["/tmp"],
                                                     "Stage" 0.0}
                                                    {"Value" ["DEPENDENCY=target/dependency"],
                                                     "Stage" 0.0,
                                                     "Cmd" "arg",
                                                     "SubCmd" "",
                                                     "JSON" false,
                                                     "Flags" []}
                                                    {"Cmd" "copy",
                                                     "SubCmd" "",
                                                     "JSON" false,
                                                     "Flags" [],
                                                     "Value" ["${DEPENDENCY}/BOOT-INF/lib" "/app/lib"],
                                                     "Stage" 0.0}
                                                    {"Cmd" "copy",
                                                     "SubCmd" "",
                                                     "JSON" false,
                                                     "Flags" [],
                                                     "Value" ["${DEPENDENCY}/META-INF" "/app/META-INF"],
                                                     "Stage" 0.0}
                                                    {"Cmd" "copy",
                                                     "SubCmd" "",
                                                     "JSON" false,
                                                     "Flags" [],
                                                     "Value" ["${DEPENDENCY}/BOOT-INF/classes" "/app"],
                                                     "Stage" 0.0}
                                                    {"SubCmd" "",
                                                     "JSON" false,
                                                     "Flags" [],
                                                     "Value" ["apk add --no-cache python3 python3-dev build-base && pip3 install awscli==1.18.1"],
                                                     "Stage" 0.0,
                                                     "Cmd" "run"}
                                                    {"Stage" 0.0,
                                                     "Cmd" "entrypoint",
                                                     "SubCmd" "",
                                                     "JSON" true,
                                                     "Flags" [],
                                                     "Value" ["java" "-cp" "app:app/lib/*" "hello.Application"]}]],
                 "test-resources/test.cue" {"spec" {"replicas" 3.0,
                                                    "selector" {"matchLabels" {"app" "hello-kubernetes"}},
                                                    "template" {"spec" {"containers" [{"ports" [{"containerPort" 8081.0}],
                                                                                       "name" "hello-kubernetes",
                                                                                       "image" "paulbouwer/hello-kubernetes:1.5"}]},
                                                                "metadata" {"labels" {"app" "hello-kubernetes"}}}},
                                            "apiVersion" "apps/v1",
                                            "kind" "Deployment",
                                            "metadata" {"name" "hello-kubernetes"}},
                 "test-resources/test.jsonnet" {"str1" "The value of self.ex2 is 3.",
                                                "concat_array" [1.0 2.0 3.0 4.0],
                                                "obj" {"a" 1.0, "b" 3.0, "c" 4.0},
                                                "str5" #"ex1=1.67\r?\nex2=3.00\r?\n",
                                                "str2" "The value of self.ex2 is 3.",
                                                "obj_member" true,
                                                "concat_string" "1234",
                                                "str4" "ex1=1.67, ex2=3.00",
                                                "equality1" false,
                                                "ex4" true,
                                                "ex1" 1.6666666666666665,
                                                "ex2" 3.0,
                                                "equality2" true,
                                                "str3" "ex1=1.67, ex2=3.00",
                                                "ex3" 1.6666666666666665},
                 "test-resources/test.yaml" {"kind" "Service",
                                             "metadata" {"name" "hello-kubernetes"},
                                             "spec" {"ports" [{"port" 80.0, "targetPort" 8080.0}],
                                                     "selector" {"app" "hello-kubernetes"},
                                                     "type" "LoadBalancer"},
                                             "apiVersion" "v1"},
                 "test-resources/test.edn" {":foo" ":bar", ":duration" "#duration 20m"},
                 "test-resources/test.spdx" {"documentNamespace" "https://swinslow.net/spdx-examples/example1/hello-v3",
                                             "creationInfo" {"creators" ["Person: Steve Winslow (steve@swinslow.net)"
                                                                         "Tool: github.com/spdx/tools-golang/builder"
                                                                         "Tool: github.com/spdx/tools-golang/idsearcher"],
                                                             "created" "2021-08-26T01:46:00Z"},
                                             "spdxVersion" "SPDX-2.3",
                                             "dataLicense" "conftest-demo",
                                             "SPDXID" "SPDXRef-DOCUMENT",
                                             "name" "hello"},
                 "test-resources/test.ini" {"auth" {"disable_login_form" false,
                                                    "login_cookie_name" "grafana_session",
                                                    "login_maximum_inactive_lifetime_days" 7.0,
                                                    "login_maximum_lifetime_days" 30.0,
                                                    "token_rotation_interval_minutes" 10.0},
                                            "auth.basic" {"enabled" true},
                                            "server" {"http_port" 3000.0,
                                                      "protocol" "http",
                                                      "serve_from_sub_path" false,
                                                      "http_addr" "",
                                                      "enforce_domain" false,
                                                      "static_root_path" "public",
                                                      "root_url" "%(protocol)s://%(domain)s:%(http_port)s/",
                                                      "domain" "localhost",
                                                      "enable_gzip" false,
                                                      "router_logging" false},
                                            "users" {"viewers_can_edit" false,
                                                     "allow_sign_up" false,
                                                     "password_hint" "password",
                                                     "auto_assign_org" true,
                                                     "verify_email_enabled" false,
                                                     "auto_assign_org_id" 1.0,
                                                     "login_hint" "email or username",
                                                     "auto_assign_org_role" "Viewer",
                                                     "allow_org_create" false,
                                                     "editors_can_admin" false,
                                                     "default_theme" "dark"},
                                            "alerting" {"execute_alerts" true,
                                                        "max_attempts" 3.0,
                                                        "nodata_or_nullvalues" "no_data",
                                                        "notification_timeout_seconds" 30.0,
                                                        "concurrent_render_limit" 5.0,
                                                        "enabled" true,
                                                        "error_or_timeout" "alerting",
                                                        "evaluation_timeout_seconds" 30.0}},,
                 "test-resources/test.hcl1.tf" {"provider" [{"google" [{"project" "instrumenta",
                                                                        "region" "europe-west2",
                                                                        "version" "2.5.0"}]}],
                                                "resource" [{"google_container_cluster" [{"primary" [{"initial_node_count" 1,
                                                                                                      "master_auth" [{"username" "",
                                                                                                                      "password" ""}],
                                                                                                      "name" "my-gke-cluster",
                                                                                                      "location" "us-central1",
                                                                                                      "remove_default_node_pool" true}]}]}
                                                            {"google_container_node_pool" [{"primary_preemptible_nodes" [{"node_config" [{"metadata" [{"disable-legacy-endpoints" "true"}],
                                                                                                                                          "oauth_scopes" ["https://www.googleapis.com/auth/logging.write"
                                                                                                                                                          "https://www.googleapis.com/auth/monitoring"],
                                                                                                                                          "preemptible" true,
                                                                                                                                          "machine_type" "n1-standard-1"}],
                                                                                                                          "name" "my-node-pool",
                                                                                                                          "location" "us-central1",
                                                                                                                          "cluster" "${google_container_cluster.primary.name}",
                                                                                                                          "node_count" 1}]}]}],
                                                "output" [{"client_certificate" [{"value" "${google_container_cluster.primary.master_auth.0.client_certificate}"}]}
                                                          {"client_key" [{"value" "${google_container_cluster.primary.master_auth.0.client_key}"}]}
                                                          {"cluster_ca_certificate" [{"value" "${google_container_cluster.primary.master_auth.0.cluster_ca_certificate}"}]}]}
                 "test-resources/test.hcl2.tf" {"resource" {"aws_alb_listener" {"my-alb-listener" [{"port" "80", "protocol" "HTTP"}]},
                                                            "aws_db_security_group" {"my-group" [{}]},
                                                            "aws_s3_bucket" {"valid" [{"acl" "private",
                                                                                       "bucket" "validBucket",
                                                                                       "tags" {"environment" "prod", "owner" "devops"}}]},
                                                            "aws_security_group_rule" {"my-rule" [{"cidr_blocks" ["0.0.0.0/0"],
                                                                                                   "type" "ingress"}]},
                                                            "azurerm_managed_disk" {"source" [{"encryption_settings" [{"enabled" false}]}]}}},
                 "test-resources/test.properties" {"other.value.url" "https://example.com/",
                                                   "secret.value.exception" "f9761ebe-d4dc-11eb-8046-1e00e20cdb95",
                                                   "SAMPLE_VALUE" "something-here"},
                 "test-resources/test.xml" {"project" {"properties" {"activejdbc.version" "2.3",
                                                                     "environments" "development.test,development"},
                                                       "groupId" "org.javalite",
                                                       "dependencies" {"dependency" [{"groupId" "junit",
                                                                                      "artifactId" "junit",
                                                                                      "version" "4.13.1",
                                                                                      "scope" "test"}
                                                                                     {"version" "${activejdbc.version}",
                                                                                      "exclusions" {"exclusion" {"groupId" "opensymphony",
                                                                                                                 "artifactId" "oscache"}},
                                                                                      "groupId" "org.javalite",
                                                                                      "artifactId" "activejdbc"}
                                                                                     {"groupId" "mysql",
                                                                                      "artifactId" "mysql-connector-java",
                                                                                      "version" "5.1.34"}
                                                                                     {"artifactId" "slf4j-simple",
                                                                                      "version" "1.7.9",
                                                                                      "groupId" "org.slf4j"}]},
                                                       "packaging" "jar",
                                                       "name" "ActiveJDBC - Simple Maven Example",
                                                       "-schemaLocation" "http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd",
                                                       "modelVersion" "4.0.0",
                                                       "build" {"plugins" {"plugin" [{"configuration" {"source" "1.8",
                                                                                                       "target" "1.8",
                                                                                                       "encoding" "UTF-8"},
                                                                                      "groupId" "org.apache.maven.plugins",
                                                                                      "artifactId" "maven-compiler-plugin",
                                                                                      "version" "3.6.0"}
                                                                                     {"groupId" "org.javalite",
                                                                                      "artifactId" "activejdbc-instrumentation",
                                                                                      "version" "${activejdbc.version}",
                                                                                      "executions" {"execution" {"phase" "process-classes",
                                                                                                                 "goals" {"goal" "instrument"}}}}
                                                                                     {"dependencies" {"dependency" {"groupId" "mysql",
                                                                                                                    "artifactId" "mysql-connector-java",
                                                                                                                    "version" "5.1.34"}},
                                                                                      "groupId" "org.javalite",
                                                                                      "artifactId" "db-migrator-maven-plugin",
                                                                                      "version" "${activejdbc.version}",
                                                                                      "configuration" {"configFile" "${project.basedir}/src/main/resources/database.properties",
                                                                                                       "environments" "${environments}"},
                                                                                      "executions" {"execution" {"id" "dev_migrations",
                                                                                                                 "phase" "validate",
                                                                                                                 "goals" {"goal" "migrate"}}}}
                                                                                     {"groupId" "org.apache.maven.plugins",
                                                                                      "artifactId" "maven-surefire-plugin",
                                                                                      "version" "2.18.1",
                                                                                      "configuration" {"trimStackTrace" "true",
                                                                                                       "useFile" "false",
                                                                                                       "includes" {"include" ["**/*Spec*.java"
                                                                                                                              "**/*Test*.java"]},
                                                                                                       "excludes" {"exclude" ["**/helpers/*"
                                                                                                                              "**/*$*"]},
                                                                                                       "reportFormat" "brief"}}]}},
                                                       "-xmlns" "http://maven.apache.org/POM/4.0.0",
                                                       "version" "1.0-SNAPSHOT",
                                                       "-xsi" "http://www.w3.org/2001/XMLSchema-instance",
                                                       "artifactId" "simple-example"}},
                 "test-resources/test.json" {"hello" [1.0 2.0 4.0], "@foo" "bar"}}
                (into {}
                      (mapcat (fn [[config-format path]]
                                (conftest/parse-as config-format path))
                              [["cue" "test-resources/test.cue"]
                               ["dockerfile" "test-resources/test.Dockerfile"]
                               ["ignore" "test-resources/test.dockerignore"]
                               ["properties" "test-resources/test.env"]
                               ["edn" "test-resources/test.edn"]
                               ["ignore" "test-resources/test.gitignore"]
                               ["hcl1" "test-resources/test.hcl1.tf"]
                               ["hcl2" "test-resources/test.hcl2.tf"]
                               ["ini" "test-resources/test.ini"]
                               ["json" "test-resources/test.json"]
                               ["jsonnet" "test-resources/test.jsonnet"]
                               ["properties" "test-resources/test.properties"]
                               ["spdx" "test-resources/test.spdx"]
                               ["toml" "test-resources/test.toml"]
                               ["vcl" "test-resources/test.vcl"]
                               ["xml" "test-resources/test.xml"]
                               ["yaml" "test-resources/test.yaml"]]))))
    (is (= {"test-resources/hocon.conf" {"play" {"server" {"websocket" {"frame" {"maxLength" "64k"}}, "debug" {"addDebugInfoToRequests" false}, "dir" "<<unknown value>>", "http" {"address" "0.0.0.0", "idleTimeout" "\"75 seconds\"", "port" 9001.0}, "https" {"address" "0.0.0.0", "engineProvider" "play.core.server.ssl.DefaultSSLEngineProvider", "idleTimeout" "\"75 seconds\"", "keyStore" {"algorithm" "<<unknown value>>", "password" "\"\"", "path" "<<unknown value>>", "type" "JKS"}, "needClientAuth" false, "port" "<<unknown value>>", "trustStore" {"noCaVerification" false}, "wantClientAuth" false}, "pidfile" {"path" "<<unknown value>>"}}, "editor" "<<unknown value>>"}}}
           (conftest/parse-as "hocon" "test-resources/hocon.conf")))
    (testing "actually-json.xml"
      (is (= {"test-resources/actually-json.xml" "{\"hello\": [1, 2, 4]}"}
             (conftest/parse "test-resources/actually-json.xml"))
          "`parse` will use an xml parser due to incorrect file extension")
      (is (= {"test-resources/actually-json.xml" {"hello" [1.0 2.0 4.0]}}
             (conftest/parse-as "json" "test-resources/actually-json.xml"))
          "`parse-as` with explicit parser as arg returns correct output"))))

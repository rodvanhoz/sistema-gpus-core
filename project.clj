(defproject sistema-gpus-core "0.1.0-SNAPSHOT"
  :description "Sistema para gerenciar cadastros e testes de GPUs"
  :url "http://example.com/sistema-gpus-core"
  :min-lein-version "2.0.0"
  :resource-paths ["config" "src/main/resources"]
  :jvm-opts ["-Duser.timezone=UTC" "-Duser.country=BR" "-Duser.language=pt"]

  :dependencies [[org.clojure/clojure "1.10.0"]
                 [compojure "1.6.1"]
                 [ring/ring-defaults "0.3.2"]
                 [org.postgresql/postgresql "42.1.4"]
                 [metosin/ring-http-response "0.9.0"]
                 [clj-time "0.15.2"]
                 [ring/ring-json "0.4.0"]
                 [ring-cors "0.1.13"]
                 [toucan "1.15.1"]
                 [ring-logger "1.0.1"]
                 [ring/ring-mock "0.3.2"]
                 [ch.qos.logback/logback-classic "1.2.11"]
                 [org.clojure/tools.logging "1.2.4"]
                 [org.slf4j/slf4j-api "1.7.26"]
                 [org.slf4j/jul-to-slf4j "1.7.25"]
                 [org.slf4j/jcl-over-slf4j "1.7.25"]
                 [org.slf4j/log4j-over-slf4j "1.7.26"]
                 [org.clojure/java.jdbc "0.7.10"]
                 [com.mchange/c3p0 "0.9.5.2"]
                 [environ "1.2.0"]
                 [http-kit/http-kit "2.8.0"]
                 ;; ----------------------------
                 ;; Dependências do Testcontainers
                 ;; ----------------------------
                 [org.testcontainers/testcontainers "1.19.0"]
                 [org.testcontainers/postgresql "1.19.0"]
                 ;; Caso queira wrapper em Clojure para Testcontainers
                 [clj-test-containers "0.7.4"]
                 [com.stuartsierra/component "1.1.0"]]

  :profiles {:uberjar {:jar-name "sistema-gpus-core-single.jar"
                       :aot          [main.core]
                       :uberjar-name "sistema-gpus-core.jar"}}

  :ring {:init   main.core/-main
         :handler sistema-gpus-core.handler/app
         :port    8000}

  :plugins [[lein-ring "0.12.5"]
            [lein-cljfmt "0.8.0"]]

  :aliases {"lint/format" ["cljfmt" "fix"]
            "lint/check"  ["cljfmt" "check"]})

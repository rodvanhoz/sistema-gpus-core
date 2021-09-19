(defproject sistema-gpus-core "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [compojure "1.6.1"]
                 [ring/ring-defaults "0.3.2"]
                 [org.clojure/tools.logging "1.1.0"]
                 [ring/ring-json "0.4.0"]
                 [ring-cors "0.1.13"]
                 [korma "0.5.0-RC1"]
                 [metosin/ring-http-response "0.9.0"]
                 [clj-time "0.15.2"]
                 [com.microsoft.sqlserver/mssql-jdbc "9.4.0.jre8"]]
  :plugins [[lein-ring "0.12.5"]]
  :ring {:handler sistema-gpus-core.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.2"]]}})

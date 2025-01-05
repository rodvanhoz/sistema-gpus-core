(ns sistema-gpus-core.core
  (:require [com.stuartsierra.component :as component]
            [sistema-gpus-core.system :refer [create-system]]
            [org.httpkit.server :as httpkit]
            [sistema-gpus-core.handler :refer [app]]
            [clojure.tools.logging :as log]))

(defn -main []
  (let [system (component/start (create-system))]
    (try
      (log/info "System started!")
      (let [port 8000]
        (log/info "Starting web server on port" port)
        ;; Sobe o servidor HTTPKit (ou Jetty, ou outro adaptador)
        (httpkit/run-server app {:port port})
        ;; Bloqueia a thread principal (para não sair do -main)
        @(promise))
      (finally
        (component/stop system)))))

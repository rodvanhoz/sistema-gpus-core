(ns sistema-gpus-core.core
  (:require [com.stuartsierra.component :as component]
            [sistema-gpus-core.system :refer [create-system]]
            [clojure.tools.logging :as log]))

(defn -main []
  (let [system (component/start (create-system))]
    (try
      (log/info "System started!")
      ;; Aqui você pode adicionar a lógica principal, por exemplo:
      ;; - inicializar um servidor web
      ;; - expor as rotas de API
      ;; - chamar funções do seu domain etc.
      (finally
        (component/stop system)))))

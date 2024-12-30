(ns sistema-gpus-core.components.storage
  (:require [com.stuartsierra.component :as component]
            [toucan.db :as db]))

(defrecord PostgresStorage [db-spec]
  component/Lifecycle
  (start [this]
    (println "Iniciando PostgresStorage...")
    (db/set-default-db-connection! db-spec)
    (assoc this :connected true))

  (stop [this]
    (println "Parando PostgresStorage...")
    (db/clear-default-db-connection!)
    (assoc this :connected false)))

(defn new-postgres-storage
  "Cria instância do componente PostgresStorage."
  [db-config]
  (map->PostgresStorage {:db-spec db-config}))

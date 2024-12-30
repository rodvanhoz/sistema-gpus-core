(ns sistema-gpus-core.system
  (:require [com.stuartsierra.component :as component]
            [sistema-gpus-core.components.storage :refer [new-postgres-storage]]))

(defn create-system []
  (component/system-map
   :storage
   (new-postgres-storage {:dbtype "postgresql"
                          :dbname (System/getenv "DB_NAME")
                          :host (System/getenv "DB_HOST")
                          :port (Integer. (System/getenv "DB_PORT"))
                          :user (System/getenv "DB_USER")
                          :password (System/getenv "DB_PASS")})))

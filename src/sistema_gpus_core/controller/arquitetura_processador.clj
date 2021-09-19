(ns sistema-gpus-core.controller.arquitetura-processador
  (:require [clojure.tools.logging :as log]
            [ring.util.http-response :refer [ok bad-request unauthorized internal-server-error no-content created not-found]]
            [sistema-gpus-core.db.arquitetura-processador :as db.arquitetura-processador]))

(defn get-all []
  (db.arquitetura-processador/get-all))
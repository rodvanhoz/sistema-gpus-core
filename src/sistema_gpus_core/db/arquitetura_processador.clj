(ns sistema-gpus-core.db.arquitetura-processador
  (:require [korma.db :refer :all]
    [korma.core :refer :all]
    [clojure.java.jdbc :as sql]
    [clojure.tools.logging :as log]
    [sistema-gpus-core.db :as  db]
    [sistema-gpus-core.db.entities :as e]))

(defn get [clauses]
  (select e/arquitetura-processador
    (where clauses)))

(defn get-all []
  (get true))
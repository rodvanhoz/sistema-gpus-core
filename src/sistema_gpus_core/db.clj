(ns sistema-gpus-core.db
  (:require [korma.db :as korma :refer [defdb]]))

;(Class/forName "com.mssql.jdbc.Driver")

(defn- db-conn []
  (korma/mssql
      {:db "Benchmarks" 
       :user "sa"
       :password "Geforce560ti" 
       :host "192.168.0.11" 
       :port "49168"
       :ssl false}))

(defdb db (db-conn))

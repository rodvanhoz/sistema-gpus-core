(ns sistema-gpus-core.helper.utils
  (:import (java.util UUID))
  (:require [clj-time.coerce :as c]))

(defn uuid
  "Retorna um novo UUID random."
  []
  (UUID/randomUUID))

(defn uuid-from-string
  "Converte string para UUID. Se já for UUID, retorna ele mesmo."
  [uuid]
  (when uuid
    (if (uuid? uuid)
      uuid
      (UUID/fromString uuid))))

(defn str->date
  "Converte string no formato \"yyyy-MM-dd HH:mm:ss.SSS\" (ou compatível)
   para um java.sql.Timestamp."
  [date-str]
  (when date-str
    (c/to-timestamp date-str)))

(defn date->str
  "Converte um java.sql.Timestamp (ou Date) para string no formato ISO."
  [date]
  (when date
    (str (c/from-sql-date date))))  ; c/from-sql-date retorna um DateTime do clj-time, e str converte para ISO8601

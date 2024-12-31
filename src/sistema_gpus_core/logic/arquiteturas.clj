(ns sistema-gpus-core.logic.arquiteturas
  (:require
   [sistema-gpus-core.helper.utils :refer [uuid-from-string]]))

(defn ->uuid-if-id
  "Se a chave k for algum campo de ID, converte v de string -> UUID.
     Caso contrário, retorna v inalterado."
  [k v]
  (case k
    :id_arquitetura       (uuid-from-string v)
      ;; fallback - não converte
    v))

(defn uuid->str
  [item]
  (-> item
      (update :id_arquitetura str)))

(defn string->uuid
  [item]
  (-> item
      (cond-> (:id_arquitetura item) (update :id_arquitetura #(when % (uuid-from-string %))))))

(defn build-entity
  [entity]
  (-> entity))
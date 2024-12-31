(ns sistema-gpus-core.logic.arquitetura-processador
  (:require
   [sistema-gpus-core.helper.utils :refer [uuid-from-string]]))

(defn ->uuid-if-id
  "Se a chave k for algum campo de ID, converte v de string -> UUID.
     Caso contrário, retorna v inalterado."
  [k v]
  (case k
    :id_arquitetura_proc  (uuid-from-string v)
    :id_arquitetura       (uuid-from-string v)
    :id_processador       (uuid-from-string v)
      ;; fallback - não converte
    v))

(defn uuid->str
  [item]
  (-> item
      (update :id_arquitetura_proc str)
      (update :id_arquitetura str)
      (update :id_processador str)))

(defn string->uuid
  [item]
  (-> item
      (cond-> (:id_arquitetura_proc item) (update :id_arquitetura_proc #(when % (uuid-from-string %))))
      (cond-> (:id_arquitetura item) (update :id_arquitetura #(when % (uuid-from-string %))))
      (cond-> (:id_processador item) (update :id_processador #(when % (uuid-from-string %))))))

(defn build-entity
  [entity]
  (-> entity
      (update :id_arquitetura #(when % (uuid-from-string %)))
      (update :id_processador #(when % (uuid-from-string %)))))
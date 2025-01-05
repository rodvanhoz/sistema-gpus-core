(ns sistema-gpus-core.logic.arquiteturas
  (:require
   [sistema-gpus-core.helper.utils :refer [uuid-from-string]]))

(defn ->coerce-object-field
  "Se a chave k for algum campo de ID, converte v de string -> UUID.
     Caso contrário, retorna v inalterado."
  [k v]
  (case k
    :id_arquitetura       (uuid-from-string v)
      ;; fallback - não converte
    v))

(defn object->string-fields
  [item]
  (-> (into {} item)
      (update :id_arquitetura str)))

(defn string-fields->object
  [item]
  (-> (into {} item)
      (cond-> (:id_arquitetura item) (update :id_arquitetura #(when % (uuid-from-string %))))))

(defn prepare
  [entity]
  (-> (into {} entity)))
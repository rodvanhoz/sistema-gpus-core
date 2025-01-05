(ns sistema-gpus-core.logic.caracteristicas-graficas
  (:require
   [sistema-gpus-core.helper.utils :refer [uuid-from-string]]))

(defn ->coerce-object-field
  "Se a chave k for algum campo de ID, converte v de string -> UUID.
     Caso contrário, retorna v inalterado."
  [k v]
  (case k
    :id_carac_grafica       (uuid-from-string v)
      ;; fallback - não converte
    v))

(defn object->string-fields
  [item]
  (-> (into {} item)
      (update :id_carac_grafica str)))

(defn string-fields->object
  [item]
  (-> (into {} item)
      (cond-> (:id_carac_grafica item) (update :id_carac_grafica #(when % (uuid-from-string %))))))

(defn prepare
  [entity]
  (-> (into {} entity)))
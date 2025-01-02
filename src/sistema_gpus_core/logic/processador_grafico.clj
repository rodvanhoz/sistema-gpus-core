(ns sistema-gpus-core.logic.processador-grafico
  (:require
   [sistema-gpus-core.helper.utils :refer [uuid-from-string]]))

(defn ->coerce-object-field
  "Se a chave k for algum campo de ID, converte v de string -> UUID.
             Caso contrário, retorna v inalterado."
  [k v]
  (case k
    :id_proc_grafico   (uuid-from-string v)
              ;; fallback - não converte
    v))

(defn object->string-fields
  [item]
  (-> item
      (update :id_proc_grafico str)))

(defn string-fields->object
  [item]
  (-> item
      (cond-> (:id_proc_grafico item) (update :id_proc_grafico #(when % (uuid-from-string %))))))

(defn prepare
  [entity]
  (-> entity))
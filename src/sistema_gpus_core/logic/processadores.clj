(ns sistema-gpus-core.logic.processadores
  (:require
   [sistema-gpus-core.helper.utils :refer [uuid-from-string]]))

(defn ->coerce-object-field
  "Se a chave k for algum campo de ID, converte v de string -> UUID.
               Caso contrário, retorna v inalterado."
  [k v]
  (case k
    :id_processador       (uuid-from-string v)
    :id_dados_processador (uuid-from-string v)
    :id_gpu               (uuid-from-string v)
                ;; fallback - não converte
    v))

(defn object->string-fields
  [item]
  (-> item
      (update :id_processador str)
      (update :id_dados_processador str)
      (update :id_gpu str)))

(defn string-fields->object
  [item]
  (-> item
      (cond-> (:id_processador item) (update :id_processador #(when % (uuid-from-string %))))
      (cond-> (:id_dados_processador item) (update :id_dados_processador #(when % (uuid-from-string %))))
      (cond-> (:id_gpu item) (update :id_gpu #(when % (uuid-from-string %))))))

(defn prepare
  [entity]
  (-> entity
      (update :id_dados_processador #(when % (uuid-from-string %)))
      (update :id_gpu #(when % (uuid-from-string %)))))
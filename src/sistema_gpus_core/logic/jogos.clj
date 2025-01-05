(ns sistema-gpus-core.logic.jogos
  (:require
   [sistema-gpus-core.helper.utils :refer [uuid-from-string str->date date->str]]))

(defn ->coerce-object-field
  "Se a chave k for algum campo de ID, converte v de string -> UUID.
           Caso contrário, retorna v inalterado."
  [k v]
  (case k
    :id_jogo   (uuid-from-string v)
    :dt_lancto (str->date v)
            ;; fallback - não converte
    v))

(defn object->string-fields
  [item]
  (-> (into {} item)
      (update :id_jogo str)
      (update :dt_lancto date->str)))

(defn string-fields->object
  [item]
  (-> (into {} item)
      (cond-> (:id_jogo item) (update :id_jogo #(when % (uuid-from-string %))))
      (cond-> (:dt_lancto item) (update :dt_lancto #(when % (str->date %))))))

(defn prepare
  [entity]
  (-> (into {} entity)
      (update :dt_lancto #(when % (str->date %)))))
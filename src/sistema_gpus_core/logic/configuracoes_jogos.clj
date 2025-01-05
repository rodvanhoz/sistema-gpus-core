(ns sistema-gpus-core.logic.configuracoes-jogos
  (:require
   [sistema-gpus-core.helper.utils :refer [uuid-from-string]]))

(defn ->coerce-object-field
  "Se a chave k for algum campo de ID, converte v de string -> UUID.
       Caso contrário, retorna v inalterado."
  [k v]
  (case k
    :id_configuracao_jogo  (uuid-from-string v)
    :id_jogo               (uuid-from-string v)
    :id_configuracao       (uuid-from-string v)
        ;; fallback - não converte
    v))

(defn object->string-fields
  [item]
  (-> (into {} item)
      (update :id_configuracao_jogo str)
      (update :id_jogo str)
      (update :id_configuracao str)))

(defn string-fields->object
  [item]
  (-> (into {} item)
      (cond-> (:id_configuracao_jogo item) (update :id_configuracao_jogo #(when % (uuid-from-string %))))
      (cond-> (:id_jogo item) (update :id_jogo #(when % (uuid-from-string %))))
      (cond-> (:id_configuracao item) (update :id_configuracao #(when % (uuid-from-string %))))))

(defn prepare
  [entity]
  (-> (into {} entity)
      (update :id_jogo #(when % (uuid-from-string %)))
      (update :id_configuracao #(when % (uuid-from-string %)))))
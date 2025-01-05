(ns sistema-gpus-core.logic.testes-gpu
  (:require
   [sistema-gpus-core.helper.utils :refer [uuid-from-string date->str str->date]]))

(defn ->coerce-object-field
  "Se a chave k for algum campo de ID, converte v de string -> UUID.
                   Caso contrário, retorna v inalterado."
  [k v]
  (case k
    :id_teste_gpu         (uuid-from-string v)
    :id_configuracao_jogo (uuid-from-string v)
    :id_gpu               (uuid-from-string v)
    :id_processador       (uuid-from-string v)
    :avg_fps              (Integer/parseInt v)
    :min_fps              (Integer/parseInt v)
    :dt_teste             (str->date v)
                    ;; fallback - não converte
    v))

(defn object->string-fields
  [item]
  (-> (into {} item)
      (update :id_teste_gpu str)
      (update :id_configuracao_jogo str)
      (update :id_gpu str)
      (update :id_processador str)
      (update :avg_fps str)
      (update :min_fps str)
      (update :dt_teste date->str)))

(defn string-fields->object
  [item]
  (-> (into {} item)
      (cond-> (:id_teste_gpu item) (update :id_teste_gpu #(when % (uuid-from-string %))))
      (cond-> (:id_configuracao_jogo item) (update :id_configuracao_jogo #(when % (uuid-from-string %))))
      (cond-> (:id_gpu item) (update :id_gpu #(when % (uuid-from-string %))))
      (cond-> (:id_processador item) (update :id_processador #(when % (uuid-from-string %))))
      (cond-> (:avg_fps item) (update :avg_fps #(when % (Integer/parseInt %))))
      (cond-> (:min_fps item) (update :min_fps #(when % (Integer/parseInt %))))
      (cond-> (:dt_teste item) (update :dt_teste #(when % (str->date %))))))

(defn prepare
  [entity]
  (-> (into {} entity)
      (update :id_configuracao_jogo #(when % (uuid-from-string %)))
      (update :id_gpu #(when % (uuid-from-string %)))
      (update :id_processador #(when % (uuid-from-string %)))
      (update :avg_fps #(when % (Integer/parseInt %)))
      (update :min_fps #(when % (Integer/parseInt %)))
      (update :dt_teste #(when % (str->date %)))))
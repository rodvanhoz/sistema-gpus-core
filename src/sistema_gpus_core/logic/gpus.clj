(ns sistema-gpus-core.logic.gpus
  (:require
   [sistema-gpus-core.helper.utils :refer [uuid-from-string str->date date->str]]))

(defn ->uuid-if-id
  "Se a chave k for algum campo de ID, converte v de string -> UUID.
   Caso contrário, retorna v inalterado."
  [k v]
  (case k
    :id_gpu                     (uuid-from-string v)
    :id_processador_grafico     (uuid-from-string v)
    :id_caracteristicas_graficas (uuid-from-string v)
    :id_render_config           (uuid-from-string v)
    ;; fallback - não converte
    v))

(defn uuid->str
  [item]
  (-> item
      (update :id_gpu str)
      (update :id_processador_grafico str)
      (update :id_caracteristicas_graficas str)
      (update :id_render_config str)
      (update :dt_lancto date->str)))

(defn string->uuid
  [item]
  (-> item
      (cond-> (:id_gpu item) (update :id_gpu #(when % (uuid-from-string %))))
      (cond-> (:id_processador_grafico item) (update :id_processador_grafico #(when % (uuid-from-string %))))
      (cond-> (:id_caracteristicas_graficas item) (update :id_caracteristicas_graficas #(when % (uuid-from-string %))))
      (cond-> (:id_render_config item) (update :id_render_config #(when % (uuid-from-string %))))
      (cond-> (:dt_lancto item) (update :dt_lancto #(when % (str->date %))))))

(defn build-entity
  [entity]
  (-> entity
      (update :id_processador_grafico #(when % (uuid-from-string %)))
      (update :id_caracteristicas_graficas #(when % (uuid-from-string %)))
      (update :id_render_config #(when % (uuid-from-string %)))
      (update :dt_lancto #(when % (str->date %)))))
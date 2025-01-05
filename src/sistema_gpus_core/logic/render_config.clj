(ns sistema-gpus-core.logic.render-config
  (:require
   [sistema-gpus-core.helper.utils :refer [uuid-from-string]]))

(defn ->coerce-object-field
  "Se a chave k for algum campo de ID, converte v de string -> UUID.
                 Caso contrário, retorna v inalterado."
  [k v]
  (case k
    :id_render_config (uuid-from-string v)
    :shading_units    (Integer/parseInt v)
    :tmus             (Integer/parseInt v)
    :rops             (Integer/parseInt v)
    :sm_count         (Integer/parseInt v)
    :l1_cache         (Integer/parseInt v)
    :l2_cache         (Integer/parseInt v)
    :tensor_cores     (Integer/parseInt v)
    :rt_cores         (Integer/parseInt v)
                  ;; fallback - não converte
    v))

(defn object->string-fields
  [item]
  (-> (into {} item)
      (update :id_render_config str)
      (update :shading_units str)
      (update :tmus str)
      (update :rops str)
      (update :sm_count str)
      (update :l1_cache str)
      (update :l2_cache str)
      (update :tensor_cores str)
      (update :rt_cores str)))

(defn string-fields->object
  [item]
  (-> (into {} item)
      (cond-> (:id_render_config item) (update :id_render_config #(when % (uuid-from-string %))))
      (cond-> (:shading_units item) (update :shading_units #(when % (Integer/parseInt %))))
      (cond-> (:tmus item) (update :tmus #(when % (Integer/parseInt %))))
      (cond-> (:rops item) (update :rops #(when % (Integer/parseInt %))))
      (cond-> (:sm_count item) (update :sm_count #(when % (Integer/parseInt %))))
      (cond-> (:l1_cache item) (update :l1_cache #(when % (Integer/parseInt %))))
      (cond-> (:l2_cache item) (update :l2_cache #(when % (Integer/parseInt %))))
      (cond-> (:tensor_cores item) (update :tensor_cores #(when % (Integer/parseInt %))))
      (cond-> (:rt_cores item) (update :rt_cores #(when % (Integer/parseInt %))))))

(defn prepare
  [entity]
  (-> (into {} entity)
      (update :shading_units #(when % (Integer/parseInt %)))
      (update :tmus #(when % (Integer/parseInt %)))
      (update :rops #(when % (Integer/parseInt %)))
      (update :sm_count #(when % (Integer/parseInt %)))
      (update :l1_cache #(when % (Integer/parseInt %)))
      (update :l2_cache #(when % (Integer/parseInt %)))
      (update :tensor_cores #(when % (Integer/parseInt %)))
      (update :rt_cores #(when % (Integer/parseInt %)))))
(ns sistema-gpus-core.models.render-config
  (:require [toucan.models :as models]
            [sistema-gpus-core.domain.model :refer [ModelProtocol transform model-name]]
            [toucan.db :as db]))

(models/defmodel RenderConfig :render_config)

(defrecord RenderConfigModel [])

(extend-type RenderConfigModel
  ModelProtocol
  (model-name [_] RenderConfig)
  (primary-key [_ entity] (:id_render_config entity))
  (default-fields [_]
    [:id_render_config
     :shading_units
     :tmus
     :rops
     :sm_count
     :l1_cache
     :l2_cache
     :tensor_cores
     :rt_cores
     :old_id])
  (transform [_ entity]
    entity)

  ;; CRUD
  (read-all [this]
    (db/select (model-name this)))

  (get-item [this kvs]
    (let [fn-select (partial db/select-one (model-name this))]
      (apply fn-select (->> kvs (seq) (flatten)))))

  (put-item! [this entity]
    (db/insert! (model-name this) (transform this entity)))

  (update-item! [this clause kvs]
    (let [fn-update-where (partial db/update-where! (model-name this) clause)]
      (apply fn-update-where (->> kvs (seq) (flatten)))))

  (delete-item! [this id]
    (db/simple-delete! (model-name this) {:id_render_config id}))

  (items-count [this]
    (db/count (model-name this))))

(defn ->RenderConfig
  "Retorna um record RenderConfigModel que implementa ModelProtocol."
  []
  (->RenderConfigModel))

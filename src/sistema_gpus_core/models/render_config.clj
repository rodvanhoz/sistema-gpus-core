(ns sistema-gpus-core.models.render-config
  (:require [toucan.models :as models]
            [sistema-gpus-core.domain.model :refer [ModelProtocol]]
            [toucan.db :as db]))

(models/defmodel RenderConfig :render_config)

(extend-type RenderConfig
  ModelProtocol
  (table-name [_] :render_config)
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
    (db/select (table-name this)))

  (get-item [this id]
    (db/select-one (table-name this) {:id_render_config id}))

  (put-item! [this entity]
    (db/insert! (table-name this) (transform this entity)))

  (update-item! [this id updates]
    (db/update! (table-name this) {:id_render_config id} updates))

  (delete-item! [this id]
    (db/delete! (table-name this) {:id_render_config id}))

  (items-count [this]
    (db/select-one [(str "SELECT COUNT(*) AS count FROM " (name (table-name this)))])))

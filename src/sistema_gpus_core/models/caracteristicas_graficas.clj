(ns sistema-gpus-core.models.caracteristicas-graficas
  (:require [toucan.models :as models]
            [sistema-gpus-core.domain.model :refer [ModelProtocol]]
            [toucan.db :as db]))

(models/defmodel CaracteristicasGraficas :caracteristicas_graficas)

(extend-type CaracteristicasGraficas
  ModelProtocol
  (table-name [_] :caracteristicas_graficas)
  (primary-key [_ entity] (:id_carac_grafica entity))
  (default-fields [_]
    [:id_carac_grafica
     :direct_x
     :open_gl
     :open_cl
     :vulkan
     :cuda
     :shader_model
     :old_id])
  (transform [_ entity]
    entity) ;; nenhum transform específico

  ;; CRUD
  (read-all [this]
    (db/select (table-name this)))

  (get-item [this id]
    (db/select-one (table-name this) {:id_carac_grafica id}))

  (put-item! [this entity]
    (db/insert! (table-name this) (transform this entity)))

  (update-item! [this id updates]
    (db/update! (table-name this) {:id_carac_grafica id} updates))

  (delete-item! [this id]
    (db/delete! (table-name this) {:id_carac_grafica id}))

  (items-count [this]
    (db/select-one [(str "SELECT COUNT(*) AS count FROM " (name (table-name this)))])))

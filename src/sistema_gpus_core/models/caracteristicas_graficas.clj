(ns sistema-gpus-core.models.caracteristicas-graficas
  (:require
   [clojure.string :as str]
   [sistema-gpus-core.domain.model :refer [ModelProtocol model-name transform]]
   [toucan.db :as db]
   [toucan.models :as models]))

(models/defmodel CaracteristicasGraficas :caracteristicas_graficas)

(defrecord CaracteristicasGraficasModel [])

(extend-type CaracteristicasGraficasModel
  ModelProtocol
  (model-name [_]
    CaracteristicasGraficas)

  (primary-key [_ entity]
    (:id_carac_grafica entity))

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
    ;; Exemplo de transform: uppercase no DirectX
    (update entity :direct_x #(when % (str/upper-case %))))

  ;; CRUD
  (read-all [this]
    (db/select (model-name this)))

  (get-item [this k v]
    (db/select-one (model-name this) k v))

  (put-item! [this entity]
    (db/insert! (model-name this) (transform this entity)))

  (update-item! [this updates k v]
    (db/update-where! (model-name this) updates k v))

  (delete-item! [this id]
    (db/simple-delete! (model-name this) {:id_carac_grafica id}))

  (items-count [this]
    (db/count (model-name this))))

(defn ->CaracGraficas
  []
  (->CaracteristicasGraficasModel))

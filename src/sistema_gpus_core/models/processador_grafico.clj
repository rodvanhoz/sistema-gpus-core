(ns sistema-gpus-core.models.processador-grafico
  (:require [toucan.models :as models]
            [sistema-gpus-core.domain.model :refer [ModelProtocol]]
            [toucan.db :as db]
            [clojure.string :as str]))

(models/defmodel ProcessadorGrafico :processador_grafico)

(extend-type ProcessadorGrafico
  ModelProtocol
  (table-name [_] :processador_grafico)
  (primary-key [_ entity] (:id_proc_grafico entity))
  (default-fields [_]
    [:id_proc_grafico
     :nome_gpu
     :variant_gpu
     :arquitetura
     :fundicao
     :nn_processador
     :nro_transistors
     :mm_processador
     :old_id])
  (transform [_ entity]
    (update entity :nome_gpu #(when % (str/upper-case %))))

  ;; CRUD
  (read-all [this]
    (db/select (table-name this)))

  (get-item [this id]
    (db/select-one (table-name this) {:id_proc_grafico id}))

  (put-item! [this entity]
    (db/insert! (table-name this) (transform this entity)))

  (update-item! [this id updates]
    (db/update! (table-name this) {:id_proc_grafico id} updates))

  (delete-item! [this id]
    (db/delete! (table-name this) {:id_proc_grafico id}))

  (items-count [this]
    (db/select-one [(str "SELECT COUNT(*) AS count FROM " (name (table-name this)))])))

(ns sistema-gpus-core.models.processador-grafico
  (:require [toucan.models :as models]
            [sistema-gpus-core.domain.model :refer [ModelProtocol transform model-name]]
            [toucan.db :as db]
            [clojure.string :as str]))

(models/defmodel ProcessadorGrafico :processador_grafico)

(defrecord ProcessadorGraficoModel [])

(extend-type ProcessadorGraficoModel
  ModelProtocol
  (model-name [_] ProcessadorGrafico)
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
    (db/simple-delete! (model-name this) {:id_proc_grafico id}))

  (items-count [this]
    (db/count (model-name this))))

(defn ->ProcessadorGrafico
  "Retorna um record ProcessadorGraficoModel que implementa ModelProtocol."
  []
  (->ProcessadorGraficoModel))
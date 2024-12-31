(ns sistema-gpus-core.models.arquiteturas
  (:require
   [clojure.string :as str]
   [sistema-gpus-core.domain.model :refer [ModelProtocol model-name transform]]
   [toucan.db :as db]
   [toucan.models :as models]))

;; 1) Define model Toucan
(models/defmodel Arquiteturas :arquiteturas)

;; 2) Cria record
(defrecord ArquiteturasModel [])

;; 3) extend-type
(extend-type ArquiteturasModel
  ModelProtocol
  (model-name [_]
    Arquiteturas)

  (primary-key [_ entity]
    (:id_arquitetura entity))

  (default-fields [_]
    [:id_arquitetura
     :nome_arquitetura
     :old_id])

  (transform [_ entity]
    (update entity :nome_arquitetura #(when % (str/upper-case %))))

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
    (db/simple-delete! (model-name this) {:id_arquitetura id}))

  (items-count [this]
    (db/count (model-name this))))

;; 4) Função construtora
(defn ->Arquiteturas
  "Retorna um record ArquiteturasModel que implementa ModelProtocol."
  []
  (->ArquiteturasModel))

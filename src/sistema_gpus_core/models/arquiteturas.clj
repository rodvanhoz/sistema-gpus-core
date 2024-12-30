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

  (get-item [this k v]
    (db/select-one (model-name this) k v))

  (put-item! [this entity]
    (db/insert! (model-name this) (transform this entity)))

  (update-item! [this updates k v]
    (db/update-where! (model-name this) updates k v))

  (delete-item! [this id]
    (db/simple-delete! (model-name this) {:id_arquitetura id}))

  (items-count [this]
    (db/count (model-name this))))

;; 4) Função construtora
(defn ->Arquiteturas
  []
  (->ArquiteturasModel))

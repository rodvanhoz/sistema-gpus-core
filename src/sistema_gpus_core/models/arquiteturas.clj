(ns sistema-gpus-core.models.arquiteturas
  (:require [toucan.models :as models]
            [sistema-gpus-core.domain.model :refer [ModelProtocol]]
            [toucan.db :as db]
            [clojure.string :as str]))

(models/defmodel Arquitetura :arquiteturas)

(extend-type Arquitetura
  ModelProtocol
  (table-name [_] :arquiteturas)
  (primary-key [_ entity] (:id_arquitetura entity))
  (default-fields [_]
    [:id_arquitetura
     :nome_arquitetura
     :old_id])
  (transform [_ entity]
    ;; Exemplo: uppercase no nome, se presente
    (update entity :nome_arquitetura #(when % (str/upper-case %))))

  ;; CRUD
  (read-all [this]
    (db/select (table-name this)))

  (get-item [this id]
    (db/select-one (table-name this) {:id_arquitetura id}))

  (put-item! [this entity]
    (db/insert! (table-name this) (transform this entity)))

  (update-item! [this id updates]
    (db/update! (table-name this) {:id_arquitetura id} updates))

  (delete-item! [this id]
    (db/delete! (table-name this) {:id_arquitetura id}))

  (items-count [this]
    (db/select-one [(str "SELECT COUNT(*) AS count FROM " (name (table-name this)))])))

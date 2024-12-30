(ns sistema-gpus-core.models.caracteristicas-graficas-test
  (:require [clojure.test :refer :all]
            [toucan.db :as db]
            [sistema-gpus-core.db-setup :as db-setup]
            [sistema-gpus-core.domain.model :as model]
            [sistema-gpus-core.models.caracteristicas-graficas :as cg]
            [sistema-gpus-core.helper.utils :refer [uuid-from-string]]))

(use-fixtures :once db-setup/with-postgres-testcontainer)

(deftest test-carac-graficas-crud
  ;; mocks
  (db-setup/apply-mock-caracteristicas-graficas! (db/connection))

  ;; 1) read-all
  (testing "read-all em caracteristicas_graficas"
    (let [result (model/read-all (cg/->CaracGraficas))]
      (is (= (count result) 5)
          "Deve ter 5 registros.")))

  ;; 2) get-item
  (testing "get-item"
    (let [target-id (uuid-from-string "03320cda-708b-4ef8-b7eb-95ffbec741d6")
          item      (model/get-item (cg/->CaracGraficas) :id_carac_grafica target-id)]
      (is (some? item))
      (is (= target-id (-> item :id_carac_grafica uuid-from-string)))))

  ;; 3) put-item!
  (testing "put-item! insere nova carac grafica"
    (let [nova {:direct_x "DX13"
                :open_gl  "4.6"
                :cuda     "9.2"}
          _    (model/put-item! (cg/->CaracGraficas) nova)
          achou (model/get-item (cg/->CaracGraficas) :direct_x "DX13")]
      (is (some? achou))
      (is (= "DX13" (:direct_x achou)))))

  ;; 4) update-item!
  (testing "update-item! atualiza"
    (let [target-id (uuid-from-string "03320cda-708b-4ef8-b7eb-95ffbec741d6")
          _         (model/update-item! (cg/->CaracGraficas)
                                        {:id_carac_grafica target-id}
                                        :open_gl "4.9")
          updated   (model/get-item (cg/->CaracGraficas) :id_carac_grafica target-id)]
      (is (= "4.9" (:open_gl updated)))))

  ;; 5) delete-item!
  (testing "delete-item!"
    (let [del-id (uuid-from-string "03320cda-708b-4ef8-b7eb-95ffbec741d6")]
      (model/delete-item! (cg/->CaracGraficas) del-id)
      (is (nil? (model/get-item (cg/->CaracGraficas) :id_carac_grafica del-id)))))

  ;; 6) items-count
  (testing "items-count"
    (let [cnt (model/items-count (cg/->CaracGraficas))]
      (is (pos? cnt)))))

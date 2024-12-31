(ns sistema-gpus-core.models.arquitetura-processador-test
  (:require [clojure.test :refer :all]
            [toucan.db :as db]
            [sistema-gpus-core.db-setup :as db-setup]
            [sistema-gpus-core.domain.model :as model]
            [sistema-gpus-core.models.arquitetura-processador :as ap]
            [sistema-gpus-core.helper.utils :refer [uuid-from-string]]))

(use-fixtures :once db-setup/with-postgres-testcontainer)

(deftest test-arquitetura-processador-crud
  (db-setup/apply-mock-arquitetura-processador! (db/connection))

  ;; 1) read-all
  (testing "read-all"
    (let [res (model/read-all (ap/->ArquiteturaProcessador))]
      (is (= (count res) 5))))

  ;; 2) get-item
  (testing "get-item"
    (let [aid (uuid-from-string "3d4430a3-244c-4547-9572-f2af864b4648")
          item (model/get-item (ap/->ArquiteturaProcessador) {:id_arquitetura_proc aid})]
      (is (some? item))))

  ;; 3) put-item!
  (testing "put-item!"
    (let [novo {:id_arquitetura (uuid-from-string "954fb9a1-f09c-4c6c-a279-ebca7282690d")
                :id_processador (uuid-from-string "f9ce2969-a573-4e8b-b71c-abc263bcb4a5")}
          _    (model/put-item! (ap/->ArquiteturaProcessador) novo)
          achou (model/get-item (ap/->ArquiteturaProcessador) {:id_arquitetura (uuid-from-string "954fb9a1-f09c-4c6c-a279-ebca7282690d")})]
      (is (some? achou))))

  ;; 4) update-item!
  (testing "update-item!"
    (let [aid (uuid-from-string "3d4430a3-244c-4547-9572-f2af864b4648")]
      (model/update-item! (ap/->ArquiteturaProcessador)
                          {:id_arquitetura_proc aid}
                          {:id_arquitetura (uuid-from-string "a2be633c-46f3-47b9-896f-57fc44a65d61")})
      (is (= (uuid-from-string "a2be633c-46f3-47b9-896f-57fc44a65d61")
             (:id_arquitetura (model/get-item (ap/->ArquiteturaProcessador)
                                              {:id_arquitetura_proc aid}))))))

  ;; 5) delete-item!
  (testing "delete-item!"
    (let [aid (uuid-from-string "3d4430a3-244c-4547-9572-f2af864b4648")]
      (model/delete-item! (ap/->ArquiteturaProcessador) aid)
      (is (nil? (model/get-item (ap/->ArquiteturaProcessador) {:id_arquitetura_proc aid})))))

  ;; 6) items-count
  (testing "items-count"
    (let [cnt (model/items-count (ap/->ArquiteturaProcessador))]
      (is (pos? cnt)))))

(ns sistema-gpus-core.models.configuracoes-test
  (:require [clojure.test :refer :all]
            [toucan.db :as db]
            [sistema-gpus-core.db-setup :as db-setup]
            [sistema-gpus-core.domain.model :as model]
            [sistema-gpus-core.models.configuracoes :as cfg]
            [sistema-gpus-core.helper.utils :refer [uuid-from-string]]))

(use-fixtures :once db-setup/with-postgres-testcontainer)

(deftest test-configuracoes-crud
  (db-setup/apply-mock-configuracoes! (db/connection))

  ;; 1) read-all
  (testing "read-all configuracoes"
    (let [res (model/read-all (cfg/->Configuracoes))]
      (is (= (count res) 4))))

  ;; 2) get-item
  (testing "get-item configuracoes"
    (let [cid (uuid-from-string "b82abfb8-138d-47f5-adbb-c139444ab7a5")
          item (model/get-item (cfg/->Configuracoes) :id_configuracao cid)]
      (is (some? item))
      (is (= cid (-> item :id_configuracao uuid-from-string)))))

  ;; 3) put-item!
  (testing "put-item!"
    (let [nova {:api "dx14" :qualidade_grafica "High" :ssao "Y"}
          _    (model/put-item! (cfg/->Configuracoes) nova)
          achou (model/get-item (cfg/->Configuracoes) :api "dx14")]
      (is (some? achou))))

  ;; 4) update-item!
  (testing "update-item!"
    (let [cid (uuid-from-string "b82abfb8-138d-47f5-adbb-c139444ab7a5")]
      (model/update-item! (cfg/->Configuracoes) {:id_configuracao cid} {:qualidade_grafica "MEDIUM"})
      (is (= "MEDIUM"
             (:qualidade_grafica (model/get-item (cfg/->Configuracoes) :id_configuracao cid))))))

  ;; 5) delete-item!
  (testing "delete-item!"
    (let [cid (uuid-from-string "7cdbd945-7811-4a99-8e17-0ee82ab8e4c9")]
      (model/delete-item! (cfg/->Configuracoes) cid)
      (is (nil? (model/get-item (cfg/->Configuracoes) :id_configuracao cid)))))

  ;; 6) items-count
  (testing "items-count"
    (let [cnt (model/items-count (cfg/->Configuracoes))]
      (is (pos? cnt)))))

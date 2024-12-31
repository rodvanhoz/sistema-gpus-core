(ns sistema-gpus-core.models.arquiteturas-test
  (:require [clojure.test :refer :all]
            [toucan.db :as db]
            [sistema-gpus-core.db-setup :as db-setup]
            [sistema-gpus-core.domain.model :as model]
            [sistema-gpus-core.models.arquiteturas :as arq]
            [sistema-gpus-core.helper.utils :refer [uuid-from-string]]))

;; Fixture para Testcontainers
(use-fixtures :once db-setup/with-postgres-testcontainer)

(deftest test-arquiteturas-crud
  (db-setup/apply-mock-arquiteturas! (db/connection))

  (testing "read-all deve retornar registros"
    (let [result (model/read-all (arq/->Arquiteturas))]
      (is (= (count result) 5))))

  (testing "get-item retorna item esperado"
    (let [arq-id (uuid-from-string "e001456e-4691-4755-99ad-ea90ff4f19fd")
          item   (model/get-item (arq/->Arquiteturas) {:id_arquitetura arq-id})]
      (is (= arq-id (-> item :id_arquitetura uuid-from-string)))))

  (testing "put-item! insere nova arquitetura"
    (let [nova-arq {:nome_arquitetura "RDNA 5"}
          _        (model/put-item! (arq/->Arquiteturas) nova-arq)
          result   (model/get-item (arq/->Arquiteturas) {:nome_arquitetura "RDNA 5"})]
      (is (= "RDNA 5" (:nome_arquitetura result)))))

  (testing "update-item! atualiza item"
    (let [arq-id (uuid-from-string "a2be633c-46f3-47b9-896f-57fc44a65d61")
          _      (model/update-item! (arq/->Arquiteturas)
                                     {:id_arquitetura arq-id}
                                     {:nome_arquitetura "Maxwell 2 Updated"})
          item   (model/get-item (arq/->Arquiteturas) {:id_arquitetura arq-id})]
      (is (= "Maxwell 2 Updated" (:nome_arquitetura item)))))

  (testing "delete-item! remove item"
    (let [arq-id (uuid-from-string "a2be633c-46f3-47b9-896f-57fc44a65d61")]
      (model/delete-item! (arq/->Arquiteturas) arq-id)
      (is (nil? (model/get-item (arq/->Arquiteturas) {:id_arquitetura arq-id})))))

  (testing "items-count retorna contagem"
    (let [cnt (model/items-count (arq/->Arquiteturas))]
      (is (pos? cnt)))))

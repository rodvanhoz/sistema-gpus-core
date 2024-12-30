(ns sistema-gpus-core.controller.gpus-test
  (:require
   [clojure.test :refer :all]
   [toucan.db :as db]
   [sistema-gpus-core.db-setup :as db-setup]
   [sistema-gpus-core.controller.gpus :as ctrl]
   [sistema-gpus-core.controller.client :refer :all]))

(use-fixtures :once db-setup/with-postgres-testcontainer)

(deftest gpus-controller-crud-test
  ;; Carrega mocks da tabela gpus
  (db-setup/apply-mock-gpus! (db/connection))

  ;; Instancia o controller
  (let [controller (ctrl/->gpus-controller)]

    ;; 1) read-all
    (testing "read-all"
      (let [res (read-all controller)]
        (is (>= (count res) 1) "Deve retornar pelo menos 1 GPU")))

    ;; 2) get-item
    (testing "get-item"
      (let [gpu-id "669e6eb0-6528-489a-ac90-bb3670cb6a78"  ;; string
            item   (get-item controller {:id_gpu gpu-id})]
        (is (some? item) "Deve encontrar a GPU com esse id_gpu")
        (is (= gpu-id (:id_gpu item)) "UUID deve ser igual (em string)")))

    ;; 3) put-item
    (testing "put-item"
      (let [new-entity {:nome_fabricante "ZOTAC"
                        :nome_modelo     "ZOTAC FANTASTIC"
                        :id_processador_grafico "64f83ccb-c2f1-493a-9516-a88e86c3dd97"
                        :dt_lancto       "2021-07-01 00:00:00.000"
                        :id_caracteristicas_graficas "a9a17e79-fa0d-4b54-9d20-5b1a3d8f45c8"
                        :id_render_config "35f895ea-3ee1-4eb4-ae99-933511c2472f"}]
        (put-item controller new-entity)
        ;; checar se foi inserido
        (let [found (get-item controller {:nome_modelo "ZOTAC FANTASTIC" :nome_fabricante "ZOTAC"})]
          (is (some? found))
          (is (= "ZOTAC" (:nome_fabricante found)))
          (is (= "ZOTAC FANTASTIC" (:nome_modelo found))))))

    ;; 4) update-item
    (testing "update-item"
      (let [gpu-id  "669e6eb0-6528-489a-ac90-bb3670cb6a78"
            updates {:nome_modelo "GeForce GTX 770 - UPDATED"
                     :nome_fabricante "SUPER ZOTAC"}]
        (update-item controller updates {:id_gpu gpu-id})
        (let [updated (get-item controller {:id_gpu gpu-id})]
          (is (= "SUPER ZOTAC" (:nome_fabricante updated)))
          (is (= "GeForce GTX 770 - UPDATED" (:nome_modelo updated))))))

    ;; 5) delete-item
    (testing "delete-item"
      (let [gpu-id "a41adbc8-acf0-432e-bd13-a2eb7d66b034"]
        (delete-item controller gpu-id)
        (is (nil? (get-item controller {:id_gpu gpu-id}))
            "Registro deve ter sido removido.")))

    ;; 6) items-count
    (testing "items-count"
      (let [cnt (items-count controller)]
        (is (pos? cnt) "items-count deve ser positivo")))))

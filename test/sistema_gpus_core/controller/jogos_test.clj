(ns sistema-gpus-core.controller.jogos-test
  (:require
   [clojure.test :refer :all]
   [toucan.db :as db]
   [sistema-gpus-core.db-setup :as db-setup]
   [sistema-gpus-core.controller.jogos :as ctrl]
   [sistema-gpus-core.domain.client :refer :all]))

(use-fixtures :once db-setup/with-postgres-testcontainer)

(deftest controller-crud-test
            ;; Carrega mocks da tabela
  (db-setup/apply-mock-jogos! (db/connection))

            ;; Instancia o controller
  (let [controller (ctrl/->jogos-controller)]

              ;; 1) read-all
    (testing "read-all"
      (let [res (read-all controller)]
        (is (= (count res) 6) "Deve retornar 6 jogos")))

              ;; 2) get-item
    (testing "get-item"
      (let [id "60ed2c10-dc21-4065-a24a-6f428bce23d3"
            item   (get-item controller {:id_jogo id})]
        (is (some? item) "Deve encontrar a jogos com esse id")
        (is (= id (:id_jogo item)) "UUID deve ser igual (em string)")))

              ;; 3) put-item
    (testing "put-item"
      (let [new-entity {:nome_jogo "Elden Ring 3" :dt_lancto "2030-05-05T00:00:00.000Z"}]
        (put-item controller new-entity)
                  ;; checar se foi inserido
        (let [found (get-item controller {:nome_jogo "Elden Ring 3" :dt_lancto "2030-05-05T00:00:00.000Z"})]
          (is (some? found))
          (is (= "Elden Ring 3" (:nome_jogo found)))
          (is (= "2030-05-05T00:00:00.000Z" (:dt_lancto found))))))

              ;; 4) update-item
    (testing "update-item"
      (let [id  "60ed2c10-dc21-4065-a24a-6f428bce23d3"
            updates {:nome_jogo "ZELDA TOTK"}]
        (update-item controller updates {:id_jogo id})
        (let [updated (get-item controller {:id_jogo id})]
          (is (some? updated))
          (is (= "ZELDA TOTK" (:nome_jogo updated))))))

              ;; 5) delete-item
    (testing "delete-item"
      (let [id "60ed2c10-dc21-4065-a24a-6f428bce23d3"]
        (delete-item controller id)
        (is (nil? (get-item controller {:id_jogo id}))
            "Registro deve ter sido removido.")))

              ;; 6) items-count
    (testing "items-count"
      (let [cnt (items-count controller)]
        (is (pos? cnt) "items-count deve ser positivo")))))
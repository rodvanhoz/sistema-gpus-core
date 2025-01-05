(ns sistema-gpus-core.controller.configuracoes-jogos-test
  (:require
   [clojure.test :refer :all]
   [toucan.db :as db]
   [sistema-gpus-core.db-setup :as db-setup]
   [sistema-gpus-core.controller.configuracoes-jogos :as ctrl]
   [sistema-gpus-core.domain.controller-client :refer :all]))

(use-fixtures :once db-setup/with-postgres-testcontainer)

(deftest controller-crud-test
        ;; Carrega mocks da tabela
  (db-setup/apply-mock-configuracoes-jogos! (db/connection))

        ;; Instancia o controller
  (let [controller (ctrl/->configuracoes-jogos-controller)]

          ;; 1) read-all
    (testing "read-all"
      (let [res (read-all controller)]
        (is (= (count res) 3) "Deve retornar 5 configuracoes-jogos")))

          ;; 2) get-item
    (testing "get-item"
      (let [id "5a9ece7c-68fe-499a-9039-b8897af1cad6"
            item   (get-item controller {:id_configuracao_jogo id})]
        (is (some? item) "Deve encontrar a configuracoes-jogos com esse id")
        (is (= id (:id_configuracao_jogo item)) "UUID deve ser igual (em string)")))

          ;; 3) put-item
    (testing "put-item"
      (let [new-entity {:id_jogo "503f90a2-3d0e-4de7-8834-66492b5abd96"
                        :id_configuracao "7e7890ca-8ff0-4a8c-aee9-00a1d3e085d8"}]
        (put-item controller new-entity)
              ;; checar se foi inserido
        (let [found (get-item controller {:id_jogo "503f90a2-3d0e-4de7-8834-66492b5abd96"
                                          :id_configuracao "7e7890ca-8ff0-4a8c-aee9-00a1d3e085d8"})]
          (is (some? found))
          (is (= "503f90a2-3d0e-4de7-8834-66492b5abd96" (:id_jogo found)))
          (is (= "7e7890ca-8ff0-4a8c-aee9-00a1d3e085d8" (:id_configuracao found))))))

;; 4) update-item
    (testing "update-item"
      (let [id  "5a9ece7c-68fe-499a-9039-b8897af1cad6"
            updates {:id_jogo "b17fda4d-b019-4a12-9825-f53d219fbad6"}]
        (update-item controller updates {:id_configuracao_jogo id})
        (let [updated (get-item controller {:id_configuracao_jogo id})]
          (is (some? updated))
          (is (= "b17fda4d-b019-4a12-9825-f53d219fbad6" (:id_jogo updated))))))

          ;; 5) delete-item
    (testing "delete-item"
      (let [id "5a9ece7c-68fe-499a-9039-b8897af1cad6"]
        (delete-item controller id)
        (is (nil? (get-item controller {:id_configuracao_jogo id}))
            "Registro deve ter sido removido.")))

          ;; 6) items-count
    (testing "items-count"
      (let [cnt (items-count controller)]
        (is (pos? cnt) "items-count deve ser positivo")))))
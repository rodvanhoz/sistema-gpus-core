(ns sistema-gpus-core.models.configuracoes-jogos-test
  (:require [clojure.test :refer :all]
            [toucan.db :as db]
            [sistema-gpus-core.db-setup :as db-setup]
            [sistema-gpus-core.domain.model :as model]
            [sistema-gpus-core.models.configuracoes-jogos :as cj]
            [sistema-gpus-core.helper.utils :refer [uuid-from-string]]))

(use-fixtures :once db-setup/with-postgres-testcontainer)

(deftest test-configuracoes-jogos-crud
  (db-setup/apply-mock-configuracoes-jogos! (db/connection))

  ;; 1) read-all
  (testing "read-all configuracoes_jogos"
    (let [res (model/read-all (cj/->ConfiguracoesJogos))]
      (is (= (count res) 3))))

  ;; 2) get-item
  (testing "get-item"
    (let [cid (uuid-from-string "5a9ece7c-68fe-499a-9039-b8897af1cad6")
          item (model/get-item (cj/->ConfiguracoesJogos) :id_configuracao_jogo cid)]
      (is (some? item))))

  ;; 3) put-item!
  (testing "put-item!"
    (let [nova {:id_jogo (uuid-from-string "503f90a2-3d0e-4de7-8834-66492b5abd96")
                :id_configuracao (uuid-from-string "7e7890ca-8ff0-4a8c-aee9-00a1d3e085d8")}
          _    (model/put-item! (cj/->ConfiguracoesJogos) nova)
          achou (model/get-item (cj/->ConfiguracoesJogos) :id_jogo (uuid-from-string "503f90a2-3d0e-4de7-8834-66492b5abd96"))]
      (is (some? achou))))

  ;; 4) update-item!
  (testing "update-item!"
    (let [cid (uuid-from-string "5a9ece7c-68fe-499a-9039-b8897af1cad6")]
      (model/update-item! (cj/->ConfiguracoesJogos)
                          {:id_configuracao_jogo cid}
                          :id_jogo (uuid-from-string "b17fda4d-b019-4a12-9825-f53d219fbad6"))
      (is (= (uuid-from-string "b17fda4d-b019-4a12-9825-f53d219fbad6")
             (:id_jogo (model/get-item (cj/->ConfiguracoesJogos)
                                       :id_configuracao_jogo cid))))))

  ;; 5) delete-item!
  (testing "delete-item!"
    (let [cid (uuid-from-string "5a9ece7c-68fe-499a-9039-b8897af1cad6")]
      (model/delete-item! (cj/->ConfiguracoesJogos) cid)
      (is (nil? (model/get-item (cj/->ConfiguracoesJogos) :id_configuracao_jogo cid)))))

  ;; 6) items-count
  (testing "items-count"
    (let [cnt (model/items-count (cj/->ConfiguracoesJogos))]
      (is (pos? cnt)))))

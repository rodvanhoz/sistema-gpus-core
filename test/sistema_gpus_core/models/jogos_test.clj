(ns sistema-gpus-core.models.jogos-test
  (:require [clojure.test :refer :all]
            [toucan.db :as db]
            [sistema-gpus-core.db-setup :as db-setup]
            [sistema-gpus-core.domain.model :as model]
            [sistema-gpus-core.models.jogos :as jogos]
            [sistema-gpus-core.helper.utils :refer [uuid-from-string str->date]]))

(use-fixtures :once db-setup/with-postgres-testcontainer)

(deftest test-jogos-crud
  (db-setup/apply-mock-jogos! (db/connection))

  ;; 1) read-all
  (testing "read-all jogos"
    (let [res (model/read-all (jogos/->Jogos))]
      (is (= (count res) 6))))

  ;; 2) get-item
  (testing "get-item"
    (let [jid (uuid-from-string "60ed2c10-dc21-4065-a24a-6f428bce23d3")
          item (model/get-item (jogos/->Jogos) :id_jogo jid)]
      (is (some? item))))

  ;; 3) put-item!
  (testing "put-item!"
    (let [novo {:nome_jogo "Elden Ring 3" :dt_lancto (str->date "2030-05-05T00:00:00.000Z")}
          _    (model/put-item! (jogos/->Jogos) novo)
          achou (model/get-item (jogos/->Jogos) :nome_jogo "Elden Ring 3")]
      (is (some? achou))))

  ;; 4) update-item!
  (testing "update-item!"
    (let [jid (uuid-from-string "60ed2c10-dc21-4065-a24a-6f428bce23d3")]
      (model/update-item! (jogos/->Jogos) {:id_jogo jid} :nome_jogo "ZELDA TOTK")
      (is (= "ZELDA TOTK" (:nome_jogo (model/get-item (jogos/->Jogos) :id_jogo jid))))))

  ;; 5) delete-item!
  (testing "delete-item!"
    (let [jid (uuid-from-string "60ed2c10-dc21-4065-a24a-6f428bce23d3")]
      (model/delete-item! (jogos/->Jogos) jid)
      (is (nil? (model/get-item (jogos/->Jogos) :id_jogo jid)))))

  ;; 6) items-count
  (testing "items-count"
    (let [cnt (model/items-count (jogos/->Jogos))]
      (is (pos? cnt)))))

(ns sistema-gpus-core.models.render-config-test
  (:require [clojure.test :refer :all]
            [toucan.db :as db]
            [sistema-gpus-core.db-setup :as db-setup]
            [sistema-gpus-core.domain.model :as model]
            [sistema-gpus-core.models.render-config :as rc]
            [sistema-gpus-core.helper.utils :refer [uuid-from-string]]))

(use-fixtures :once db-setup/with-postgres-testcontainer)

(deftest test-render-config-crud
  (db-setup/apply-mock-render-config! (db/connection))

  ;; 1) read-all
  (testing "read-all render_config"
    (let [res (model/read-all (rc/->RenderConfig))]
      (is (>= (count res) 1))))

  ;; 2) get-item
  (testing "get-item"
    (let [rid (uuid-from-string "ffff1111-1111-1111-1111-ffffffffffff")
          item (model/get-item (rc/->RenderConfig) :id_render_config rid)]
      (is (some? item))))

  ;; 3) put-item!
  (testing "put-item!"
    (let [novo {:shading_units 4096 :tmus 256 :rops 96}
          _    (model/put-item! (rc/->RenderConfig) novo)
          achou (->> (model/read-all (rc/->RenderConfig))
                     (filter #(= 96 (:rops %))) first)]
      (is (some? achou))))

  ;; 4) update-item!
  (testing "update-item!"
    (let [rid (uuid-from-string "ffff2222-2222-2222-2222-ffffffffffff")]
      (model/update-item! (rc/->RenderConfig)
                          {:sm_count 64}
                          :id_render_config rid)
      (is (= 64
             (:sm_count (model/get-item (rc/->RenderConfig) :id_render_config rid))))))

  ;; 5) delete-item!
  (testing "delete-item!"
    (let [rid (uuid-from-string "ffff3333-3333-3333-3333-ffffffffffff")]
      (model/delete-item! (rc/->RenderConfig) rid)
      (is (nil? (model/get-item (rc/->RenderConfig) :id_render_config rid)))))

  ;; 6) items-count
  (testing "items-count"
    (let [cnt (model/items-count (rc/->RenderConfig))]
      (is (pos? cnt)))))

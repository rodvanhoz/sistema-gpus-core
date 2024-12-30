(ns sistema-gpus-core.models.gpus-test
  (:require [clojure.test :refer :all]
            [toucan.db :as db]
            [sistema-gpus-core.db-setup :as db-setup]
            [sistema-gpus-core.domain.model :as model]
            [sistema-gpus-core.models.gpus :as gpus]
            [sistema-gpus-core.helper.utils :refer [uuid-from-string str->date]]))

;; 1) Usamos a fixture que sobe o container e aplica o baseline
(use-fixtures :once db-setup/with-postgres-testcontainer)

(deftest test-gpus-crud
  (db-setup/apply-mock-gpus! (db/connection))
  ;; Testar read-all
  (testing "read-all deve retornar as GPUs mockadas"
    (let [result (model/read-all (gpus/->GPUs))]
      (is (>= (count result) 1) "Deve retornar pelo menos 1 GPU")))

  ;; Testar get-item
  (testing "get-item retorna a GPU esperada"
    (let [gpu-id (uuid-from-string "669e6eb0-6528-489a-ac90-bb3670cb6a78")
          gpu    (model/get-item (gpus/->GPUs) :id_gpu gpu-id)]
      (is (= gpu-id (-> gpu :id_gpu (uuid-from-string))))
      (is (= "NVIDIA" (:nome_fabricante gpu)))))

  ;; Testar put-item!
  (testing "put-item! insere uma nova GPU"
    (let [new-gpu {:nome_fabricante          "ASUS"
                   :nome_modelo              "ASUS SPECIAL GPU BLA"
                   :tam_memoria_kb           6144
                   :tp_memoria               "GDDR6"
                   :dt_lancto                (str->date "2020-01-01 00:00:00.000")
                   :id_processador_grafico   (uuid-from-string "64f83ccb-c2f1-493a-9516-a88e86c3dd97")
                   :id_caracteristicas_graficas (uuid-from-string "bb8e8e6b-8c83-401a-a1a0-2964a049de60")
                   :id_render_config         (uuid-from-string "12b9b661-9e9e-4cc2-a421-6ecf6adc7741")}
          _      (model/put-item! (gpus/->GPUs) new-gpu)
          result (model/get-item (gpus/->GPUs) :nome_modelo "ASUS SPECIAL GPU BLA")]
      ;; (is (= new-id (-> result :id_gpu (uuid-from-string))))
      (is (= "ASUS" (:nome_fabricante result)))))

;; Testar update-item!
  (testing "update-item! atualiza um campo da GPU"
    (let [gpu-id  (uuid-from-string "669e6eb0-6528-489a-ac90-bb3670cb6a78")
          _       (model/update-item! (gpus/->GPUs) {:id_gpu gpu-id} :nome_modelo "GeForce GTX 770 UPDATED")
          updated (model/get-item (gpus/->GPUs) :id_gpu gpu-id)]
      (is (= "GeForce GTX 770 UPDATED" (:nome_modelo updated)))))

  ;; Testar delete-item!
  (testing "delete-item! remove uma GPU do banco"
    (let [gpu-id (uuid-from-string "a41adbc8-acf0-432e-bd13-a2eb7d66b034")]
      (model/delete-item! (gpus/->GPUs) gpu-id)
      (is (nil? (model/get-item (gpus/->GPUs) :id_gpu gpu-id)))))

  ;; Testar items-count
  (testing "items-count retorna a contagem de GPUs"
    (let [counter (model/items-count (gpus/->GPUs))]
      (is (= counter 7) "A resposta deve conter 7"))))

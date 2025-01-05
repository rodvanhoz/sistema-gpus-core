(ns sistema-gpus-core.domain.controller-client)

(defprotocol ControllerClientProtocol
  "Protocolo que define as operações CRUD no nível de Controller,
   recebendo/cláusulas e transformando tipos de dados."

  (read-all [this]
    "Lista todos os registros de um modelo.")

  (get-item [this clause]
    "Obtém um item passando um map ex.: {:id_gpu \"...\"}.")

  (put-item [this entity]
    "Insere um item (map com campos).")

  (update-item [this updates clause]
    "Atualiza um item. `updates` é um map com campos, `clause` é um map ex.: {:id_gpu \"...\"}.")

  (delete-item [this id]
    "Deleta um item passando o id (string de UUID).")

  (items-count [this]
    "Retorna a contagem total de registros na tabela."))

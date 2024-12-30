(ns sistema-gpus-core.domain.model)

(defprotocol ModelProtocol
  "Define o comportamento genérico de um modelo, incluindo funções CRUD."
  (model-name [this] "Retorna o nome da tabela.")
  (primary-key [this entity] "Retorna a chave primária de uma entidade (map).")
  (default-fields [this] "Retorna os campos padrão do modelo.")
  (transform [this entity] "Transforma os dados antes de salvar ou retornar.")

  ;; --- Operações CRUD ---
  (read-all [this] "Lista todos os registros de um modelo.")
  (get-item [this k v] "Obtém um item pelo ID.")
  (put-item! [this entity] "Insere um item.")
  (update-item! [this clause kvs] "Atualiza um item.")
  (delete-item! [this id] "Deleta um item.")
  (items-count [this] "Retorna a contagem total de registros na tabela."))

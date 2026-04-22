-- Criação da tabela de tarefas
CREATE TABLE IF NOT EXISTS tarefa (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    descricao TEXT,
    local VARCHAR(255),
    data_hora TIMESTAMP
);

-- Criar índice para melhorar performance nas buscas
CREATE INDEX IF NOT EXISTS idx_tarefa_titulo ON tarefa(titulo);


CREATE OR REPLACE FUNCTION inserir_cliente(
    p_nome VARCHAR,
    p_email VARCHAR,
    p_telefone VARCHAR
) RETURNS VOID AS $$
BEGIN
    INSERT INTO cliente (nome, email, telefone)
    VALUES (p_nome, p_email, p_telefone);
END;
$$ LANGUAGE plpgsql;

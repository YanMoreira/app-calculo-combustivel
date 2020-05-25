package com.example.calculadoradecombustvel.helper;

import com.example.calculadoradecombustvel.model.Lista;

import java.util.List;

public interface IAbastecimentoDAO {

    public boolean salvar(Lista lista);
    public boolean atualizar(Lista lista);
    public boolean deletar(Lista lista);
    public List<Lista> listar();
}

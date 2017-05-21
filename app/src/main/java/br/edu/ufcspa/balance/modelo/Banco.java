package br.edu.ufcspa.balance.modelo;

import java.util.ArrayList;

/**
 * Created by Rodrigo on 20/05/2017.
 * Classe que abriga as chamadas de acesso ao banco de dados interno
 */

public class Banco {


    public static ArrayList<Avaliacao> getAvaliacoes(Paciente paciente){
        try {
            return  (ArrayList<Avaliacao>) Avaliacao.find(Avaliacao.class, "id_Paciente = " + paciente.getId(), null, null, "data DESC", null);
        } catch (Exception e) {
            e.printStackTrace();
            return  new ArrayList<Avaliacao>();
        }
    }

    public static Long countAvaliacoes(Paciente paciente){
            return (Long) Paciente.count(Avaliacao.class, "id_Paciente = " + paciente.getId(), null);
    }


}

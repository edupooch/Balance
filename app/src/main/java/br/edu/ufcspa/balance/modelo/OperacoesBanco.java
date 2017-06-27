package br.edu.ufcspa.balance.modelo;

import java.util.ArrayList;

/**
 * Created by Rodrigo on 20/05/2017.
 * Classe que abriga as chamadas de acesso ao banco de dados interno
 */

public class OperacoesBanco {

    public static ArrayList<Avaliacao> getAvaliacoes(Paciente paciente) {
        try {
            return (ArrayList<Avaliacao>) Avaliacao.find(Avaliacao.class,
                    "id_Paciente = " + paciente.getId(), null, null, "id DESC", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static Long countAvaliacoes(Paciente paciente) {
        return Paciente.count(Avaliacao.class, "id_Paciente = " + paciente.getId(), null);
    }


}

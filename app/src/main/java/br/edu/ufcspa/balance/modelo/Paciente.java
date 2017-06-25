package br.edu.ufcspa.balance.modelo;

import android.support.annotation.NonNull;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.io.Serializable;
import java.util.List;

/**
 * Classe modelo do paciente com seus atributos e operações.
 *
 * Created by edupooch on 21/12/15.
 */
public class Paciente extends SugarRecord<Paciente> implements Serializable, Comparable<Paciente> {

    public static final int MASCULINO = 1;
    public static final int FEMININO = 2;

    public Long id = null;
    private String nome;
    private String telefone;
    private String email;
    private Double massa;
    private Double estatura;
    private String dataNascimento;
    private Integer genero;
    private String obs;

    private String anamnesisDate;
    private String diagnostico;
    private String dataDiagnostico;
    private String historicoDoencaAtual;
    private String historicoDoencasAnteriores;
    private String procedimentosTerapeuticos;

    @Ignore
    private List<Avaliacao> avaliacoes;

    /**
     * Construtor vazio obrigatório pelo SugarORM
     */
    public Paciente() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getMassa() {
        return massa;
    }

    public void setMassa(Double massa) {
        this.massa = massa;
    }

    public Double getAltura() {
        return estatura;
    }

    public void setEstatura(Double estatura) {
        this.estatura = estatura;
    }

    @Override
    public String toString() {
        return getNome();
    }

    public Integer getGenero() {
        return genero;
    }

    public void setGenero(Integer genero) {
        this.genero = genero;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }


    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }


    public String getHistoricoDoencaAtual() {
        return historicoDoencaAtual;
    }

    public void setHistoricoDoencaAtual(String historicoDoencaAtual) {
        this.historicoDoencaAtual = historicoDoencaAtual;
    }

    public String getHistoricoDoencasAnteriores() {
        return historicoDoencasAnteriores;
    }

    public void setHistoricoDoencasAnteriores(String historicoDoencasAnteriores) {
        this.historicoDoencasAnteriores = historicoDoencasAnteriores;
    }

    public String getProcedimentosTerapeuticos() {
        return procedimentosTerapeuticos;
    }

    public void setProcedimentosTerapeuticos(String procedimentosTerapeuticos) {
        this.procedimentosTerapeuticos = procedimentosTerapeuticos;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getDataAnamnese() {
        return anamnesisDate;
    }

    public void setAnamnesisDate(String anamnesisDate) {
        this.anamnesisDate = anamnesisDate;
    }

    public String getDataDiagnostico() {
        return dataDiagnostico;
    }

    public void setDataDiagnostico(String dataDiagnostico) {
        this.dataDiagnostico = dataDiagnostico;
    }

    private List<Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }

    public void copy(Paciente pacienteAlterado) {
        this.nome = pacienteAlterado.getNome();
        this.massa = pacienteAlterado.getMassa();
        this.estatura = pacienteAlterado.getAltura();
        this.email = pacienteAlterado.getEmail();
        this.obs = pacienteAlterado.getObs();
        this.telefone = pacienteAlterado.getTelefone();
        this.dataNascimento = pacienteAlterado.getDataNascimento();
        this.genero = pacienteAlterado.getGenero();
        this.anamnesisDate = pacienteAlterado.getDataAnamnese();
        this.diagnostico = pacienteAlterado.getDiagnostico();
        this.dataDiagnostico = pacienteAlterado.getDataDiagnostico();
        this.historicoDoencaAtual = pacienteAlterado.getHistoricoDoencaAtual();
        this.historicoDoencasAnteriores = pacienteAlterado.getHistoricoDoencasAnteriores();
        this.procedimentosTerapeuticos = pacienteAlterado.getProcedimentosTerapeuticos();
        this.avaliacoes = pacienteAlterado.getAvaliacoes();
    }

    @Override
    public int compareTo(@NonNull Paciente paciente) {
        return this.getNome().compareTo(paciente.getNome());
    }
}
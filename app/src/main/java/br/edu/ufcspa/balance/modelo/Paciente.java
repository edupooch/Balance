package br.edu.ufcspa.balance.modelo;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by edupooch on 21/12/15.
 */
public class Paciente extends SugarRecord<Paciente> implements Serializable {

    public static final int MASCULINO = 1;
    public static final int FEMININO = 2;
    public static final String DATA_NULA = "01/01/1800";

    public Long id = null;
    private String nome;
    private String telefone;
    private String email;
    private double massa;
    private double estatura;
    private String dataNascimento;
    private int genero;
    private String obs;
    private String caminhoFoto;

    private String anamnesisDate;
    private String diagnostico;
    private String dataDiagnostico;
    private String historicoDoencaAtual;
    private String historicoDoencasAnteriores;
    private String procedimentosTerapeuticos;

    @Ignore
    private List<Avaliacao> avaliacoes;

    public Paciente() {
    }

    public String getCaminhoFoto() {
        return caminhoFoto;
    }

    public void setCaminhoFoto(String caminhoFoto) {
        this.caminhoFoto = caminhoFoto;
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

    public double getMassa() {
        return massa;
    }

    public void setMassa(double massa) {
        this.massa = massa;
    }

    public double getEstatura() {
        return estatura;
    }

    public void setEstatura(double estatura) {
        this.estatura = estatura;
    }

    @Override
    public String toString() {
        return getNome();
    }

    public int getGenero() {
        return genero;
    }

    public void setGenero(int genero) {
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

    public String getAnamnesisDate() {
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

    public List<Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }

    public void setAvaliacoes(List<Avaliacao> avaliacoes) {
        this.avaliacoes = avaliacoes;
    }

    public void copy(Paciente pacienteAlterado) {
        this.nome = pacienteAlterado.getNome();
        this.massa = pacienteAlterado.getMassa();
        this.estatura = pacienteAlterado.getEstatura();
        this.email = pacienteAlterado.getEmail();
        this.obs = pacienteAlterado.getObs();
        this.telefone = pacienteAlterado.getTelefone();
        this.dataNascimento = pacienteAlterado.getDataNascimento();
        this.genero = pacienteAlterado.getGenero();
        this.caminhoFoto = pacienteAlterado.getCaminhoFoto();
        this.anamnesisDate = pacienteAlterado.getAnamnesisDate();
        this.diagnostico = pacienteAlterado.getDiagnostico();
        this.dataDiagnostico = pacienteAlterado.getDataDiagnostico();
        this.historicoDoencaAtual = pacienteAlterado.getHistoricoDoencaAtual();
        this.historicoDoencasAnteriores = pacienteAlterado.getHistoricoDoencasAnteriores();
        this.procedimentosTerapeuticos = pacienteAlterado.getProcedimentosTerapeuticos();
        this.avaliacoes = pacienteAlterado.getAvaliacoes();
    }
}
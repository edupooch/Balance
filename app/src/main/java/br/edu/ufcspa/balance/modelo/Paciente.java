package br.edu.ufcspa.balance.modelo;

import com.orm.SugarRecord;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by edupooch on 21/12/15.
 */
public class Paciente extends SugarRecord<Paciente> implements Serializable {

    public static final int MASCULINO = 1;
    public static final int FEMININO = 0;
    public static final String DATA_NULA = "01/01/1800";

    public Long id = null;
    private String nome;
    private String telefone;
    private String email;
    private double massa;
    private double estatura;
    private Date dataNascimento;
    private int genero;
    private String obs;
    private String caminhoFoto;

    private Date anamnesisDate;
    private String diagnostico;
    private Date dataDiagnostico;
    private String historicoDoencaAtual;
    private String historicoDoencasAnteriores;
    private String procedimentosTerapeuticos;
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

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
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

    public Date getAnamnesisDate() {
        return anamnesisDate;
    }

    public void setAnamnesisDate(Date anamnesisDate) {
        this.anamnesisDate = anamnesisDate;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public Date getDataDiagnostico() {
        return dataDiagnostico;
    }

    public void setDataDiagnostico(Date dataDiagnostico) {
        this.dataDiagnostico = dataDiagnostico;
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
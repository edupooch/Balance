package br.edu.ufcspa.balance.modelo;

import com.orm.SugarRecord;

import java.io.Serializable;
import java.util.Date;


public class Avaliacao extends SugarRecord<Avaliacao> implements Serializable {

    public static final int OLHOS_ABERTOS = 1;
    public static final int OLHOS_FECHADOS = 2;
    public static final int UMA_PERNA = 1;
    public static final int DUAS_PERNAS = 2;

    public Long id;
    private Long idPaciente;
    private Date data;

    private Integer olhos;
    private Double altura;
    private Integer pernas;
    private Integer mitigacao;
    private Integer frequencia;
    private Double velocidade;
    private String observacoes;
    private Integer Duracao;

    private Double centro_X;
    private Double centro_Y;
    private Double centro_Z;

    private String dadosGiroscopio;
    private String dadosAcelerometro;


    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Integer getOlhos() {
        return olhos;
    }

    public void setOlhos(Integer olhos) {
        this.olhos = olhos;
    }

    public Double getAltura() {
        return altura;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }

    public Integer getPernas() {
        return pernas;
    }

    public void setPernas(Integer pernas) {
        this.pernas = pernas;
    }

    public Integer getMitigacao() {
        return mitigacao;
    }

    public void setMitigacao(Integer mitigacao) {
        this.mitigacao = mitigacao;
    }

    public Integer getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(Integer frequencia) {
        this.frequencia = frequencia;
    }

    public Double getVelocidade() {
        return velocidade;
    }

    public void setVelocidade(Double velocidade) {
        this.velocidade = velocidade;
    }

    public Double getCentro_X() {
        return centro_X;
    }

    public void setCentro_X(Double centro_X) {
        this.centro_X = centro_X;
    }

    public Double getCentro_Y() {
        return centro_Y;
    }

    public void setCentro_Y(Double centro_Y) {
        this.centro_Y = centro_Y;
    }

    public Double getCentro_Z() {
        return centro_Z;
    }

    public void setCentro_Z(Double centro_Z) {
        this.centro_Z = centro_Z;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getDadosGiroscopio() {
        return dadosGiroscopio;
    }

    public void setDadosGiroscopio(String dadosGiroscopio) {
        this.dadosGiroscopio = dadosGiroscopio;
    }

    public String getDadosAcelerometro() {
        return dadosAcelerometro;
    }

    public void setDadosAcelerometro(String dadosAcelerometro) {
        this.dadosAcelerometro = dadosAcelerometro;
    }

    public Integer getDuracao() {
        return Duracao;
    }

    public void setDuracao(Integer duracao) {
        Duracao = duracao;
    }
}

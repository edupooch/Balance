package br.edu.ufcspa.balance.modelo;

import com.orm.SugarRecord;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class Avaliacao extends SugarRecord<Avaliacao> implements Serializable {

    public static final String DATA_NULA = "01/01/1800";
    public static final int OLHOS_ABERTOS = 1;
    public static final int OLHOS_FECHADOS = 2;
    public static final int UMA_PERNA = 1;
    public static final int DUAS_PERNAS = 2;


    public Long id = null;
    private Long idPaciente = null;
    private Date data = null;

    private Integer olhos = null;
    private Double altura = null;
    private Integer pernas = null;
    private Integer mitigacao = null;
    private Integer frequencia = null;
    private Double area = null;
    private String observacoes = null;
    private Integer Duracao = null;


    private Double cetro_X = null;
    private Double cetro_Y = null;
    private Double cetro_Z = null;

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

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Double getCetro_X() {
        return cetro_X;
    }

    public void setCetro_X(Double cetro_X) {
        this.cetro_X = cetro_X;
    }

    public Double getCetro_Y() {
        return cetro_Y;
    }

    public void setCetro_Y(Double cetro_Y) {
        this.cetro_Y = cetro_Y;
    }

    public Double getCetro_Z() {
        return cetro_Z;
    }

    public void setCetro_Z(Double cetro_Z) {
        this.cetro_Z = cetro_Z;
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

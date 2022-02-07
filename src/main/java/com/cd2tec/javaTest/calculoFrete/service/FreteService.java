package com.cd2tec.javaTest.calculoFrete.service;

import com.cd2tec.javaTest.calculoFrete.dto.DadosCarga;
import com.cd2tec.javaTest.calculoFrete.dto.DadosViaCep;
import com.cd2tec.javaTest.calculoFrete.entity.DadosFrete;
import com.cd2tec.javaTest.calculoFrete.repository.FreteRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class FreteService {

    @Autowired
    private FreteRepository freteRepository;

    public String calculaFrete(DadosCarga dadosCarga) throws Exception{
        String webService =  "https://viacep.com.br/ws/";
        String urlCepOrigem = webService+dadosCarga.getCepOrigem()+"/json";
        String urlCepDestino = webService+dadosCarga.getCepDestino()+"/json";

        DadosViaCep dadosOrigem = consultaViaCep(urlCepOrigem);
        DadosViaCep dadosDestino = consultaViaCep(urlCepDestino);

        DadosFrete dadosFrete = new DadosFrete();
        calculaValorFrete(dadosOrigem,dadosDestino,dadosCarga,dadosFrete);
        setDadosFrete(dadosFrete,dadosCarga);
        freteRepository.save(dadosFrete);
        return "sucesso";
    }

    public DadosViaCep consultaViaCep(String urlCep) throws IOException {
            URL url = new URL(urlCep);
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            BufferedReader resposta = new BufferedReader(new InputStreamReader((conexao.getInputStream())));
            String jsonString = converteJsonEmString(resposta);
            Gson gson = new Gson();
            return gson.fromJson(jsonString, DadosViaCep.class);
    }

    public void calculaValorFrete(DadosViaCep dadosOrigem, DadosViaCep dadosDestino, DadosCarga dadosCarga, DadosFrete dadosFrete){
        if(dadosOrigem.getDdd() == dadosDestino.getDdd()) {
            dadosFrete.setVlTotalFrete(dadosCarga.getPeso()/2);
            dadosFrete.setDataPrevistaEntrega(LocalDate.now().plusDays(1L));
        }
        else if(dadosOrigem.getUf().equals(dadosDestino.getUf())){
            Double desconto = dadosCarga.getPeso() * 0.75;
            dadosFrete.setVlTotalFrete(dadosCarga.getPeso() - desconto);
            dadosFrete.setDataPrevistaEntrega(LocalDate.now().plusDays(3L));
        }
        else {
            dadosFrete.setVlTotalFrete(dadosCarga.getPeso());
            dadosFrete.setDataPrevistaEntrega(LocalDate.now().plusDays(10L));
        }
    }

    public void setDadosFrete(DadosFrete dadosFrete, DadosCarga dadosCarga){
        LocalDate data = LocalDate.now();
        dadosFrete.setPeso(dadosCarga.getPeso());
        dadosFrete.setCepOrigem(dadosCarga.getCepOrigem());
        dadosFrete.setCepDestino(dadosCarga.getCepDestino());
        dadosFrete.setNomeDestinatario(dadosCarga.getNomeDestinatario());
        dadosFrete.setDataConsulta(data);
    }

    public String converteJsonEmString(BufferedReader bufferedReader) throws IOException {
        String resposta, jsonEmString = "";
        while ((resposta = bufferedReader.readLine()) != null) {
            jsonEmString += resposta;
        }
        return jsonEmString;
    }

    public DadosFrete consultaFrete(Long id){
        return freteRepository.findById(id).orElse(null);
    }
}

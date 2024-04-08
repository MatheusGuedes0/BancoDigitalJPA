/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.cdb.BancoDigitalJPA.service;

import br.com.cdb.BancoDigitalJPA.entity.Cartao;
import br.com.cdb.BancoDigitalJPA.entity.CartaoCredito;
import br.com.cdb.BancoDigitalJPA.entity.CartaoDebito;
import br.com.cdb.BancoDigitalJPA.entity.Conta;
import br.com.cdb.BancoDigitalJPA.entity.TipoCliente;
import br.com.cdb.BancoDigitalJPA.exception.SaldoInsuficienteException;
import br.com.cdb.BancoDigitalJPA.exception.SenhaIncorretaException;
import br.com.cdb.BancoDigitalJPA.repository.CartaoRepository;
import br.com.cdb.BancoDigitalJPA.repository.ContaRepository;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author mathe
 */
@Service
public class CartaoService {

    @Autowired
    private CartaoRepository cartaoRepository;
    @Autowired
    private ContaRepository contaRepository;

    public Cartao criarCartao(Class<? extends Cartao> tipoCartao, String senha, Conta conta) {
        Random random = new Random();
        Optional<Conta> contaOptional = contaRepository.findById(conta.getId());
        if (contaOptional.isPresent()) {
            Conta contaObject = contaOptional.get();
            if (CartaoCredito.class.equals(tipoCartao)) {
                CartaoCredito cartaoCredito = new CartaoCredito();
                Calendar calendar = Calendar.getInstance();
                cartaoCredito.setAtivo(true);
                cartaoCredito.setSenha(senha);
                cartaoCredito.setConta(contaObject);
                calendar.setTime(new Date());
                calendar.add(Calendar.MONTH, 1);
                Date data = calendar.getTime();
                cartaoCredito.setDataFatura(data);
                cartaoCredito.setValorFatura(0);
                cartaoCredito.setNumero(random.nextLong((1 * Long.SIZE), Long.MAX_VALUE));
                if (contaObject.getCliente().getTipoCliente() == TipoCliente.COMUM) {
                    cartaoCredito.setLimiteTotalDoCartao(1000);
                    cartaoCredito.setLimiteAtual(1000);
                } else if (cartaoCredito.getConta().getCliente().getTipoCliente() == TipoCliente.SUPER) {
                    cartaoCredito.setLimiteTotalDoCartao(2000);
                    cartaoCredito.setLimiteAtual(2000);
                } else if (cartaoCredito.getConta().getCliente().getTipoCliente() == TipoCliente.PREMIUM) {
                    cartaoCredito.setLimiteTotalDoCartao(6000);
                    cartaoCredito.setLimiteAtual(6000);
                }

                return cartaoRepository.save(cartaoCredito);
            } else if (CartaoDebito.class.equals(tipoCartao)) {
                CartaoDebito cartaoDebito = new CartaoDebito();
                cartaoDebito.setAtivo(true);
                cartaoDebito.setSenha(senha);
                cartaoDebito.setConta(contaObject);
                cartaoDebito.setNumero(random.nextLong((1 * Long.SIZE), Long.MAX_VALUE));
                cartaoDebito.setLimiteDiario(1000.00);
                return cartaoRepository.save(cartaoDebito);
            } else {
                return null;
            }
        } else {
            throw new RuntimeException("Conta não encontrada!!");
        }
    }

    public Cartao realizarPagamentos(Long numeroCartao, String senha, String numeroBoleto) {
        Cartao cartao = cartaoRepository.findByNumero(numeroCartao);
        // Nessa classe, implementariamos uma função de pagar algo a partir de uma requisição externa
        // com o número do boleto. Como não aprendemos a fazer isso, o metodo realizarPagamentos
        // apenas simula um pagamento.
        // Boleto boleto = boleto.buscarBoleto(numeroBoleto); // constroiria o objeto boleto a partir do numero
        // double valor = boleto.getValor(); // pegaria o valor do metodo construido
        double valor = 800; // simulando o valor de um boleto

        // Estou ciente de que aqui precisa de uma implementação mais elaborada com Token usando Spring Security
        // Como não aprendemos no curso sobre token, achei melhor deixar em implementação simples
        // para evitar classes JWT's que não foram requeridas no projeto final
        if (cartao instanceof CartaoCredito) {
            CartaoCredito cartaoCredito = (CartaoCredito) cartaoRepository.findByNumero(numeroCartao);
            if (cartaoCredito.getSenha().equals(senha)) {
                if (valor <= cartaoCredito.getLimiteAtual() && cartaoCredito.isAtivo()) {
                    cartaoCredito.setLimiteAtual(cartaoCredito.getLimiteAtual() - valor);
                    cartaoCredito.setValorFatura(cartaoCredito.getValorFatura() + valor);
                    if (cartaoCredito.getLimiteAtual() == 0) {
                        cartaoCredito.setAtivo(false);
                    }
                    return cartaoRepository.save(cartaoCredito);
                } else {
                    throw new RuntimeException("Limite do Cartão atingido!");
                }
            } else {
                throw new RuntimeException("Senha incorreta, impossível realizar pagamento!");
            }
        } else {
            CartaoDebito cartaoDebito = (CartaoDebito) cartaoRepository.findByNumero(numeroCartao);
            if (cartaoDebito.getSenha().equals(senha)) {
                if (valor <= cartaoDebito.getConta().getSaldoConta() && cartaoDebito.getLimiteDiario() <= valor) {
                    cartaoDebito.getConta().setSaldoConta(cartaoDebito.getConta().getSaldoConta() - valor);

                    //Aqui seria bom implementar o histórico de transações do dia para que o limiteDiário voltasse toda vez que desse
                    //24 horas. Como não estamos trabalhando com automações de software, ele desconta o militeDiário
                    //ignorando os dias
                    cartaoDebito.setLimiteDiario(cartaoDebito.getLimiteDiario() - valor);
                    return cartaoRepository.save(cartaoDebito);
                } else {
                    throw new RuntimeException("Saldo insuficiente ou limite diário excedido!");
                }
            } else {
                throw new RuntimeException("Senha incorreta, impossível realizar pagamento!");
            }
        }
    }

    public Cartao mudarSenha(Long id, String antigaSenha, String novaSenha, String novaSenhaConfirm) {
        Optional<Cartao> cartaoOptional = cartaoRepository.findById(id);
        if (cartaoOptional.isPresent()) {
            Cartao cartao = cartaoOptional.get();
            if (cartao.getSenha().equals(antigaSenha)) {
                if (novaSenha.equals(novaSenhaConfirm)) {
                    cartao.setSenha(novaSenha);
                    return cartaoRepository.save(cartao);
                } else {
                    throw new SenhaIncorretaException("A senhas não são iguais!");
                }
            } else {
                throw new RuntimeException("Senha incorreta!!");
            }
        } else {
            throw new RuntimeException("Cartao não encontrado!!");
        }
    }

    public Cartao ativarCartao(Long id, String senha, boolean ativar) {
        Optional<Cartao> cartaoOptional = cartaoRepository.findById(id);
        if (cartaoOptional.isPresent()) {

            Cartao cartao = cartaoOptional.get();
            if (cartao.getSenha().equals(senha)) {
                cartao.setAtivo(ativar);
                return cartaoRepository.save(cartao);
            } else {
                throw new RuntimeException("Senha incorreta!!");
            }
        } else {
            throw new RuntimeException("Cartao não encontrado!!");
        }
    }

    public Cartao ajustarLimite(Long id, String senha, double limite) {
        Optional<Cartao> cartaoOptional = cartaoRepository.findById(id);

        if (cartaoOptional.isPresent()) {
            Cartao cartao = cartaoOptional.get();
            if (cartao.getSenha().equals(senha)) {
                if (cartao instanceof CartaoCredito) {
                    CartaoCredito cartaoCredito = (CartaoCredito) cartao;
                    if (cartao.getConta().getCliente().getTipoCliente() == TipoCliente.COMUM) {
                        if (limite <= 1000) {
                            cartaoCredito.setLimiteTotalDoCartao(limite);
                            cartaoCredito.setLimiteAtual(limite - cartaoCredito.getValorFatura());
                        } else {
                            throw new RuntimeException("limite não permitido para clientes Comuns. "
                                    + "experimente os planos Super e Premium agora mesmo!");
                        }
                    } else if (cartao.getConta().getCliente().getTipoCliente() == TipoCliente.SUPER) {
                        if (limite <= 5000) {
                            cartaoCredito.setLimiteTotalDoCartao(limite);
                            cartaoCredito.setLimiteAtual(limite - cartaoCredito.getValorFatura());
                        } else {
                            throw new RuntimeException("limite não permitido para clientes Super. "
                                    + "experimente o plano Premium agora mesmo!");
                        }
                    } else if (cartao.getConta().getCliente().getTipoCliente() == TipoCliente.PREMIUM) {
                        if (limite <= 10000) {
                            cartaoCredito.setLimiteTotalDoCartao(limite);
                            cartaoCredito.setLimiteAtual(limite - cartaoCredito.getValorFatura());
                        } else {
                            throw new RuntimeException("Desculpe, o seu limite não pode ultrapassar R$ 10.000,00");
                        }
                    }
                } else if (cartao instanceof CartaoDebito) {
                    CartaoDebito cartaoDebito = (CartaoDebito) cartao;

                    if (limite <= 2000) {
                        cartaoDebito.setLimiteDiario(limite);
                    } else {
                        throw new RuntimeException("O limite diário máximo permitido é R$ 2000.00 !!");
                    }

                } else {
                    throw new RuntimeException("Tipo de cartao não suportado!!");
                }
                return cartaoRepository.save(cartao);
            } else {
                throw new SenhaIncorretaException("Senha incorreta!!");
            }
        } else {
            throw new RuntimeException("Cartao não encontrado!!");
        }
    }

    public List<Cartao> getCartao() {
        return cartaoRepository.findAll();
    }

    public void aplicarTaxaSobreFatura() {

        List<Cartao> cartoes = getCartao();
        for (Cartao cartao : cartoes) {
            if (cartao instanceof CartaoCredito) {
                CartaoCredito cartaoCredito = (CartaoCredito) cartao;
                double porcetagemSobLimite = cartaoCredito.getLimiteTotalDoCartao() * 0.8;
                if (cartaoCredito.getValorFatura() >= porcetagemSobLimite) {
                    double taxa = cartaoCredito.getValorFatura() * 0.05;
                    cartaoCredito.setValorFatura(cartaoCredito.getValorFatura() + taxa);
                    cartaoRepository.save(cartaoCredito);
                }
            }
        }
    }
}

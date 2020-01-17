package br.com.hbsis.email;

import br.com.hbsis.item.Item;
import br.com.hbsis.pedido.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Component
public class EmailSender {

    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void enviarEmail(Pedido pedido, List<Item> items) throws MessagingException {

        String host = "smtp.gmail.com";

        Properties props = new Properties();
        setProps(props, host);

        Session session = setSession(props);

        MimeMessage message = new MimeMessage(session);
        message.setSubject("O pedido #" + pedido.getCodigo() +" foi aprovado ");
        message.setContent(pedido.getFuncionario().getNome()  + "\r\n"
                + ", o seu pediodo #<b>" + pedido.getCodigo() + "</b>\r\n" +
                "Do fornecedor: "+ pedido.getPeriodoVendas().getFornecedor().getRazaoSocial() + " foi aprovado" + "\r\n" +
                "Com os seguintes itens: <p>" + "\r\n" +
                listaItens(items)+
                "A data de retirado do seu pedido é <b>" + pedido.getPeriodoVendas().getDataRetiradaPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "</b>, \r\n" +
                "O endereço para a retirada é <b>" + pedido.getPeriodoVendas().getFornecedor().getEndereco() + "</b>\r\n" +", para mais informações entre em contato pelo Telefone: <b>" +
                pedido.getPeriodoVendas().getFornecedor().getTelefone() + "</b>, ou pelo E-mail: " + pedido.getPeriodoVendas().getFornecedor().getEmail(), "text/html");
        message.setRecipients(Message.RecipientType.TO,pedido.getFuncionario().getEmail());
        message.setFrom("informacoespedido@gmail.com");

        message.saveChanges();
        try {
            javaMailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void setProps(Properties props, String host) {
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
    }

    private static Session setSession(Properties props) {
                return Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("informacoespedido", "senhaemail");
                    }
                });
    }

    public String listaItens(List<Item> itemList) {

        StringBuilder lista = new StringBuilder();

        for (Item item : itemList) {
            lista.append("<b>" + "#");
            lista.append(item.getProduto().getLinhaDeCategoria().getNomeLinhaCategoria());
            lista.append(" ").append(item.getProduto().getNomeProduto());
            lista.append(" | Unidades: ");
            lista.append(item.getQuantidade());
            lista.append("</b><p>");
            lista.append("\r\n");
        }
        return lista.toString();
    }
}

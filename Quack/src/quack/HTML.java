package quack;

public interface HTML {

    // Esta interface especifica funções para construção de páginas HTML
    // a serem enviadas ao browser através do servidor HTTP.
    
    static string errorPage(String msg);
    // Compõe uma página HTML com a mensagem de erro {msg}.
}

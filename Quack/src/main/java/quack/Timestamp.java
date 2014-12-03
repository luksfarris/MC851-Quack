package quack;

public interface Timestamp {

  // Funções para gerenciar datahoras (data + hora)
  // 
  // Alguns eventos, como o envio ou re-envio de mensagens, são
  // rotulados com uma /datahora/ (data + hora). Externamente, uma
  // datahora é uma {String} no formato "%Y-%m-%d %H:%M:%S %Z" onde
  // "%Z" é o fuso horário. 
  //
  // As datahoras neste formato externo serão convertidas
  // internamente para um {long int}: um número inteiro
  // de segundos desde a "época UNIX" (1970-01-01 00:00:00 UTC)
  // 
  // Por enquanto, vamos usa apenas o fuso horário "UTC" (praticamente a 
  // hora de Londres).  ??{ Mais tarde podemos implementar códigos de fusos locais,
  // como "BRST", ou a notação geral "(+0300)" para UTC + 3 horas. }??
  
  public long now();
  // Retorna a datahora atual no formato interno.
  
  public String toString(long timestamp, String timezone);
  // Converte uma datahora do formato intern para o formato externo,
  // com o fuso horário especificado pelo parâmetro {timezone}.
  // Por enquanto, {timezone} deve ser obrigatoriamente "UTC".
  
  public long fromString(String timestamp);
  // Converte uma datahora do formato externo para o formato interno.
  // Por enquanto, a cadeia {timestamp} deve terminar com " UTC", e
  // a conversão vai usar o fuso horário UTC.
  
  }

---
title: Integração Stone com SDK Android

language_tabs:
  - java: Java

search: true
---

# Introdução

O SDK da Stone para Android oferece uma forma simples e rápida para iniciar sua integração. Com o SDK é possível integrar um dispositivo com bluetooth e Android 2.3 ou superior com o serviço Stone. Para isso, basta importar as bibliotecas `XStream` e `sdkstoneapplication.jar` em seu projeto.

# Integração

## Envio da transação

```java
StartTransaction.sendTransactionToStoneApplication(getApplicationContext(),
                                                   amount.getText().toString(),
                                                   getChecked(),
                                                   numberOfParcel,
                                                   parcelType,
                                                   demand );
```

O usuário terá uma transação de interface que o levará da sua aplicação para a aplicação da Stone. O SDK faz a comunicação com o aplicativo [Stone Mobile](https://play.google.com/store/apps/details?id=br.com.stone) que, por sua vez, se comunica com o PINPad. O aplicativo está encarregado de avisar quando há ou não uma conexão com algum PINpad. Se o usuário não estiver conectado com nenhum dispositivo, o aplicativo exibirá um botão para buscar e conectar com algum dispositivo.

A conexão com o PINpad pode ser criada automaticamente após a primeira conexão. Para essa função ser habilitada, é necessário ir em CONFIGURAÇÕES, localizada na aba lateral e selecionar a opção de ‘Conexão automática’.

Quando esta opção está marcada, um serviço é ativado. Esse serviço é responsável por verificar a conexão com o PINpad a cada 5 minutos. Se nenhum PINpad estiver conectado com o dispositivo, o mesmo tentará criar conexão com o último PINpad conectado com o dispositivo.

<aside class="warning">
OBS: Lembrando que se o aplicativo da Stone for fechado, a conexão será interrompida por alguns segundos, e se a opção de ‘Conexão automática’ estiver marcada, dentro de alguns segundos e conexão irá o serviço será ativado e irá conectar com o PINpad. 
</aside>

## Retorno da transação

```java
String xmlTransaction  = backActivity.getString("xmlTransaction");
ReturnOfTransactionXml mReturnOfTransactionXml;
mReturnOfTransactionXml = TransactionResponse.getTransaction(this, xmlTransaction, backActivity);

Log.i("sdk_stone",
        "\n\n======== Dados recebidos SDK ========"
        + "\nValor               : " + mReturnOfTransactionXml.amount
        + "\nARN                 : " + mReturnOfTransactionXml.arn
        + "\nParcelas            : " + mReturnOfTransactionXml.parcel
        + "\nBandeira            : " + mReturnOfTransactionXml.flag
        + "\nCA                  : " + mReturnOfTransactionXml.ca
        + "\nStatus              : " + mReturnOfTransactionXml.status
        + "\nData                : " + mReturnOfTransactionXml.date
        + "\nAmountOfInst.       : " + mReturnOfTransactionXml.amountOfInstallments
        + "\nDemandId            : " + mReturnOfTransactionXml.demandId
        + "\nTipo da transação   : " + mReturnOfTransactionXml.transactionType);
```

Quando o autorizador da Stone responder a transação, aprovada ou negada, o aplicativo da Stone irá retornar para o aplicativo que o chamou e enviará algumas informações que foram recebidas do autorizador.

O método getExternalInformations() fica responsável por verificar se há ou não um retorno de transação. É aconselhável que ele fique no método “onResume()” da main do seu projeto. O aplicativo da Stone retornará um XML contendo as informações da transação: `getIntent().getExtras().getString("xmlTransaction")`

# Classes

| Nome | Descrição |
| ---- | --------- |
| StartTransaction | Essa classe envia uma transação para o aplicativo Stone Mobile. |
| StartTypedTransaction | Essa classe envia uma transação digitada para o aplicativo Stone Mobile. |
| StartCancellation | Essa classe permite o cancelamento de transações |

## StartTransaction

Essa classe envia uma transação para o aplicativo Stone Mobile.

`void StartTransaction.sendTransactionToStoneApplication(Context context, String amount, Integer typeOfPurchase, Integer numberOfParcels, Integer typeParcels, Integer demandId)`

```java
StartTransaction.sendTransactionToStoneApplication(getApplicationContext(),      // contexto
                                                   amount.getText().toString(),  // valor em centavos
                                                   getChecked()                  // tipo da transação
                                                   numberOfParcel,               // número de parcelas
                                                   parcelType,                   // tipo de parcelas
                                                   demand);                      // id da transação
```

| Tipo | Parâmetro | Descrição |
| ---- | --------- | --------- |
| Context | context | Contexto da aplicação |
| String | amount | Valor da transação **em centavos** |
| Integer | typeOfPurchase | Tipo da compra (1 para Débito ou 2 para Crédito)  |
| Integer | numberOfParcels | Número de parcelas (se houve parcerlamento) |
| Integer | typeOfParcels | Tipo de parcela (0 - à vista, 1 - lojista, 2 - emissor |
| Integer | demandId | Um identificador da transação |

## StartTypedTransaction

Essa classe envia uma transação digitada para o aplicativo Stone Mobile

`void StartTypedTransaction.sendTypedTransactionToStoneApplication(Context context, String amount, String PAN, String CVV, String date, Integer numberOfParcels, Integer typeParcels)`

```java
StartTransaction.sendTypedTransactionToStoneApplication(getApplicationContext(),     // contexto
                                                        amount.getText().toString(), // valor em centavos
                                                        pan,                         // número do cartão
                                                        cvv,                         // CVV
                                                        date,                        // expiração
                                                        numberOfParcel,              // número de parcelas
                                                        parcelType);                 // tipo de parcelas
```

| Tipo | Parâmetro | Descrição |
| ---- | --------- | --------- |
| Context | context | Contexto da aplicação |
| String | amount | Valor da transação **em centavos** |
| String | PAN | Número do cartão do cliente |
| String | CVV | Código de segurança do verso do cartão |
| String | date | Data de expiração do cartão |
| Integer | numberOfParcels | Número de parcelas (se houve parcerlamento) |
| Integer | typeOfParcels | Tipo de parcela (0 - à vista, 1 - lojista, 2 - emissor |

## StartCancellation

Essa classe permite o cancelamento de transações

`void StartCancellation.sendCancellationToStoneApplication(Context context, String arn, String ca)`

```java
StartCancellation.sendCancellationToStoneApplication(getApplicationContext(), // contexto
                                                     arn,                     // Numeração da transação
                                                     ca);                     // Código da transação
```

| Tipo | Parâmetro | Descrição |
| ---- | --------- | --------- |
| Context | context | Contexto da aplicação |
| String | arn | Numeração da transação |
| String | ca | Código da transação |

## TransactionResponse

`ReturnOfTransactionXml getTransaction(Activity activity, String xmlReceive, Bundle backActivity)`

```java
mReturnOfTransactionXml = TransactionResponse.getTransaction(this,xmlTransaction,backActivity);
```

| Tipo | Parâmetro | Descrição |
| ---- | --------- | --------- |
| Activity | activity | Contexto da aplicação |
| String | xmlReceive | A String ‘xml’ recebe uma String contida na variável ‘backActivity’. A mesma deve ser passada, apenas. |
| Bundle | backActivity | O retorno da Intent |
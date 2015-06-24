---
title: Integração Stone com SDK Android

language_tabs:
  - java: Java

search: true
---

# Introdução

O SDK da Stone para Android oferece uma forma simples e rápida para iniciar sua integração. Com o SDK é possível integrar um dispositivo com bluetooth e Android 2.3 ou superior com o serviço Stone. Para isso, basta importar as bibliotecas `XStream` e `sdkstoneapplication.jar` em seu projeto.

Você poderá fazer o download do SDK, XStream e do código de exemplo no repositório android-sdk da Stone no Github: [android-sdk](https://github.com/stone-pagamentos/sdk-android)

## Conceitos básicos

### Informações de básicas referentes à integração

* A integração requer android 2.3 ou superior;
* Um tablet ou celular com bluetooth;
* Utilizar as funções disponíveis no .jar de integração;
* Essa documentação foi criada dando como exemplos a aplicação ‘StoneSDK’, desenvolvido pela Stone.
* Essa SDK usa de suporte o jar XStream na versão 1.4.7 (xstream-1.4.7.jar).

## Como Integrar com a Stone

* Certifique-se que o .jar está na pasta ‘libs’ do seu projeto, como na imagem ao lado:
* Faça o import do jar da XStream;
* Após incluir o .jar de integração na pasta ‘libs’, você poderá iniciar a integração.

# Integração

## Envio da transação

<aside class="warning">O método `sendTransactionToStoneApplication` está obsoleto e será removido em versões futuras</aside>

```java
@deprecated
StartTransaction.sendTransactionToStoneApplication(getApplicationContext(),
                                                   amount.getText().toString(),
                                                   getChecked(),
                                                   numberOfParcel,
                                                   parcelType,
                                                   demand,
                                                   autoFlagCheckedTextView.isChecked() ? 1 : 2,
                                                   R.anim.fade_in,
                                                   R.anim.fade_out );
```

```java
StartTransaction.startNewTransaction(this, mTransaction, animStart, animEnd);
```

O usuário terá uma transação de interface que o levará da sua aplicação para a aplicação da Stone. O SDK faz a comunicação com o aplicativo [Stone Mobile](https://play.google.com/store/apps/details?id=br.com.stone) que, por sua vez, se comunica com o PINPad. O aplicativo está encarregado de avisar quando há ou não uma conexão com algum PINpad. Se o usuário não estiver conectado com nenhum dispositivo, o aplicativo exibirá um botão para buscar e conectar com algum dispositivo.

A conexão com o PINpad pode ser criada automaticamente após a primeira conexão. Para essa função ser habilitada, é necessário ir em CONFIGURAÇÕES, localizada na aba lateral e selecionar a opção de ‘Conexão automática’.

Quando esta opção está marcada, um serviço é ativado. Esse serviço é responsável por verificar a conexão com o PINpad a cada 5 minutos. Se nenhum PINpad estiver conectado com o dispositivo, o mesmo tentará criar conexão com o último PINpad conectado com o dispositivo.

<aside class="warning">
OBS: Lembrando que se o aplicativo da Stone for fechado, a conexão será interrompida por alguns segundos, e se a opção de ‘Conexão automática’ estiver marcada, dentro de alguns segundos e conexão irá o serviço será ativado e irá conectar com o PINpad. 
</aside>

### Retorno da transação

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

Quando o autorizador da Stone responder a transação, isso quer dizer se ela foi aprovada ou negada, o aplicativo da Stone irá retornar para o aplicativo que chamou o mesmo e enviará algumas informações que foram recebidas do autorizador.

O StoneSDK possui um método que fica responsável por verificar se há ou não um retorno de transação, o getExternalInformations(), é aconselhável que ele fique no método “onResume()” da main do seu projeto.
O aplicativo da Stone retornará um XML contendo as informações da transação.

A captura da resposta, dentro desse método, é feita da seguinte forma:  `getIntent().getExtras().getString("xmlTransaction")`

A variável ‘backActivity’ recebe as informações de uma Intent e verifica se é nula.

Se a variável não for nula, é instanciada uma variável do tipo ReturnOfTransactionXml, que é uma classe responsável por serializar e desserializar uma transação e tornar a mesma um objeto.

Se ela for diferente de ‘null’, o objeto será desserealizado, sendo assim, tornando-se manipulável através da variável ‘backActivity’.

Um exemplo de manipulação dessa variável pode ser visualizada ao lado:

A classe TransactionResponse está no sdkstoneapplication.jar, e o método ‘getTransaction’ deve receber como parâmetro:

* Contexto da aplicação
* XML da transação
* Bundle

## Cancelamento

A partir dos valores que são passados de retorno após o envio de uma transação, é possível fazer o cancelamento de uma transação, se a mesma ainda permanecer no banco de dados do aplicativo da Stone.

Para fazer o cancelamento de uma transação, serão necessários:

* Código de autorização da transação
* Numeração da transação
* Contexto da aplicação

Código de autorização da transação :

|Tipo|Passagem|Descrição|
|----|--------|---------|
|String|ARN|Numeração da transação|
|String|CA|Código da transação|
|Context|getApplicationContext()|Contexto da aplicação|

O envio do cancelamento é muito parecido com o do envio de uma transação, como pode ser visto a seguir: `StartCancellation.sendCancellationToStoneApplication(getApplication(), arn, ca);`

### Resposta do Cancelamento

A resposta do cancelamento também é bastante similar à resposta da transação:

A String ‘xmlCancellation’ é a responsável por pegar a resposta da Intent do cancelamento. Uma verificação se ela é numa ou vazia, se não for, exibir no LogCat as informações.

`String xmlCancellation = backActivity.getString("xmlCancellation");`

A resposta da requisição de cancelamento possui como resposta:

* Código de autorização da transação
* Numeração da transação
* Status

Respostas do cancelamento:

|Tipo|Passagem|Descrição|
|----|--------|---------|
|String|ARN|Numeração da transação|
|String|CA|Código da transação|
|String|Status|**Approved** – Cancelamento realizado com sucesso; **Declined** – Cancelamento negado; **PartialApproved** – Parcialmente aprovada; **TchenicalError** – Erro técnico no proxy de cancelamento;|

<aside class="notice">Por padrão, a senha de cancelamento é “1234”, podendo ser alterada nas CONFIGURAÇÕES da aplicação.</aside>

## Impressão

As impressoras dos Pinpads também podem ser utilizadas pela API de integração, para isso existe a classe StartPrint.

Na classe StartPrint, estão disponíveis três métodos:

* validateListSize() – responsável por validar o tamanho de cada linha e tamanho dos caractéres.
* putSpace() – responsável por adicionar quantidades de espaços no final da lista 
* sendPrint() - responsável por  enviar a lista de impressão para o app da Stone

Para imprimir, basta criar uma lista de PrintObject, esse objeto representa cada linha que será impressa.

<aside class="notice">Ao ser passado “TAG” como tamanho ou alinhamento, será impresso um QR Code com o conteúdo que foi passado.</aside>

### 4.2 Resposta da impressão

Os commandos de impressão possuem uma resposta que devem ser esperadas junto às demais respostas na Main da aplicação.

`String xmlPrint = (seu bundle).getString(“xmlPrint”);`

Essa String deve ser passada para o método PrintResponse.getPrint();

```java
ReturnOfPrintXml returnOfPrintXml = new ReturnOfPrintXml();
returnOfPrintXml = PrintResponse.getPrint(this, xmlPrint, backActivity);
```

Desta forma você terá um objeto do tipo ReturnOfPrintXml e poderá saber se a impressão foi concluída com sucesso pela propriedade printCode:

|Tipo|Resposta|Significado|
|----|--------|---------|
|int|1|Impresso com sucesso|
|Int|2|Ocorreu um erro durante a impressão|
|Int|3|Pinpad conectado não possui impressoda (Ex: D200)|

# Classes

| Nome | Descrição |
| ---- | --------- |
| StartTransaction | Essa classe envia uma transação para o aplicativo Stone Mobile. |
| StartTypedTransaction | Essa classe envia uma transação digitada para o aplicativo Stone Mobile. |
| StartCancellation | Essa classe permite o cancelamento de transações |

## StartTransaction

Essa classe envia uma transação para o aplicativo Stone Mobile.

`void StartTransaction.startNewTransaction(Activity activity, Transaction transaction, Integer animStart, Integer animEnd);`

```java
StartTransaction.startNewTransaction(this,
                                     mTransaction,
                                     animStart,
                                     animEnd);
```

| Tipo | Parâmetro | Descrição |
| ---- | --------- | --------- |
| Context | context | Contexto da aplicação |
| Transaction | Transaction | Dados da transação |
| Integer | animStart | Animação inicial |
| Integer | animEnd | Animação final |

### Transaction

| Método | Descrição |
| --------- | --------- |
| setAmount(String amount) | Define o valor da transação |
| setTypeOfPurchase(Integer typeOfPurchase) | Tipo da compra (1- débito ou 2 - crédito )|
| setTypeOfInstalment(Integer typeParcels) | Tipo de parcela (0 -  à vista ou 1 - parcelamento) |
| setNumberOfInstalments(Integer numberOfInstalments) | Número de parcelas da transação |
| setDemandId(Integer demandId) | Uma numeração para a transação |
| setNeededConfirm(boolean isNeededConfirm) | Confirmação das informações pelo usuário |

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

## ReturnOfTransactionXML

OBS: Todos os valores que são passados, são retornados para futuras comparações, caso isso seja importante para o desenvolvedor.

| Tipo | Parâmetro | Descrição |
| ---- | --------- | --------- |
|String|Flag|MASTERCARD ou VISA|
|String|Arn|Numeração da transação|
|String|Amount|Valor da transação|
|String|Date|Formato: YYYY-MM-dd HH:mm:ss|
|String|CA|Código de autorização|
|String|Parcel|Número de parcelas|
|String|Status|APR ou DEC|
|String|TransactionType|1 – débito; 2 – crédito|
|Long|DemandID|Número do ID do pedido|

<h1 align="center"> Projeto Agenda Petshop </h1>

![Badge Status Geral](https://img.shields.io/badge/Status%20Geral-Em%20Desenvolvimento-blue?style=for-the-badge) ![Status Back-End](https://img.shields.io/badge/Back--End-Em%20Desenvolvimento-yellowgreen?style=for-the-badge) ![Status Front-End](https://img.shields.io/badge/Front--End-Em%20An%C3%A1lise-critical?style=for-the-badge) ![Previsão de término](https://img.shields.io/badge/Previs%C3%A3o%20de%20T%C3%A9rmino-Setembro-informational?style=for-the-badge)

O projeto visa auxiliar na criação de uma agenda para os banhos, tosas e serviços relacionados de um petshop. Tendo registro de clientes, e os pets dos mesmos para facilitar a atividade dos atendentes do caixa, dessa forma erros são evitados e encontrar o que se precisa mais rápido.

<a name="indice"></a>
## Índice

* [Índice](#indice)
* [Funcionalidades do Sistema](#funcionalidades-do-sistema)
* [Tecnologias Utilizadas](#tecnologias)
* [Protótipos de telas](#propototipos-de-telas)
* [Telas Finais](#telas-finais)
* [Desafios Encontrados](#desafios-encontrados)
* [Contribuições](#contribuicoes)
* [Conclusão](#conclusao)

<a name="funcionalidades-do-sistema"></a>
## Funcionalidades do sistema
O sistema é dividido em várias partes que se interligam entre si.
Sendo elas:
- [Usuários](#usuarios)
- [Clientes](#clientes)
- [Pets](#pets)
- [Serviços](#servicos)
- [Agenda](#agenda)
- [Logs](#logs)

<a name="usuarios"></a>
### Usuários
São os utilizadores do sistema, precisando ter um e-mail para fazer o login, e estarem autenticados, e das permissões corretas para poder acessar as demais funcionalidades disponíveis.
`Permissões`
- `ADMIN:` A permissão de nível mais alto é a de ADMIN, com ela todos acessos estarão liberados;
- `RUSER:` Permissão para LER usuários;
- `CUSER:` Permissão para CRIAR usuários;
- `UUSER:` Permissão para ATUALIZAR usuários;
- `DUSER:` Permissão para INATIVAR usuários, excluir usuários causaria muitos problemas (do meu ponto de vista), então resolvi manter os usuários e só deixar de monstrá-los nas opções de leitura;
- `RCLIENT:` Permissão para LER clientes;
- `CCLIENT:` Permissão para CRIAR clientes;
- `UCLIENT:` Permissão para ATUALIZAR clientes;
- `DCLIENT:` Permissão para INATIVAR clientes, pelo mesmo motivo dos usuários;
- `RPET:` Permissão para LER pets;
- `CPET:` Permissão para CRIAR pets;
- `UPET:` Permissão para ATUALIZAR pets;
- `DPET:` Permissão para EXCLUIR pets;
- `RTASK:` Permissão para LER serviços;
- `CTASK:` Permissão para CRIAR serviços;
- `UTASK:` Permissão para ATUALIZAR serviços;
- `DTASK:` Permissão para EXLUIR serviços;
- `RSCHEDULE:` Permissão para LER agendas;
- `CSCHEDULE:` Permissão para CRIAR agendas;
- `USCHEDULE:` Permissão para ATUALIZAR agendas;
- `DSCHEDULE:` Permissão para EXCLUIR agendas;
- `RREPORTS:` Permissão para LER relatórios;
> Ter criado todas essas permissões e fazê-las funcionar no Sprig Security foi um pouco trabalhoso, mas depois de entender as coisas de desenrolaram bem;

<a name="clientes"></a>
### Clientes
Clientes são os donos dos pets, e possuem as informações necessárias para se entrar em contato e entrega dos pets quando o serviço estiver concluído.

<a name="pets"></a>
### Pets
Os pets, ou bichos de estimação, são uma das partes centrais do sistema. Com eles que os serviços (a seguir) são definidos, vários campos em aberto para as características e são intrinsicamente associados aos seus donos.
> Ter colocado os pets na entidade do cliente foi um pouco trabalhoso, e tirá-los de lá também em caso de exclusão, porém o conhecimento adquirido tornou-se divertido de alguma forma;

<a name="servicos"></a>
### Serviços
Os serviços são referentes ao que o petshop tem a oferecer na agenda com relação ao banho, tosa, hidradatação e outros, como cada bicho pode ter um porte, então os serviços são definidos dessa forma. Cada um tem um preço único que será usado como custo total na agenda.
> Depois de criar a agenda eu percebi um bug na atualização de preços que afetava o custo de forna negativa, ao corrigir fiquei surpreso com o resultado bom;

<a name="agenda"></a>
### Agenda
A agenda possui informações de todas as entidades anteriores, além do dia, e horário previamente selecionados pelo cliente, e inseridos pelo usuário. Essa é uma parte crítica do sistema, porque tudo gira em torno dela, de acordo com os pets, que cada dono possuí, e os valores dos serviços referentes ao porte do pet. Então muitas verificações foram necessárias para evitar que o sistema não quebrasse e que informações erradas fossem inseridas.
> Trabalhoso e ainda precisa de ajustes por causa dos logs;

<a name="logs"></a>
### Logs
Os logs foram uma solicitação do cliente para ter controle e informações sobre tudo que os usuários do sistema fazem. Naturalmente usaria-se os *triggers* do banco de dados, mas aparentemente o MongoDB não possuí isso, então fiz a mão todas as atividades necessárias.
**Ainda precisa de ajustes e ser finalizado**

<a name="tecnologias"></a>
## Tecnologias Utilizadas
* Java
* Spring Boot
* MongoDB
* Git & GitHub
* JUnit
* JWT
* ModelMapper

<a name="propototipos-de-telas"></a>
## Protótipos das Telas
Para desenhar essas telas e apresentar no escopo eu utilizei o [Figma](https://www.figma.com/) e esse é o protótipo que pretendo usar para me basear e fazer o **Front-End**.

`Protótipo tela de Login`![Tela Petshop - Login](https://user-images.githubusercontent.com/72516703/185235086-ffad6a1d-377b-4202-b603-94ad744d7a31.png)
`Protótipo de Nova Senha`![Tela Petshop -  Nova Senha](https://user-images.githubusercontent.com/72516703/185235162-fe625e76-95f0-468d-bd36-68414631698f.png)
`Protótipo de Esqueci a Senha`![Tela Petshop - Esqueci a Senha](https://user-images.githubusercontent.com/72516703/185235215-deec5c6e-a7b8-46d1-abaa-c5ce6055ea0b.png)
`Protótipo Home`![Home](https://user-images.githubusercontent.com/72516703/185235689-dc3b6795-85d7-4e4e-8bc4-ed1d026611c1.png)
`Protótipo Agenda Calendário`![Home - Calendário](https://user-images.githubusercontent.com/72516703/185235790-33a202a2-05b2-4cd6-a7ca-1b53dac78ae7.png)
`Protótipo Agenda Adicionar`![Home - Adicionar](https://user-images.githubusercontent.com/72516703/185235846-88ab7843-a4b0-4382-80b6-4d7e991a102f.png)
`Protótipo tela Usuários`![Usuários](https://user-images.githubusercontent.com/72516703/185236110-363ec7a4-4009-445a-9682-b2b7fd32c15f.png)
`Protótipo tela Forumlário Usuário` ![Usuários - formulário](https://user-images.githubusercontent.com/72516703/185236205-b3d31f2d-4251-444a-83d3-c87425ab8050.png)
`Protótipo tela Clientes`![Clientes](https://user-images.githubusercontent.com/72516703/185236420-206f408e-8032-4b2d-a6da-e243dd65ef3c.png)
`Protótipo tela Formulário Cliente` ![Clientes - formulário](https://user-images.githubusercontent.com/72516703/185236472-4c57b197-1380-4905-b4d4-5d570eaf1615.png)
`Protótipo tela Serviços` ![Serviços](https://user-images.githubusercontent.com/72516703/185236605-50b37ce4-64ba-495b-b6d3-7070f9e72d57.png)
`Protótipo tela Formulário Serviço`![Serviços - formulário](https://user-images.githubusercontent.com/72516703/185236648-bf29fece-b3fb-41aa-bc69-aeae9446076e.png)
`Protótipo tela Relatórios`![Fluxo](https://user-images.githubusercontent.com/72516703/185236707-956ad375-c089-48aa-9a7d-2996f7319a38.png)

<a name="telas-finais"></a>
## Telas Finais
Ainda em planejamento

<a name="desafios-encontrados"></a>
## Desafios Encontrados
Nesta parte comentarei sobre alguns dos desafios que encontrei durante a construção do sistema e uma check lista do que já fiz e o que falta fazer
- [x] Iniciar o sistema 
> Pode parecer que não, mas ter iniciado foi realmente difícil e depois as dificuldades só aumentaram conforma a complexidade das atividades foram crescendo
- [x] Aprender a usar o MongoDB com Java
> Bancos de dados não relacionais são bem diferentes do que eu esperava, mas aprendi rápid ode alguma forma 
- [x] Fazer o Spring Security funcionar corretamente
> Eu tive muito trabalho para fazer isso funcionar corretamente porque a forma de utilizar o Spring Security mudou e depois de muita pesquisa consegui fazer funcionar corretamente junto com o JJWT
- [ ] Corrigir logs
- [ ] Enviar e-mail para o usuário criado
- [ ] Usuário mudar a própria senha
- [ ] Todo o Front-End
- [ ] Ajustes finais

<a name="contribuicoes"></a>
## Contribuições
Alura e o bootcamp de Java 2021
Os muitos sites com tutorias
Documentações (principalmente MongoDB e JJWT)

<a name="conclusao"></a>
## Conclusão
Pretendo terminar o projeto em Setembro, o quanto antes e disponibilizá-lo via Docker;
O aprendizado até o momento tem sido satisfatório e divertido, apesar dos bugs e problemas encontrados pelo caminho, o sistema está fluindo.
Usando o Postman para fazer as verificações e teste manuais, por mais trabalhoso que tem sido, é incrível ver tudo funcionando corretamente.
Espero usar o conhecimento adquirido para me aprimorar e desenvolver sistemas ainda mais completos e complexos.

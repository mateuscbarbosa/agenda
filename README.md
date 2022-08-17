<h1 align="center"> Projeto Agenda Petshop </h1>
![badges que preciso colocar]
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

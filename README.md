MetadataPatterner
=================

### Sumário

* [Objetivo](#objetivo)
* [Requisitos](#requisitos)
* [Uso](#uso)
 * [Utilização via interface gráfica](#gui)
 * [Utilização via console](#console)
 * [Observações](#obs)
* [Modelo de dados](#modelo-de-dados)
 * [XML](#xml)
 * [CSV](#csv)
* [Referências](#refs)


### Objetivo

O MetadataPatterner permite a padronização de valores de um item no DSpace a partir do processamento dos valores encontrados em um metadado específico.

### Requisitos

Sistemas operacionais: Windows Vista ou superior, GNU/Linux, Mac OS X.

Java Runtime: Oracle ou OpenJDK versão 7 ou superior.

### Uso

Existem 2 modos de utilizar a aplicação: uma via interface gráfica e outra via console do sistema.

#### Utilização via interface gráfica

Para a utilização via interface gráfica, é necessário preencher os campos:
* **rules.xml**: caminho do arquivo XML contendo as regras para a padronização; o arquivo XML deve seguir as especificações da seção Modelo de Dados -> XML.
* **in.csv**: caminho do arquivo CSV contendo os metadados dos itens; o arquivo CSV deve serguir as especificações da seção Modelo de Dados -> CSV.
* **out.csv**: caminho e nome do arquivo CSV que deverá ser criado após a execução do MetadataPatterner.
* **csv column**: cabeçalho da coluna do arquivo CSV (in.csv) que contem os valores que servirão de base para a vinculação; o cabeçalho informado deve conter a indicação do idioma entre colchetes (exemplos: [en], [pt], [es]), se tal indicação constar no CSV.

Exemplo:

* rules.xml: D:\data\patterner.xml
* in.csv: D:\data\items-to-be-patterned.csv
* out.csv: D:\data\patterned-items.csv
* csv column: dc.type[en]

#### Utilização via console

Existem 6 parâmetros na utilização via console e uma vez que algum desses informados o programa não executará a interface gráfica, são eles:
* **-h** ou **--help**: [Opcional] Mostra os parametros possíveis a serem utilizados.
* **-c** ou **--column (valor)**: [Obrigatório] Informar o titulo da coluna onde o programa deve analisar os valores.
* **-i** ou **--input-file (valor)**: [Obrigatório] Informar o caminho do arquivo CSV contendo os metadados dos itens; o arquivo CSV deve serguir as especificações da seção Modelo de Dados -> XML.
* **-o** ou **--output-file (valor)**: [Obrigatório] Informar o caminho e nome do arquivo CSV que deverá ser criado após a execução do MetadataPatterner.
* **-m** ou **--map-file (valor)**: [Obrigatório] Informar o caminho do arquivo XML contendo as regras para a padronização; o arquivo XML deve seguir as especificações da seção Modelo de Dados -> CSV.

#### Observações

A execução do programa leva em torno de alguns segundos, caso a execução esteja demorando recomenda-se a modificação do valor padrão da memória do java pelo parâmetro: -Xmx???m onde ??? é o número de megabytes de memória a disponibilizar.  

### Modelo de Dados

#### XML

Para a criação de regras é necessário utilizar um arquivo XML com as seguintes especificações:
1. Versão 1.0
2. Utilizar codificação UTF-8
3. Deve seguir exclusivamente o schema MetadataPatterner em http://base.repositorio.unesp.br/XMLSchema/MetadataPatterner

#### CSV

O MetadataPatterner utiliza o mesmo padrão de aquivo CSV (RFC4180) que o software DSpace.

### Referências

http://www.ietf.org/rfc/rfc4180.txt
https://wiki.duraspace.org/display/DSDOC4x/Batch+Metadata+Editing#BatchMetadataEditing-TheCSVFiles
http://base.repositorio.unesp.br/XMLSchema/MetadataPatterner
https://github.com/vitorsilverio/Item2CollectionRuler
http://www.dspace.org

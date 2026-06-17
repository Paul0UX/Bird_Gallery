# 🐦 Bird Gallery

Bird Gallery é um aplicativo Android desenvolvido para registrar e catalogar aves observadas pelo usuário. A aplicação funciona como uma galeria pessoal de observação de aves, permitindo cadastrar informações detalhadas sobre cada ave encontrada, incluindo fotografia, local de observação e descrição.

## 📋 Funcionalidades

- Cadastro de usuários e autenticação por login.
- Cadastro de aves observadas.
- Armazenamento de fotografia da ave.
- Registro do local e país da observação.
- Registro do sexo da ave (Macho/Fêmea).
- Descrição personalizada da ave.
- Visualização detalhada dos registros.
- Edição de informações cadastradas.
- Exclusão de registros.
- Listagem completa das aves cadastradas.

---

## 📸 Informações armazenadas

Cada registro de ave contém:

- Nome da ave
- Local da observação
- País
- Sexo
- Descrição
- Fotografia

Os dados são armazenados localmente no dispositivo utilizando banco de dados SQLite.

---

## 🛠️ Tecnologias Utilizadas

### Linguagem

- Kotlin

### Plataforma

- Android Studio
- Android SDK

### Banco de Dados

- SQLite

### Interface

- Android View Binding
- Material Design Components

### Navegação

- Android Navigation Component

### Componentes Android

- Activities
- Fragments
- RecyclerView
- SQLiteOpenHelper
- Activity Result API

---

## 📚 Bibliotecas Utilizadas

```kotlin
androidx.appcompat
androidx.core-ktx
androidx.activity-ktx
androidx.constraintlayout
androidx.navigation-fragment-ktx
androidx.navigation-ui-ktx
com.google.android.material
```

---

## 🚀 Como Executar o Projeto

### Pré-requisitos

- Android Studio instalado
- JDK 11 ou superior
- Dispositivo Android ou Emulador

### Passos

1. Clone o repositório:

```bash
git clone https://github.com/seu-usuario/Bird_Gallery.git
```

2. Abra o projeto no Android Studio.

3. Aguarde a sincronização do Gradle.

4. Execute o aplicativo em um dispositivo físico ou emulador Android.

---

## 📱 Como Utilizar

### 1. Cadastro/Login

Ao abrir o aplicativo:

- Informe um nome de usuário e senha.
- Caso ainda não possua cadastro, preencha os campos e clique em **Cadastrar**.
- Após o cadastro, realize o login.

### 2. Adicionar uma Ave

- Clique no botão "+".
- Preencha as informações da ave.
- Selecione uma fotografia da galeria do dispositivo.
- Salve o registro.

### 3. Visualizar

- Toque sobre uma ave cadastrada para visualizar seus detalhes completos.

### 4. Editar

- Utilize o botão de edição disponível em cada registro.

### 5. Excluir

- Utilize o botão de exclusão para remover registros da galeria.

---

## 🗄️ Estrutura do Banco de Dados

### Tabela Users

| Campo | Tipo |
|---------|---------|
| id | INTEGER |
| username | TEXT |
| password | TEXT |

### Tabela Birds

| Campo | Tipo |
|---------|---------|
| id | INTEGER |
| name | TEXT |
| location | TEXT |
| country | TEXT |
| sex | TEXT |
| description | TEXT |
| photo_path | TEXT |

---

## 🎯 Objetivo do Projeto

O objetivo do Bird Gallery é facilitar o registro e a organização de observações de aves, oferecendo uma forma simples e intuitiva de armazenar informações e fotografias de espécies encontradas durante atividades de observação da natureza.

---

## 👨‍💻 Desenvolvido por

Projeto acadêmico desenvolvido utilizando Kotlin e Android Studio para aplicação dos conceitos de desenvolvimento mobile, banco de dados local e gerenciamento de interface gráfica.

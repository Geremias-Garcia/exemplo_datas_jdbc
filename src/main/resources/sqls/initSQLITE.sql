CREATE TABLE pessoa (
    id INTEGER PRIMARY KEY,
    nome TEXT NOT NULL,
    cpf TEXT UNIQUE NOT NULL,
    telefone TEXT NOT NULL,
    email TEXT NOT NULL,
    dataNascimento DATE NOT NULL,
    genero TEXT NOT NULL,
    isAtive INTEGER NOT NULL
);


CREATE TABLE funcionario (
    id INTEGER PRIMARY KEY,
    nome TEXT NOT NULL,
    cpf TEXT UNIQUE NOT NULL,
    telefone TEXT NOT NULL,
    email TEXT NOT NULL,
    data_nascimento DATE NOT NULL,
    genero TEXT NOT NULL,
    is_active INTEGER NOT NULL,
    setor TEXT NOT NULL,
    salario REAL NOT NULL
);


CREATE TABLE medico (
    id INTEGER PRIMARY KEY,
    nome TEXT NOT NULL,
    cpf TEXT UNIQUE NOT NULL,
    telefone TEXT NOT NULL,
    email TEXT NOT NULL,
    dataNascimento DATE NOT NULL,
    genero TEXT NOT NULL,
    isAtive INTEGER NOT NULL,
    salario REAL NOT NULL,
    especialidade TEXT NOT NULL,
    crm TEXT NOT NULL
);

CREATE TABLE loginPaciente (
    id INTEGER PRIMARY KEY,
    cpf TEXT UNIQUE NOT NULL,
    senha TEXT NOT NULL
);

CREATE TABLE loginMedico (
    id INTEGER PRIMARY KEY,
    cpf TEXT UNIQUE NOT NULL,
    senha TEXT NOT NULL
);

CREATE TABLE agendamento (
    id INTEGER PRIMARY KEY,
    id_paciente INTEGER,
    id_medico INTEGER,
    data DATE,
    hora TIME,
    status TEXT,
    FOREIGN KEY (id_paciente) REFERENCES pessoa(id),
    FOREIGN KEY (id_medico) REFERENCES medico(id)
);





CREATE TABLE hosts (
    id   INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) primary key,
    ip   varchar(32) NOT NULL,
    pwd  varchar(500),
    port INTEGER,
    addtime INTEGER default 0
    );
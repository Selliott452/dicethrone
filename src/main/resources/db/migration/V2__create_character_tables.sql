CREATE TABLE player (
    uuid uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    name varchar not NULL,
    character_id varchar not NULL,
    health int not NULL,
    combo_points int not null
);

CREATE TABLE player_dice (
    player_id uuid not NULL,
    dice_id int not null
);

CREATE SEQUENCE dice_id_seq;

CREATE TABLE dice (
    id integer NOT NULL PRIMARY KEY DEFAULT nextval('dice_id_seq'),
    dice_value int NOT NULL,
    icon varchar NOT NULL,
    locked boolean NOT NULL
);
CREATE TABLE game (
    uuid uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    name varchar not NULL,
    phase varchar not NULL
)
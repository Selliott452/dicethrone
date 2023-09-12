CREATE TABLE game_player (
    game_id uuid not NULL REFERENCES game (uuid),
    player_id uuid not NULL REFERENCES player (uuid)
);
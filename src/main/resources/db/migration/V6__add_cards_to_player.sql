CREATE TABLE player_hand
(
    player_id UUID NOT NULL REFERENCES player (uuid),
    card_id VARCHAR NOT NULL,
    card_order INT NOT NULL
);

CREATE TABLE player_deck
(
    player_id UUID NOT NULL REFERENCES player (uuid),
    card_id VARCHAR NOT NULL,
    card_order INT NOT NULL
);
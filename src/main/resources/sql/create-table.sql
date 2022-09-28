-- Create score table
CREATE TABLE IF NOT EXISTS scores(
    id      integer         PRIMARY KEY,
    player  varchar(255)    NOT NULL,
    score   integer         NOT NULL
);

-- Insert new result
INSERT INTO
    scores (player, score)
VALUES (?,?);

-- Select highest ever score
SELECT  MAX(s.score)
FROM    scores s;

-- Select a given player's highest ever score
SELECT  MAX(s.score)
FROM    scores s
WHERE   UPPER(s.player) = UPPER('?');

-- Select a given player's scores
SELECT  s.score
FROM    scores s
WHERE   UPPER(s.player) = UPPER(?)
ORDER BY s.score;

-- Empty table
DELETE FROM scores;

-- Get 10 top scores
SELECT
    s.player    AS "NAME"
    s.score     AS "SCORE"
FROM
    MAIN.scores s
ORDER BY
    s.score DESC
LIMIT 10;


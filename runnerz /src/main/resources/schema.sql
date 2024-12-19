DROP TABLE IF EXISTS Run;
CREATE TABLE IF NOT EXISTS Run (
    id INT NOT NULL,
    title VARCHAR(250) NOT NULL,
    started_on TIMESTAMP NOT NULL,
    completed_on TIMESTAMP NOT NULL,
    miles INT NOT NULL CHECK (miles > 0),
    location VARCHAR(50) NOT NULL,
    PRIMARY KEY(id)
);



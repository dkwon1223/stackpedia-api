CREATE TABLE category (
    category_id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(500) NOT NULL,
    slug VARCHAR(30) NOT NULL UNIQUE
);

CREATE TABLE technology (
    technology_id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    short_description VARCHAR(200) NOT NULL,
    description TEXT NOT NULL,
    slug VARCHAR(30) NOT NULL UNIQUE,
    website_url VARCHAR(255),
    github_url VARCHAR(255),
    documentation_url VARCHAR(255),
    logo_url VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE technology_category (
    technology_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    PRIMARY KEY (technology_id, category_id),
    FOREIGN KEY (technology_id) REFERENCES technology(technology_id),
    FOREIGN KEY (category_id) REFERENCES category(category_id)
);
DROP TABLE IF EXISTS apartments;

CREATE TABLE apartments (
  id BIGSERIAL PRIMARY KEY NOT NULL,
  rent INTEGER NOT NULL,
  number_of_bedrooms INTEGER NOT NULL,
  number_of_bathrooms NUMERIC(4, 2) NOT NULL,
  square_footage INTEGER NOT NULL,
  address VARCHAR(255) NOT NULL,
  city VARCHAR(255) NOT NULL,
  state VARCHAR(20) NOT NULL,
  zip_code VARCHAR(10) NOT NULL,
  user_id BIGINT not null,
  is_active boolean note null default false
);
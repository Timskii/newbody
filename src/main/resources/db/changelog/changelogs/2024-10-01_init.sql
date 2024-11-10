CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE SCHEMA IF NOT EXISTS "body";

CREATE TABLE "body"."user" (
  "id" uuid NOT NULL UNIQUE DEFAULT uuid_generate_v4(),
  "username" character varying(50) NOT NULL UNIQUE,
  "password" character varying(20),
  "status" character varying(20),
  "created_at" timestamp,
  PRIMARY KEY (id)
);








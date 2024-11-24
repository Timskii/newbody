ALTER TABLE IF EXISTS body."user" ADD COLUMN first_name character varying;
ALTER TABLE IF EXISTS body."user" ADD COLUMN last_name character varying;
ALTER TABLE IF EXISTS body."user" ADD COLUMN chat_id bigint;
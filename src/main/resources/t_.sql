CREATE TABLE "body"."subscription_plans" (
  "id" uuid DEFAULT uuid_generate_v4(),
  "name" character varying(50),
  "price" integer,
  "duration" integer,
   PRIMARY KEY (id)
);

comment on column "body"."subscription_plans"."duration" is 'count month';
comment on column "body"."subscription_plans"."price" is 'cost KZT';

CREATE TABLE "body"."user_subscriptions" (
  "id" uuid DEFAULT uuid_generate_v4(),
  "user_id" uuid NOT NULL,
  "plan_id" uuid,
  "start_date" date,
  "end_date" uuid  NOT NULL,
  "status" character varying(20),
  "payment_method" character varying(20),

  PRIMARY KEY (id),
  CONSTRAINT user_payment_user_id_fk_user_id FOREIGN KEY (user_id)
          REFERENCES "body"."user" (id) MATCH SIMPLE,
   CONSTRAINT user_payment_payment_id_fk_payment_id FOREIGN KEY (payment_id)
            REFERENCES "body"."payment" (id) MATCH SIMPLE
);

CREATE TABLE "body"."payments" (
  "id" uuid DEFAULT uuid_generate_v4(),
  "subscription_id" integer,
  "amount" character varying(20),
  "payment_date" date,
  "payment_status"
  "payment_method"
   PRIMARY KEY (id)
);

CREATE TABLE "body"."training" (
 "id" uuid DEFAULT uuid_generate_v4(),
 ""
);

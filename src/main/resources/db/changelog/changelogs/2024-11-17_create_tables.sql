
CREATE TABLE "body"."training" (
 "id" uuid primary key DEFAULT uuid_generate_v4(),
 "url" varchar,
 "type" varchar,
 "step" integer,
 "level" integer
);

CREATE TABLE "body"."subscription_plans" (
  "id" uuid primary key DEFAULT uuid_generate_v4(),
  "name" character varying(50),
  "price" integer,
  "duration" integer
);

comment on column "body"."subscription_plans"."duration" is 'count month';
comment on column "body"."subscription_plans"."price" is 'cost KZT';

CREATE TABLE "body"."user_subscriptions" (
  "id" uuid primary key DEFAULT uuid_generate_v4(),
  "user_id" uuid NOT NULL REFERENCES "body"."user"(id),
  "plan_id" uuid NOT NULL REFERENCES "body"."subscription_plans"(id),
  "start_date" date,
  "end_date" date,
  "status" character varying(20),
  "payment_method" character varying(20)
 );

CREATE TABLE "body"."user_training" (
 "id" uuid primary key DEFAULT uuid_generate_v4(),
 "user_id"  uuid NOT NULL references "body"."user"(id),
 "training_id" uuid NOT NULL references "body"."training"(id)
);

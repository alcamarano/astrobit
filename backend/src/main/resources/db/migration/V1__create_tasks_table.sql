CREATE TABLE tasks (
	id uuid DEFAULT uuidv7(),
	title VARCHAR(150) NOT NULL,
	description VARCHAR(300),
	due_date DATE NOT NULL,
	completed BOOLEAN DEFAULT false NOT NULL,
	completed_at TIMESTAMP WITH TIME ZONE,
	created_at TIMESTAMP WITH TIME ZONE DEFAULT now() NOT NULL,
	CONSTRAINT pk_tasks PRIMARY KEY ( id )
);
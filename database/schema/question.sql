create table question (
    id bigint constraint pk_question_id primary key,
    statement longtext constraint nn_question_statement not null,
    type char(3) constraint nn_question_type not null,
    solutionId bigint constraint fk_question_solution references solution(id) on delete cascade,
    hasImage boolean constraint nn_question_hasImage not null,
    subtopicID bigint constraint fk_question_subtopicID references subtopic(id) on delete cascade
);
-- insert into subject values(1, "DSA");
-- insert into subtopic values(1, 1, "Dynamic Programming");
--
--
--
-- insert into solution values(0, 1, "some solution");
-- insert into solution values(1, 2, "some solution 2");
-- insert into solution values(0, 3, "some solution 3");
-- insert into solution values(0, 4, "some solution 4");
-- insert into solution values(0, 5, "some solution 5");
-- insert into solution values(0, 6, "some solution 6");
-- insert into solution values(0, 7, "some solution 7");
--
--
--
-- insert into question values(0, -1, 4, 1, 1, 1, 1, "some statement", "mcq");
-- insert into question values(1, -1, 4, 2, 2, 1, 1, "some statement 2", "mcq");
-- insert into question values(0, -1, 4, 3, 3, 1, 1, "some statement 3", "mcq");
-- insert into question values(0, -1, 4, 4, 4, 1, 1, "some statement 4", "mcq");
-- insert into question values(0, -1, 4, 5, 5, 1, 1, "some statement 5", "mcq");
-- insert into question values(0, -1, 4, 6, 6, 1, 1, "some statement 6", "mcq");
-- insert into question values(0, -1, 4, 7, 7, 1, 1, "some statement 7", "mcq");
--
--
--
-- insert into option_table values (0,0,1,1,"q1 option A");
-- insert into option_table values (0,0,2,1,"q1 option B");
-- insert into option_table values (0,0,3,1,"q1 option C");
-- insert into option_table values (0,0,4,1,"q1 option D");
-- insert into option_table values (1,0,5,6,"q6 option A");
-- insert into option_table values (0,0,6,6,"q6 option B");
-- insert into option_table values (0,0,7,6,"q6 option C");
-- insert into option_table values (0,0,8,6,"q6 option D");
--
--
-- insert into image values(2, "my url", "Q");
-- insert into image values(2, "my url", "S");
-- insert into image values(5, "my url", "O");
--
--
--
-- insert into user values(1, "a@gmail.com", "Aayush", "password", "T");
-- insert into user values(2, "d@gmail.com", "Dhruv", "password", "T");
--
--
--
-- insert into exam values(180, 0, 1, 1, "2024-04-12", "Exam - 1");
--
--
--
-- insert into private_solution values(0, 1, "solution to private question 1");
-- insert into private_solution values(0, 2, "solution to private question 2");
-- insert into private_solution values(0, 3, "solution to private question 3");
-- insert into private_solution values(1, 4, "solution to private question 4");
-- insert into private_solution values(0, 5, "solution to private question 5");
-- insert into private_solution values(0, 6, "solution to private question 6");
-- insert into private_solution values(0, 7, "solution to private question 7");
-- insert into private_solution values(0, 8, "solution to private question 8");
-- insert into private_solution values(0, 9, "solution to private question 9");
-- insert into private_solution values(0, 10, "solution to private question 10");
--
--
--
-- insert into private_question values(1, -1, 4, 1, 1, 1, 1, 1, "some description of exam question 1", "mcq");
-- insert into private_question values(0, 0, 3, 1, 2, 2, 1, 1, "some description of exam question 2", "msq");
-- insert into private_question values(0, -1, 2, 1, 3, 3, 1, 1, "some description of exam question 3", "mcq");
-- insert into private_question values(0, -1, 2, 1, 4, 4, 1, 1, "some description of exam question 4", "mcq");
-- insert into private_question values(0, -1, 2, 1, 5, 5, 1, 1, "some description of exam question 5", "mcq");
-- insert into private_question values(0, -1, 4, 1, 6, 6, 1, 1, "some description of exam question 6", "mcq");
-- insert into private_question values(0, 0, 1, 1, 7, 7, 1, 1, "some description of exam question 7", "msq");
-- insert into private_question values(0, 0, 3, 1, 8, 8, 1, 1, "some description of exam question 8", "msq");
-- insert into private_question values(0, 0, 1, 1, 9, 9, 1, 1, "some description of exam question 9", "msq");
-- insert into private_question values(0, 0, 2, 1, 10, 10, 1, 1, "some description of exam question 10", "msq");
--
--
--
-- insert into private_option values(0, 1, 1, 1, "exam q1 option A");
-- insert into private_option values(1, 0, 2, 1, "exam q1 option B");
-- insert into private_option values(0, 0, 3, 1, "exam q1 option C");
-- insert into private_option values(0, 0, 4, 1, "exam q1 option D");
-- insert into private_option values(0, 0, 5, 2, "exam q2 option A");
-- insert into private_option values(0, 1, 6, 2, "exam q2 option B");
-- insert into private_option values(0, 0, 7, 2, "exam q2 option C");
-- insert into private_option values(0, 1, 8, 2, "exam q2 option D");
-- insert into private_option values(0, 1, 9, 9, "exam q9 option A");
-- insert into private_option values(0, 1, 10, 9, "exam q9 option B");
-- insert into private_option values(0, 0, 11, 9, "exam q9 option C");
-- insert into private_option values(0, 0, 12, 9, "exam q9 option D");
--
--
--
-- insert into image values(1, "my url", "q");
-- insert into image values(2, "my url", "o");
-- insert into image values(4, "my url", "s");
--
--
-- insert into exam_responses values(0, 1, 1, 0, 1, 1);
-- insert into exam_responses values(1, 0, 0, 0, 2, 1);
-- insert into exam_responses values(1, 0, 0, 1, 3, 1);
-- insert into exam_responses values(0, 1, 1, 1, 1, 2);
-- insert into exam_responses values(0, 0, 1, 0, 2, 2);
-- insert into exam_responses values(1, 0, 0, 1, 3, 2);


-- Batch insert:
insert into subject values(1, "DSA");
insert into subtopic values(1, 1, "Dynamic Programming");
insert into user values(1, "a@gmail.com", "Aayush", "password", "T");
insert into user values(2, "d@gmail.com", "Dhruv", "password", "S");
insert into user values(3, "k@gmail.com", "Karm", "password", "S");
insert into user values(4, "j@gmail.com", "Jay", "password", "S");


insert into exam_responses values(1, 1, 0, 0, 1, 2);
insert into exam_responses values(0, 1, 1, 0, 2, 2);
insert into exam_responses values(1, 0, 0, 0, 3, 2);
insert into exam_responses values(0, 1, 0, 1, 1, 3);
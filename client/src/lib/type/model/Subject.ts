import { Subtopic } from "./Subtopic";

type Subject = {
    id: string,
    name: string,
    subtopics: Array<Subtopic>,
    totalQuestions: number
};

export type {
    Subject
}
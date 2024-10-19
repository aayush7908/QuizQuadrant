import Subtopic from "./subtopic";

export default interface Subject {
    id: string,
    name: string,
    subtopics: Subtopic[]
};
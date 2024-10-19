import Option from "./option";
import Subtopic from "./subtopic";

interface Solution {
    id: string,
    statement: string,
    imageUrl: string | undefined
};

interface QuestionCreatedBy {
    id: string
};

export default interface Question {
    id: string,
    type: string,
    isPublic: boolean,
    statement: string,
    imageUrl: string | undefined,
    lastModifiedOn: Date,
    subtopic: Subtopic,
    options: Option[],
    solution: Solution,
    createdBy: QuestionCreatedBy,
    totalQuestions: number
};
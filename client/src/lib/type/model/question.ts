import { Option } from "./Option";
import { Solution } from "./Solution";
import { Subtopic } from "./Subtopic";
import { User } from "./User";

type Question = {
    id: string, 
    positiveMarks: number,
    negativeMarks: number,
    type: string,
    isPublic: boolean,
    statement: string, 
    imageUrl: string | undefined, 
    subtopic: Subtopic, 
    options: Option[], 
    solution: Solution,
    createdBy: User,
    lastModifiedOn: Date
};

export type {
    Question
}
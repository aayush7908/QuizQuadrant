import { Option } from "./Option";
import { Solution } from "./Solution";
import { Subtopic } from "./Subtopic";
import { User } from "./User";

type Question = {
    id: string, 
    type: string,
    isPublic: boolean,
    statement: string, 
    imageUrl: string | undefined, 
    subtopic: Subtopic, 
    options: Array<Option>, 
    solution: Solution,
    createdBy: User
};

export type {
    Question
}
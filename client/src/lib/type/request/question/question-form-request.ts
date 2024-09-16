import { Subtopic } from "../../model/Subtopic";

type optionReq = {
    statement: string,
    imageUrl: string,
    isCorrect: boolean
};

type solutionReq = {
    statement: string,
    imageUrl: string
};

type req = {
    type: string,
    isPublic: boolean,
    statement: string,
    imageUrl: string,
    subtopic: Subtopic,
    options: optionReq[],
    solution: solutionReq
};

export type {
    req
}
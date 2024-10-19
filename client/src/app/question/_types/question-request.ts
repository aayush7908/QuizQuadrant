import Subtopic from "@/app/_types/subtopic";

interface OptionRequest {
    statement: string,
    imageUrl: string,
    isCorrect: boolean
};

interface SolutionRequest {
    statement: string,
    imageUrl: string
};

export default interface QuestionRequest {
    type: string,
    isPublic: boolean,
    statement: string,
    imageUrl: string,
    subtopic: Subtopic,
    options: OptionRequest[],
    solution: SolutionRequest
};
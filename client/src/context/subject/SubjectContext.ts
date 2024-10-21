import Subject from "@/app/_types/subject";
import { createContext } from "react";

type subjectContextType = {
    subjects: Map<string, Subject> | undefined,
    addSubjects: (subjects: Subject[]) => void
};

const subjectContextDefaultValue: subjectContextType = {
    subjects: undefined,
    addSubjects: (subjects: Subject[]) => { }
};

const subjectContext = createContext<subjectContextType>(subjectContextDefaultValue);

export { subjectContext as SubjectContext };
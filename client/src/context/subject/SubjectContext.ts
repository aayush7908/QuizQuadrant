import { Subject } from "@/lib/type/model/Subject";
import { createContext } from "react";

type subjectContextType = {
    subjects: Map<string, Subject> | undefined,
    addSubjects: (subjects: Array<Subject>) => void
};

const subjectContextDefaultValue: subjectContextType = {
    subjects: undefined,
    addSubjects: (subjects: Array<Subject>) => { }
};

const subjectContext = createContext<subjectContextType>(subjectContextDefaultValue);

export { subjectContext as SubjectContext };
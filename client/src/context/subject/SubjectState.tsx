"use client"

import React, { ReactNode, useState } from "react";
import { SubjectContext } from "./SubjectContext";
import Subject from "@/app/_types/subject";

export default function SubjectState({ children }: { children: ReactNode }) {

    const [subjects, setSubjects] = useState<Map<string, Subject> | undefined>(undefined);

    const addSubjects = (data: Subject[]) => {
        const newSubjects = new Map<string, Subject>();
        data.forEach((dataSub) => {
            newSubjects.set(dataSub.id, dataSub);
        })
        setSubjects(newSubjects);
    }

    return (
        <SubjectContext.Provider value={{
            subjects,
            addSubjects
        }}>
            {children}
        </SubjectContext.Provider>
    );
}
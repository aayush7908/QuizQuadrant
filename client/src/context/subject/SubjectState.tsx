"use client"

import React, { ReactNode, useState } from "react";
import { Subject } from "@/lib/type/model/Subject";
import { SubjectContext } from "./SubjectContext";

export default function SubjectState({ children }: { children: ReactNode }) {

    const [subjects, setSubjects] = useState<Map<string, Subject> | undefined>(undefined);

    const addSubjects = (data: Array<Subject>) => {
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
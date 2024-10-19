"use client"

import React, { ReactNode, useState } from "react";
import { RefreshContext } from "./RefreshContext";

export default function RefreshState({ children }: { children: ReactNode }) {

    const [x, setX] = useState<number>(0);

    const refresh = () => {
        setX(x => x + 1);
    }

    return (
        <RefreshContext.Provider value={{
            x,
            refresh
        }}>
            {children}
        </RefreshContext.Provider>
    );
}
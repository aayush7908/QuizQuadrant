"use client"

import React, { ReactNode, useState } from "react";
import { AuthContext } from "./AuthContext";
import { AuthenticatedUser } from "@/app/_types/authenticated-user";

export default function AuthState({ children }: { children: ReactNode }) {

    const [user, setUser] = useState<AuthenticatedUser | undefined>(undefined);

    const authenticate = (data: AuthenticatedUser) => {
        const newUser = { ...data } as AuthenticatedUser;
        setUser(newUser);
    };

    const logout = () => {
        setUser(undefined);
    };

    const verifyEmail = () => {
        const newUser = { ...user } as AuthenticatedUser;
        newUser.isEmailVerified = true;
        setUser(newUser);
    }

    return (
        <AuthContext.Provider value={{
            user,
            authenticate,
            logout,
            verifyEmail
        }}>
            {children}
        </AuthContext.Provider>
    );
}
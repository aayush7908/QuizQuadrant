"use client"

import React, { ReactNode, useState } from "react";
import { AuthContext } from "./AuthContext";
import { User } from "@/lib/type/model/User";

export default function AuthState({ children }: { children: ReactNode }) {

    const [user, setUser] = useState<User | undefined>(undefined);

    const authenticate = (user: User) => {
        const newUser = {
            id: user.id,
            email: user.email,
            isEmailVerified: user.isEmailVerified
        } as User;
        setUser(newUser);
    };

    const logout = () => {
        setUser(undefined);
    };

    const verifyEmail = () => {
        const newUser = { ...user } as User;
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
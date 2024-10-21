import { AuthenticatedUser } from "@/app/_types/authenticated-user";
import { createContext } from "react";

type authContextType = {
    user: AuthenticatedUser | undefined,
    authenticate: (user: AuthenticatedUser) => void,
    logout: () => void,
    verifyEmail: () => void
};

const authContextDefaultValue: authContextType = {
    user: undefined,
    authenticate: () => { },
    logout: () => { },
    verifyEmail: () => { }
};

const authContext = createContext<authContextType>(authContextDefaultValue);

export { authContext as AuthContext };
import { User } from "@/lib/type/model/User";
import { createContext } from "react";

type authContextType = {
    user: User | undefined,
    authenticate: (user: User) => void,
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
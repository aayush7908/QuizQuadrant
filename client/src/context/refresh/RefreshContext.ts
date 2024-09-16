import { Subject } from "@/lib/type/model/Subject";
import { createContext } from "react";

type refreshContextType = {
    x: number,
    refresh: () => void
};

const refreshContextDefaultValue: refreshContextType = {
    x: 0,
    refresh: () => { }
};

const refreshContext = createContext<refreshContextType>(refreshContextDefaultValue);

export { refreshContext as RefreshContext };
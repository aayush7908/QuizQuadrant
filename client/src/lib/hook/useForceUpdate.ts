import { useState } from "react";

function useForceUpdate() {
    const [value, setValue] = useState<number>(0);
    return () => setValue(value => value + 1);
}

export {
    useForceUpdate
}
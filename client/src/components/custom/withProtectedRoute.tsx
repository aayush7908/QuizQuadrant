import { AuthContext } from "@/context/auth/AuthContext";
import { useRouter } from "next/navigation";
import { ElementType, ReactNode, useContext, useEffect } from "react";

export default function withProtectedRoute(WrappedComponent: ElementType) {
    return () => {
        const { user } = useContext(AuthContext);
        const router = useRouter();

        useEffect(() => {
            if (!user) {
                router.push("/auth/login");
            }
        }, [user, router]);

        return user ? <WrappedComponent /> : null;
    };
};
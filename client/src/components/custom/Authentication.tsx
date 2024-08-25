"use client"

import { authenticateAPI } from "@/actions/auth/authenticate";
import { AuthContext } from "@/context/auth/AuthContext";
import { useContext, useEffect } from "react";
import { useToast } from "../ui/use-toast";

export default function Authentication() {

    const { authenticate } = useContext(AuthContext);
    const { toast } = useToast();

    useEffect(() => {
        (async () => {
            const { success, data, error } = await authenticateAPI();
            if (success && data) {
                authenticate(data.user);
                toast({
                    title: "User authenticated successfully"
                });
            } else if (error) {
                toast({
                    title: error.errorMessage,
                    variant: "destructive"
                });
            }
        })();
    }, []);

    return (
        <></>
    );
}
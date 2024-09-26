"use client"

import { getUserProfileAction } from "@/actions/account/profile/get-user-profile-action";
import UserCard from "@/components/custom/account/profile/UserCard";
import { Loader } from "@/components/custom/Loader";
import { useToast } from "@/components/ui/use-toast";
import { User } from "@/lib/type/model/User";
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";

export default function Profile() {

    const [isProcessing, setIsProcessing] = useState<boolean>(true);
    const [user, setUser] = useState<User | undefined>(undefined);
    const { toast } = useToast();
    const router = useRouter();

    useEffect(() => {
        (async () => {
            const { success, data, error } = await getUserProfileAction();
            if (success && data) {
                const newUser = {
                    email: data.email,
                    firstName: data.firstName,
                    lastName: data.lastName,
                    profileImageUrl: data.profileImageUrl,
                    accountCreatedOn: new Date(data.accountCreatedOn),
                    role: data.role,
                    isEmailVerified: data.isEmailVerified
                } as User;
                setUser(newUser);
            } else if (error) {
                toast({
                    title: error.errorMessage,
                    variant: "destructive"
                });
                router.refresh();
            }
            setIsProcessing(false);
        })();
    }, []);

    return (
        <>
            {
                isProcessing ? (
                    <Loader />
                ) : (
                    user && (
                        <div className="min-h-full p-[1rem] md:p-[2rem] lg:p-[3rem] pb-[3rem]">
                            <UserCard user={user} />
                        </div>
                    )
                )
            }
        </>
    );
}
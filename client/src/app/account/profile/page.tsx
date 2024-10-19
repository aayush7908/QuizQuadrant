"use client"

import { Loader } from "@/app/_components/Loader";
import UserProfile from "@/app/_types/user-profile";
import { useToast } from "@/components/hooks/use-toast";
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import UserCard from "../_components/UserCard";
import { getUserProfileAction } from "./_actions/get-user-profile-action";
import { updateUserNameAction } from "./_actions/update-user-name-action";

export default function Profile() {

    const [isProcessing, setIsProcessing] = useState<boolean>(true);
    const [user, setUser] = useState<UserProfile | undefined>(undefined);
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
                } as UserProfile;
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
                            <UserCard
                                user={user}
                                updateUserNameFunction={updateUserNameAction}
                            />
                        </div>
                    )
                )
            }
        </>
    );
}
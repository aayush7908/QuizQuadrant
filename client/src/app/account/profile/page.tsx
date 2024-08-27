"use client"

import { getProfileAPI } from "@/actions/user/get/profile";
import NameForm from "@/components/custom/account/profile/NameForm";
import { Loader } from "@/components/custom/Loader";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { useToast } from "@/components/ui/use-toast";
import { User } from "@/lib/type/model/User";
import { CircleAlert, CircleCheck, CircleUser } from "lucide-react";
import { useRouter } from "next/navigation";
import { use, useEffect, useState } from "react";

export default function Profile() {

    const [isProcessing, setIsProcessing] = useState<boolean>(true);
    const [isNameEditable, setIsNameEditable] = useState<boolean>(false);
    const [user, setUser] = useState<User | undefined>(undefined);
    const { toast } = useToast();
    const router = useRouter();

    const toggleIsNameEditable = () => {
        setIsNameEditable(isNameEditable => !isNameEditable);
    }

    useEffect(() => {
        (async () => {
            const { success, data, error } = await getProfileAPI();
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
                            <div className="grid gap-5">
                                <div className="grid gap-2 justify-center">
                                    <CircleUser className="size-[10rem]" />
                                    <div className="flex justify-center">
                                        <label htmlFor="profileImage" className="bg-black text-center text-white py-2 px-3 rounded-md cursor-pointer">Change Image</label>
                                        <input id="profileImage" type="file" className="hidden" />
                                    </div>
                                </div>
                                <div>
                                    <Card>
                                        <CardHeader className="p-[1rem] border-b bg-muted">
                                            <CardTitle className="text-lg">
                                                <span>Name</span>
                                            </CardTitle>
                                        </CardHeader>
                                        <CardContent className="pt-[1rem]">
                                            <NameForm firstName={user.firstName} lastName={user.lastName} isNameEditable={isNameEditable} toggleIsNameEditable={toggleIsNameEditable} />
                                        </CardContent>
                                    </Card>
                                </div>
                                <div>
                                    <Card>
                                        <CardHeader className="p-[1rem] border-b bg-muted">
                                            <CardTitle className="text-lg">
                                                <span>Email</span>
                                            </CardTitle>
                                        </CardHeader>
                                        <CardContent>
                                            <div className="w-full pt-[1rem] grid lg:grid-cols-2">
                                                <span>{user.email}</span>
                                            </div>
                                        </CardContent>
                                    </Card>
                                </div>
                                <div>
                                    <Card>
                                        <CardHeader className="p-[1rem] border-b bg-muted">
                                            <CardTitle className="text-lg">
                                                <span>Email Verification Status</span>
                                            </CardTitle>
                                        </CardHeader>
                                        <CardContent>
                                            <div className="w-full pt-[1rem] flex gap-2">
                                                {
                                                    user.isEmailVerified ? (
                                                        <>
                                                            <CircleCheck />
                                                            <span>Verified</span>
                                                        </>
                                                    ) : (
                                                        <>
                                                            <CircleAlert />
                                                            <span>Verification Pending</span>
                                                        </>
                                                    )
                                                }
                                            </div>
                                        </CardContent>
                                    </Card>
                                </div>
                                <div>
                                    <Card>
                                        <CardHeader className="p-[1rem] border-b bg-muted">
                                            <CardTitle className="text-lg">
                                                <span>Role</span>
                                            </CardTitle>
                                        </CardHeader>
                                        <CardContent>
                                            <div className="w-full pt-[1rem] grid lg:grid-cols-2">
                                                <span>{user.role}</span>
                                            </div>
                                        </CardContent>
                                    </Card>
                                </div>
                                <div>
                                    <Card>
                                        <CardHeader className="p-[1rem] border-b bg-muted">
                                            <CardTitle className="text-lg">
                                                <span>Account Created On</span>
                                            </CardTitle>
                                        </CardHeader>
                                        <CardContent>
                                            <div className="w-full pt-[1rem] grid lg:grid-cols-2">
                                                <span>{`${user.accountCreatedOn.getDate()}-${user.accountCreatedOn.getMonth()}-${user.accountCreatedOn.getFullYear()} @ ${user.accountCreatedOn.getHours()}:${user.accountCreatedOn.getMinutes()}`}</span>
                                            </div>
                                        </CardContent>
                                    </Card>
                                </div>
                            </div>
                        </div>
                    )
                )
            }
        </>
    );
}
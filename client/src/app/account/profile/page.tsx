"use client"

import NameForm from "@/components/custom/account/profile/NameForm";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { CircleUser } from "lucide-react";
import { useState } from "react";

export default function Profile() {

    const [isNameEditable, setIsNameEditable] = useState<boolean>(false);

    const toggleIsNameEditable = () => {
        setIsNameEditable(isNameEditable => !isNameEditable);
    }

    return (
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
                            <NameForm firstName="Aayush" lastName="Dalal" isNameEditable={isNameEditable} toggleIsNameEditable={toggleIsNameEditable} />
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
                                <span>aayush.dalal4314@gmail.com</span>
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
                                <span>TEACHER</span>
                            </div>
                        </CardContent>
                    </Card>
                </div>
            </div>
        </div>
    );
}
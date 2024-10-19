import { usePathname } from "next/navigation"
import { useState } from "react"
import { CircleAlert, CircleCheck, CircleUser } from "lucide-react"
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"
import UserProfile from "@/app/_types/user-profile"
import UpdateUserNameRequest from "../_types/update-user-name-request"
import UserNameForm from "./UserNameForm"
import ErrorResponse from "@/app/_types/error-response"

export default function UserCard({
    user,
    updateUserNameFunction
}: {
    user: UserProfile,
    updateUserNameFunction(body: UpdateUserNameRequest): Promise<{
        success: boolean;
        error?: undefined;
    } | {
        success: boolean;
        error: ErrorResponse;
    }>
}) {

    const [isNameEditable, setIsNameEditable] = useState<boolean>(false);
    const path = usePathname();

    const toggleIsNameEditable = () => {
        setIsNameEditable(isNameEditable => !isNameEditable);
    }

    return (
        <div className="grid gap-5">
            <div className="grid gap-2 justify-center">
                <CircleUser className="size-[10rem]" />
                <div className="flex justify-center">
                    <label
                        htmlFor="profileImage"
                        className="bg-black text-center text-white py-2 px-3 rounded-md cursor-pointer"
                    >
                        Change Image
                    </label>
                    <input
                        id="profileImage"
                        type="file"
                        className="hidden"
                    />
                </div>
            </div>
            <div>
                <Card>
                    <CardHeader className="p-[1rem] border-b bg-muted rounded-t-md">
                        <CardTitle className="text-lg">
                            <span>Name</span>
                        </CardTitle>
                    </CardHeader>
                    <CardContent className="pt-[1rem]">
                        <UserNameForm
                            firstName={user.firstName}
                            lastName={user.lastName}
                            isNameEditable={isNameEditable}
                            toggleIsNameEditable={toggleIsNameEditable}
                            onSubmit={updateUserNameFunction}
                        />
                    </CardContent>
                </Card>
            </div>
            <div>
                <Card>
                    <CardHeader className="p-[1rem] border-b bg-muted rounded-t-md">
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
                    <CardHeader className="p-[1rem] border-b bg-muted rounded-t-md">
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
                    <CardHeader className="p-[1rem] border-b bg-muted rounded-t-md">
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
                    <CardHeader className="p-[1rem] border-b bg-muted rounded-t-md">
                        <CardTitle className="text-lg">
                            <span>Account Created On</span>
                        </CardTitle>
                    </CardHeader>
                    <CardContent>
                        <div className="w-full pt-[1rem] grid lg:grid-cols-2">
                            <span>{(new Date(user.accountCreatedOn)).toLocaleString()}</span>
                        </div>
                    </CardContent>
                </Card>
            </div>
        </div>
    )
}
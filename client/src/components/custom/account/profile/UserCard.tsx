import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"
import { User } from "@/lib/type/model/User"
import { CircleAlert, CircleCheck, CircleUser } from "lucide-react"
import NameForm from "./NameForm"
import { useState } from "react"
import { req } from "@/lib/type/request/account/profile/user-name-form-request"
import { updateNameAction } from "@/actions/account/profile/update-user-name-action"
import { usePathname } from "next/navigation"
import { updateUserNameAction } from "@/actions/account/admin/users/update-user-name-action"

export default function UserCard({
    user
}: {
    user: User
}) {

    const [isNameEditable, setIsNameEditable] = useState<boolean>(false);
    const path = usePathname();

    const toggleIsNameEditable = () => {
        setIsNameEditable(isNameEditable => !isNameEditable);
    }

    const updateName = async (body: req) => {
        if (path === "/account/admin/users") {
            return await updateUserNameAction(body, user.id);
        } else {
            return await updateNameAction(body);
        }
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
                        <NameForm
                            firstName={user.firstName}
                            lastName={user.lastName}
                            isNameEditable={isNameEditable}
                            toggleIsNameEditable={toggleIsNameEditable}
                            onSubmit={updateName}
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
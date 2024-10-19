import { useState } from "react"
import { AccordionContent, AccordionItem, AccordionTrigger } from "@/components/ui/accordion"
import UserCard from "@/app/account/_components/UserCard"
import { SubmitButton } from "@/app/_components/SubmitButton"
import { deleteUserAction } from "@/app/account/admin/users/_actions/delete-user-action"
import UserProfile from "@/app/_types/user-profile"
import { useToast } from "@/components/hooks/use-toast"
import UpdateUserNameRequest from "@/app/account/_types/update-user-name-request"
import { updateUserNameAction } from "../_actions/update-user-name-action"

export default function UserAccordion({
    index,
    data,
    removeFunction
}: {
    index: number,
    data: UserProfile,
    removeFunction: (index: number) => void
}) {

    const [isProcessing, setIsProcessing] = useState<boolean>(false);
    const { toast } = useToast();

    const updateUserNameFunction = async (body: UpdateUserNameRequest) => {
        return await updateUserNameAction(body, data.id);
    }

    const handleDeleteUser = async () => {
        const isConfirm = window.confirm("All data related to this user will be deleted and cannot be retrieved back again. Are you sure to delete this User ?");
        if (!isConfirm) {
            return;
        }
        const email = window.prompt("Enter email of user to delete.");
        if (email !== data.email) {
            toast({
                title: "Email does not match",
                variant: "destructive"
            });
            return;
        }
        const str = window.prompt("Write delete below to confirm.");
        if (str !== "delete") {
            toast({
                title: "Action aborted",
                variant: "destructive"
            });
            return;
        }
        setIsProcessing(true);
        const { success, error } = await deleteUserAction(data.id);
        if (success) {
            toast({
                title: "User deleted successfully"
            });
            removeFunction(index);
        } else if (error) {
            toast({
                title: error.errorMessage,
                variant: "destructive"
            });
        }
        setIsProcessing(false);
    }

    return (
        <AccordionItem value={`${index}`}>
            <AccordionTrigger>{index + 1}. {data.email}</AccordionTrigger>
            <AccordionContent className="grid gap-3 p-5 border rounded-md bg-muted/20">
                <div className="text-end">
                    <SubmitButton type="submit" variant="destructive" displayName="Delete" isProcessing={isProcessing} onSubmit={handleDeleteUser} />
                </div>
                <UserCard
                    user={data}
                    updateUserNameFunction={updateUserNameFunction}
                />
            </AccordionContent>
        </AccordionItem>
    )
}
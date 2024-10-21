import Link from "next/link";
import { useContext, useState } from "react";
import { PenLineIcon, Trash2 } from "lucide-react";
import { useToast } from "@/components/hooks/use-toast";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { RefreshContext } from "@/context/refresh/RefreshContext";
import { deleteSubjectAction } from "@/app/_actions/delete-subject-action";
import { deleteSubtopicAction } from "@/app/_actions/delete-subtopic-action";
import Subject from "@/app/_types/subject";

export function SubjectCard({
    subject
}: {
    subject: Subject
}) {

    const [isProcessing, setIsProcessing] = useState<boolean>(false);
    const { refresh } = useContext(RefreshContext);
    const { toast } = useToast();

    const handleDeleteSubject = async (id: string) => {
        setIsProcessing(true);
        const isConfirm = window.confirm("All data related to this subject will be deleted and cannot be retrieved back again. Are you sure to delete this Subject ?");
        if (!isConfirm) {
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
        const { success, error } = await deleteSubjectAction(id);
        if (success) {
            toast({
                title: "Subject deleted successfully"
            });
            refresh();
        } else if (error) {
            toast({
                title: error.errorMessage,
                variant: "destructive"
            });
        }
        setIsProcessing(false);
    }

    const handleDeleteSubtopic = async (id: string) => {
        setIsProcessing(true);
        const isConfirm = window.confirm("All data related to this subtopic will be deleted and cannot be retrieved back again. Are you sure to delete this Subtopic ?");
        if (!isConfirm) {
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
        const { success, error } = await deleteSubtopicAction(id);
        if (success) {
            toast({
                title: "Subtopic deleted successfully"
            });
            refresh();
        } else if (error) {
            toast({
                title: error.errorMessage,
                variant: "destructive"
            });
        }
        setIsProcessing(false);
    }

    return (
        <Card>
            <CardHeader className="p-[1rem] border-b bg-muted">
                <CardTitle className="text-lg flex justify-between items-center">
                    <span>{subject.name}</span>
                    <div className="flex gap-3">
                        <Link href={`/subject/edit/${subject.id}`}>
                            <PenLineIcon className="cursor-pointer" />
                        </Link>
                        {
                            !isProcessing && (
                                <Trash2
                                    className="cursor-pointer"
                                    onClick={() => {
                                        handleDeleteSubject(subject.id)
                                    }}
                                />
                            )
                        }
                    </div>
                </CardTitle>
            </CardHeader>
            <CardContent className="pt-[1rem] flex flex-wrap gap-3">
                {
                    subject.subtopics.map((subtopic, index) => {
                        return (
                            <div
                                key={index}
                                className="px-3 flex gap-3 items-center border border-gray-400 rounded-md bg-muted text-lg"
                            >
                                <span>{subtopic.name}</span>
                                <Link href={`/subtopic/edit/${subtopic.id}`}>
                                    <PenLineIcon className="size-5 cursor-pointer" />
                                </Link>
                                {
                                    !isProcessing && (
                                        <Trash2
                                            className="size-5 cursor-pointer"
                                            onClick={() => {
                                                handleDeleteSubtopic(subtopic.id)
                                            }}
                                        />
                                    )
                                }
                            </div>
                        )
                    })
                }
            </CardContent>
        </Card>
    );
}
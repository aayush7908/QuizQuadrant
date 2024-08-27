"use client"

import { SubjectCard } from "@/components/custom/account/subject-and-subtopic/SubjectCard"
import { useToast } from "@/components/ui/use-toast";
import { AuthContext } from "@/context/auth/AuthContext";
import { SubjectContext } from "@/context/subject/SubjectContext"
import { PlusSquare } from "lucide-react";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { useContext, useEffect } from "react"

export default function SubjectAndSubtopic() {

    const { user } = useContext(AuthContext);
    const { subjects } = useContext(SubjectContext);
    const { toast } = useToast();
    const router = useRouter();

    useEffect(() => {
        if (!user || user.role !== "ADMIN") {
            toast({
                title: "Access Denied",
                variant: "destructive"
            });
            router.push("/");
            return;
        }
    }, []);

    return (
        <>
            {
                user && user.role === "ADMIN" ? (
                    <div className="p-[1rem] md:p-[2rem] lg:p-[3rem] pb-[3rem] grid gap-5">
                        <div className="flex flex-wrap gap-3 justify-end">
                            <Link href={"/subject/create"} className="h-[2.5rem] flex items-center gap-2 text-base bg-black text-white py-2 px-3 rounded-md cursor-pointer">
                                <PlusSquare />
                                <span>Create Subject</span>
                            </Link>
                            <Link href={"/subtopic/create"} className="h-[2.5rem] flex items-center gap-2 text-base bg-black text-white py-2 px-3 rounded-md cursor-pointer">
                                <PlusSquare />
                                <span>Create Subtopic</span>
                            </Link>
                        </div>
                        {
                            (subjects && subjects.size > 0) ? (
                                Array.from(subjects.values()).map((subject, index) => {
                                    return (
                                        <SubjectCard key={index} subject={subject} />
                                    )
                                })
                            ) : (
                                <div className="h-full flex justify-center text-xl">No subjects created</div>
                            )
                        }
                    </div>
                ) : (
                    null
                )
            }
        </>
    );
}
